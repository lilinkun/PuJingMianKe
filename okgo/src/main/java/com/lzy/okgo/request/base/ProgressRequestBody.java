
package com.lzy.okgo.request.base;

import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.utils.HttpUtils;
import com.lzy.okgo.utils.OkLogger;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;


public class ProgressRequestBody<T> extends RequestBody {

    private final RequestBody requestBody;         
    private final Callback<T> callback;
    private UploadInterceptor interceptor;

    ProgressRequestBody(RequestBody requestBody, Callback<T> callback) {
        this.requestBody = requestBody;
        this.callback = callback;
    }


    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }


    @Override
    public long contentLength() {
        try {
            return requestBody.contentLength();
        } catch (IOException e) {
            OkLogger.printStackTrace(e);
            return -1;
        }
    }


    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        CountingSink countingSink = new CountingSink(sink);
        BufferedSink bufferedSink = Okio.buffer(countingSink);
        requestBody.writeTo(bufferedSink);
        bufferedSink.flush();
    }


    private final class CountingSink extends ForwardingSink {

        private final Progress progress;

        CountingSink(Sink delegate) {
            super(delegate);
            progress = new Progress();
            progress.totalSize = contentLength();
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);

            Progress.changeProgress(progress, byteCount, new Progress.Action() {
                @Override
                public void call(Progress progress) {
                    if (interceptor != null) {
                        interceptor.uploadProgress(progress);
                    } else {
                        onProgress(progress);
                    }
                }
            });
        }
    }

    private void onProgress(final Progress progress) {
        HttpUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.uploadProgress(progress);
                }
            }
        });
    }

    public void setInterceptor(UploadInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    public interface UploadInterceptor {
        void uploadProgress(Progress progress);
    }
}

package cn.com.pujing.autoupdate;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import java.io.File;

/**
 * author : liguo
 * date : 2021/4/21 15:24
 * description :
 */
public class ApkDownloadReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent data) {
        //获取下载任务的downloadId
        /*long downloadId = data.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
        if (downloadId == PrefUtils.getApkDownloadId()) {
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            //通过downloadId来查找下载的任务
            Cursor query = downloadManager.query(new DownloadManager.Query().setFilterById(downloadId));
            try {
                if (query.moveToFirst()) {
                    //获取下载任务的状态
                    int status = query.getInt(query.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    switch (status) {
                        //下载暂停
                        case DownloadManager.STATUS_PAUSED:
                            break;
                        //下载延迟
                        case DownloadManager.STATUS_PENDING:
                            break;
                        //正在下载
                        case DownloadManager.STATUS_RUNNING:
                            break;
                        //下载完成
                        case DownloadManager.STATUS_SUCCESSFUL:
                            //下载完成安装APK
                            installApk(context,query);
                            break;
                        //下载失败
                        case DownloadManager.STATUS_FAILED:
                            //弹出提示
                            Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
                            break;
                    }

                }
            } finally {
                query.close();
            }*/
//        }
    }

    private void installApk(Context context,Cursor query) {
        String apkPath = query.getString(query.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
        Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE, Uri.fromFile(new File(apkPath)));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
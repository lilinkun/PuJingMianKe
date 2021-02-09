package com.lzy.okgo.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.SystemClock;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.base.Request;
import com.lzy.okgo.utils.IOUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Progress implements Serializable {
    private static final long serialVersionUID = 6353658567594109891L;

    public static final int NONE = 0;         
    public static final int WAITING = 1;      
    public static final int LOADING = 2;      
    public static final int PAUSE = 3;        
    public static final int ERROR = 4;        
    public static final int FINISH = 5;       

    public static final String TAG = "tag";
    public static final String URL = "url";
    public static final String FOLDER = "folder";
    public static final String FILE_PATH = "filePath";
    public static final String FILE_NAME = "fileName";
    public static final String FRACTION = "fraction";
    public static final String TOTAL_SIZE = "totalSize";
    public static final String CURRENT_SIZE = "currentSize";
    public static final String STATUS = "status";
    public static final String PRIORITY = "priority";
    public static final String DATE = "date";
    public static final String REQUEST = "request";
    public static final String EXTRA1 = "extra1";
    public static final String EXTRA2 = "extra2";
    public static final String EXTRA3 = "extra3";

    public String tag;                              
    public String url;                              
    public String folder;                           
    public String filePath;                         
    public String fileName;                         
    public float fraction;                          
    public long totalSize;                          
    public long currentSize;                        
    public transient long speed;                    
    public int status;                              
    public int priority;                            
    public long date;                               
    public Request<?, ? extends Request> request;   
    public Serializable extra1;                     
    public Serializable extra2;                     
    public Serializable extra3;                     
    public Throwable exception;                     

    private transient long tempSize;                
    private transient long lastRefreshTime;         
    private final transient List<Long> speedBuffer;       

    public Progress() {
        lastRefreshTime = SystemClock.elapsedRealtime();
        totalSize = -1;
        priority = Priority.DEFAULT;
        date = System.currentTimeMillis();
        speedBuffer = new ArrayList<>();
    }

    public static Progress changeProgress(Progress progress, long writeSize, Action action) {
        return changeProgress(progress, writeSize, progress.totalSize, action);
    }

    public static Progress changeProgress(final Progress progress, long writeSize, long totalSize, final Action action) {
        progress.totalSize = totalSize;
        progress.currentSize += writeSize;
        progress.tempSize += writeSize;

        long currentTime = SystemClock.elapsedRealtime();
        boolean isNotify = (currentTime - progress.lastRefreshTime) >= OkGo.REFRESH_TIME;
        if (isNotify || progress.currentSize == totalSize) {
            long diffTime = currentTime - progress.lastRefreshTime;
            if (diffTime == 0) diffTime = 1;
            progress.fraction = progress.currentSize * 1.0f / totalSize;
            progress.speed = progress.bufferSpeed(progress.tempSize * 1000 / diffTime);
            progress.lastRefreshTime = currentTime;
            progress.tempSize = 0;
            if (action != null) {
                action.call(progress);
            }
        }
        return progress;
    }


    private long bufferSpeed(long speed) {
        speedBuffer.add(speed);
        if (speedBuffer.size() > 10) {
            speedBuffer.remove(0);
        }
        long sum = 0;
        for (float speedTemp : speedBuffer) {
            sum += speedTemp;
        }
        return sum / speedBuffer.size();
    }


    public void from(Progress progress) {
        totalSize = progress.totalSize;
        currentSize = progress.currentSize;
        fraction = progress.fraction;
        speed = progress.speed;
        lastRefreshTime = progress.lastRefreshTime;
        tempSize = progress.tempSize;
    }

    public interface Action {
        void call(Progress progress);
    }

    public static ContentValues buildContentValues(Progress progress) {
        ContentValues values = new ContentValues();
        values.put(TAG, progress.tag);
        values.put(URL, progress.url);
        values.put(FOLDER, progress.folder);
        values.put(FILE_PATH, progress.filePath);
        values.put(FILE_NAME, progress.fileName);
        values.put(FRACTION, progress.fraction);
        values.put(TOTAL_SIZE, progress.totalSize);
        values.put(CURRENT_SIZE, progress.currentSize);
        values.put(STATUS, progress.status);
        values.put(PRIORITY, progress.priority);
        values.put(DATE, progress.date);
        values.put(REQUEST, IOUtils.toByteArray(progress.request));
        values.put(EXTRA1, IOUtils.toByteArray(progress.extra1));
        values.put(EXTRA2, IOUtils.toByteArray(progress.extra2));
        values.put(EXTRA3, IOUtils.toByteArray(progress.extra3));
        return values;
    }

    public static ContentValues buildUpdateContentValues(Progress progress) {
        ContentValues values = new ContentValues();
        values.put(FRACTION, progress.fraction);
        values.put(TOTAL_SIZE, progress.totalSize);
        values.put(CURRENT_SIZE, progress.currentSize);
        values.put(STATUS, progress.status);
        values.put(PRIORITY, progress.priority);
        values.put(DATE, progress.date);
        return values;
    }

    public static Progress parseCursorToBean(Cursor cursor) {
        Progress progress = new Progress();
        progress.tag = cursor.getString(cursor.getColumnIndex(Progress.TAG));
        progress.url = cursor.getString(cursor.getColumnIndex(Progress.URL));
        progress.folder = cursor.getString(cursor.getColumnIndex(Progress.FOLDER));
        progress.filePath = cursor.getString(cursor.getColumnIndex(Progress.FILE_PATH));
        progress.fileName = cursor.getString(cursor.getColumnIndex(Progress.FILE_NAME));
        progress.fraction = cursor.getFloat(cursor.getColumnIndex(Progress.FRACTION));
        progress.totalSize = cursor.getLong(cursor.getColumnIndex(Progress.TOTAL_SIZE));
        progress.currentSize = cursor.getLong(cursor.getColumnIndex(Progress.CURRENT_SIZE));
        progress.status = cursor.getInt(cursor.getColumnIndex(Progress.STATUS));
        progress.priority = cursor.getInt(cursor.getColumnIndex(Progress.PRIORITY));
        progress.date = cursor.getLong(cursor.getColumnIndex(Progress.DATE));
        progress.request = (Request<?, ? extends Request>) IOUtils.toObject(cursor.getBlob(cursor.getColumnIndex(Progress.REQUEST)));
        progress.extra1 = (Serializable) IOUtils.toObject(cursor.getBlob(cursor.getColumnIndex(Progress.EXTRA1)));
        progress.extra2 = (Serializable) IOUtils.toObject(cursor.getBlob(cursor.getColumnIndex(Progress.EXTRA2)));
        progress.extra3 = (Serializable) IOUtils.toObject(cursor.getBlob(cursor.getColumnIndex(Progress.EXTRA3)));
        return progress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Progress progress = (Progress) o;
        return tag != null ? tag.equals(progress.tag) : progress.tag == null;

    }

    @Override
    public int hashCode() {
        return tag != null ? tag.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Progress{" +
               "fraction=" + fraction +
               ", totalSize=" + totalSize +
               ", currentSize=" + currentSize +
               ", speed=" + speed +
               ", status=" + status +
               ", priority=" + priority +
               ", folder=" + folder +
               ", filePath=" + filePath +
               ", fileName=" + fileName +
               ", tag=" + tag +
               ", url=" + url +
               '}';
    }
}

package com.lzy.okgo.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.lzy.okgo.model.Progress;

import java.util.List;


public class UploadManager extends BaseDao<Progress> {

    private UploadManager() {
        super(new DBHelper());
    }

    public static UploadManager getInstance() {
        return UploadManagerHolder.instance;
    }

    private static class UploadManagerHolder {
        private static final UploadManager instance = new UploadManager();
    }

    @Override
    public Progress parseCursorToBean(Cursor cursor) {
        return Progress.parseCursorToBean(cursor);
    }

    @Override
    public ContentValues getContentValues(Progress progress) {
        return Progress.buildContentValues(progress);
    }

    @Override
    public String getTableName() {
        return DBHelper.TABLE_UPLOAD;
    }

    @Override
    public void unInit() {
    }


    public Progress get(String tag) {
        return queryOne(Progress.TAG + "=?", new String[]{tag});
    }


    public void delete(String taskKey) {
        delete(Progress.TAG + "=?", new String[]{taskKey});
    }


    public boolean update(Progress progress) {
        return update(progress, Progress.TAG + "=?", new String[]{progress.tag});
    }


    public boolean update(ContentValues contentValues, String tag) {
        return update(contentValues, Progress.TAG + "=?", new String[]{tag});
    }


    public List<Progress> getAll() {
        return query(null, null, null, null, null, Progress.DATE + " ASC", null);
    }


    public List<Progress> getFinished() {
        return query(null, "status=?", new String[]{Progress.FINISH + ""}, null, null, Progress.DATE + " ASC", null);
    }


    public List<Progress> getUploading() {
        return query(null, "status not in(?)", new String[]{Progress.FINISH + ""}, null, null, Progress.DATE + " ASC", null);
    }


    public boolean clear() {
        return deleteAll();
    }
}

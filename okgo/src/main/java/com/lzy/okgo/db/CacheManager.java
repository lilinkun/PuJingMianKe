package com.lzy.okgo.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.lzy.okgo.cache.CacheEntity;

import java.util.List;


public class CacheManager extends BaseDao<CacheEntity<?>> {

    public static CacheManager getInstance() {
        return CacheManagerHolder.instance;
    }

    private static class CacheManagerHolder {
        private static final CacheManager instance = new CacheManager();
    }

    private CacheManager() {
        super(new DBHelper());
    }

    @Override
    public CacheEntity<?> parseCursorToBean(Cursor cursor) {
        return CacheEntity.parseCursorToBean(cursor);
    }

    @Override
    public ContentValues getContentValues(CacheEntity<?> cacheEntity) {
        return CacheEntity.getContentValues(cacheEntity);
    }

    @Override
    public String getTableName() {
        return DBHelper.TABLE_CACHE;
    }

    @Override
    public void unInit() {
    }


    public CacheEntity<?> get(String key) {
        if (key == null) return null;
        List<CacheEntity<?>> cacheEntities = query(CacheEntity.KEY + "=?", new String[]{key});
        return cacheEntities.size() > 0 ? cacheEntities.get(0) : null;
    }


    public boolean remove(String key) {
        if (key == null) return false;
        return delete(CacheEntity.KEY + "=?", new String[]{key});
    }


    @SuppressWarnings("unchecked")
    public <T> CacheEntity<T> get(String key, Class<T> clazz) {
        return (CacheEntity<T>) get(key);
    }


    public List<CacheEntity<?>> getAll() {
        return queryAll();
    }


    public <T> CacheEntity<T> replace(String key, CacheEntity<T> entity) {
        entity.setKey(key);
        replace(entity);
        return entity;
    }


    public boolean clear() {
        return deleteAll();
    }
}

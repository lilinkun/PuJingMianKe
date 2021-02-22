package cn.com.pujing.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import cn.com.pujing.entity.DaoMaster;
import cn.com.pujing.entity.DaoSession;
import cn.com.pujing.entity.GridItem;
import cn.com.pujing.entity.GridItemDao;


public class DBManager {
    private final static String dbName = "pj_db";
    private static DBManager mInstance;
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;

    public DBManager(Context context) {
        this.context = context;
        openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
    }
    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static DBManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DBManager.class) {
                if (mInstance == null) {
                    mInstance = new DBManager(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }


    public void insertHomeTitle(GridItem gridItem){
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        GridItemDao gridItemDao = daoSession.getGridItemDao();
        gridItemDao.insert(gridItem);
    }

    public List<GridItem> queryHomeTitle(){
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        GridItemDao gridItemDao = daoSession.getGridItemDao();
        QueryBuilder<GridItem> qb = gridItemDao.queryBuilder();
        List<GridItem> list = qb.list();
        return list;
    }

    public void deleteHomeTitle(){
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        GridItemDao gridItemDao = daoSession.getGridItemDao();
        gridItemDao.deleteAll();
    }



}
package cn.com.pujing.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.service.quicksettings.Tile;

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


    /**
     * 插入首页功能按钮
     * @param gridItem
     */
    public void insertHomeTitle(GridItem gridItem){
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        GridItemDao gridItemDao = daoSession.getGridItemDao();
        gridItemDao.insert(gridItem);
    }

    /**
     * 取出首页功能按钮
     * @return
     */
    public List<GridItem> queryHomeTitle(){
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        GridItemDao gridItemDao = daoSession.getGridItemDao();
        QueryBuilder<GridItem> qb = gridItemDao.queryBuilder();
        List<GridItem> list = qb.list();
        return list;
    }

    /**
     * 模糊查询首页功能按钮
     * @return
     */
    public List<GridItem> queryHomeTitle(String title){
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        GridItemDao gridItemDao = daoSession.getGridItemDao();
        QueryBuilder<GridItem> qb = gridItemDao.queryBuilder().where(GridItemDao.Properties.Title.like("%" + title + "%"));
        List<GridItem> list = qb.list();
        return list;
    }

    /**
     * 删除首页功能按钮
     * @return
     */
    public void deleteHomeTitle(){
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        GridItemDao gridItemDao = daoSession.getGridItemDao();
        gridItemDao.deleteAll();
    }



}

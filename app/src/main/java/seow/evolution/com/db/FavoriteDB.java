package seow.evolution.com.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import seow.evolution.com.consts.DBConsts;
import seow.evolution.com.model.FavoriteItem;
import seow.evolution.com.util.DBHelper;

public class FavoriteDB extends DBHelper {
    private static final Object[] DB_LOCK 		= new Object[0];

    public FavoriteDB(Context context) {
        super(context);
    }

    public ArrayList<FavoriteItem> fetchAllFavorites() {
        ArrayList<FavoriteItem> ret = null;
        try {
            synchronized (DB_LOCK) {
                SQLiteDatabase db = getReadableDatabase();
                Cursor cursor = db.query(DBConsts.TBL_FAVORITE, null, null, null, null, null, null);
                ret = createFavoriteBeans(cursor);
                db.close();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }

        return ret;
    }

    public boolean isExist(FavoriteItem item) {
        boolean bExist = false;
        try {
            String szWhere = DBConsts.FIELD_TITLE + " = '" + item.getTitle() + "' AND " + DBConsts.FIELD_SLIDE_NO + " = " + item.getSlideNo();

            synchronized (DB_LOCK) {
                SQLiteDatabase db = getReadableDatabase();
                Cursor cursor = db.query(DBConsts.TBL_FAVORITE, null, szWhere, null, null, null, null);
                if(cursor.moveToNext())
                    bExist = true;
                db.close();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }

        return bExist;
    }

    public long addFavorite(FavoriteItem bean) {
        long ret = -1;
        if(!isExist(bean)) {
            try {
                ContentValues value = new ContentValues();
                value.put(DBConsts.FIELD_CONTENT_ID, bean.getContentID());
                value.put(DBConsts.FIELD_TITLE, bean.getTitle());
                value.put(DBConsts.FIELD_SLIDE_NO, bean.getSlideNo());
                synchronized (DB_LOCK) {
                    SQLiteDatabase db = getWritableDatabase();
                    ret = db.insert(DBConsts.TBL_FAVORITE, null, value);
                    db.close();
                }
            } catch (IllegalStateException ex) {
                ex.printStackTrace();
            }
        }

        return ret;
    }

    public void removeAllDatas() {
        try {
            synchronized (DB_LOCK) {
                SQLiteDatabase db = getReadableDatabase();
                db.delete(DBConsts.TBL_FAVORITE, null, null);
                db.close();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
    }

    public void removeItem(int id) {
        try {
            String szWhere = DBConsts.FIELD_ID + " = " + id;
            synchronized (DB_LOCK) {
                SQLiteDatabase db = getReadableDatabase();
                db.delete(DBConsts.TBL_FAVORITE, szWhere, null);
                db.close();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
    }

    private ArrayList<FavoriteItem> createFavoriteBeans(Cursor c) {
        ArrayList<FavoriteItem> ret = null;
        try {
            ret = new ArrayList();

            final int COL_ID	            = c.getColumnIndexOrThrow(DBConsts.FIELD_ID),
                    COL_CONTENT_ID     	    = c.getColumnIndexOrThrow(DBConsts.FIELD_CONTENT_ID),
                    COL_TITLE        		= c.getColumnIndexOrThrow(DBConsts.FIELD_TITLE),
                    COL_SLIDE_NO            = c.getColumnIndexOrThrow(DBConsts.FIELD_SLIDE_NO);

            while (c.moveToNext()) {
                FavoriteItem bean = new FavoriteItem();
                bean.setId(c.getInt(COL_ID));
                bean.setContentID(c.getInt(COL_CONTENT_ID));
                bean.setTitle(c.getString(COL_TITLE));
                bean.setSlideNo(c.getInt(COL_SLIDE_NO));
                ret.add(bean);
            }

            c.close();
            getReadableDatabase().close();
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }

        return ret;
    }
}

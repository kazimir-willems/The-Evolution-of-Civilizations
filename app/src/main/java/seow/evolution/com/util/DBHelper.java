package seow.evolution.com.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import seow.evolution.com.consts.DBConsts;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME_PREFIX = "THE_EOC_DB";
    private static final int DB_VERSION = 2;

    protected static String STAFF_TABLE_CREATE_SQL =
            "CREATE TABLE IF NOT EXISTS " + DBConsts.TBL_FAVORITE + " (" +
                    DBConsts.FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DBConsts.FIELD_CONTENT_ID + " INTEGER," +
                    DBConsts.FIELD_TITLE + " TEXT," +
                    DBConsts.FIELD_SLIDE_NO + " INTEGER);";


    public DBHelper(Context context) {
        super(context, DB_NAME_PREFIX, null, DB_VERSION);
        this.getWritableDatabase().close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(STAFF_TABLE_CREATE_SQL);
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            onCreate(db);
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
    }
}

package seow.evolution.com.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "EvolutionPreference";
    private static final String TAG_LAST_SLIDE = "last_slide";
    private static final String TAG_BOOKMARK_SLIDE = "bookmark_slide";
    private static final String TAG_READING = "reading";
    private static final String TAG_FONT_SIZE = "fontsize";
    private static final String TAG_CONTENT_ID = "content_id";
    private static final String TAG_TITLE = "title";
    private static final String TAG_ADMOB_SWITCH = "admob_switch";
    private static final String TAG_INTERESTIAL_SWITCH = "interestial_switch";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean saveContentID(int id){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(TAG_CONTENT_ID, id);
        editor.apply();
        return true;
    }

    public int getContentID(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getInt(TAG_CONTENT_ID, 0);
    }

    public boolean saveLastSlide(int slide){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(TAG_LAST_SLIDE, slide);
        editor.apply();
        return true;
    }

    public int getLastSlide(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getInt(TAG_LAST_SLIDE, 0);
    }

    public boolean saveTitle(String title){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAG_TITLE, title);
        editor.apply();
        return true;
    }

    public String getTitle(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_TITLE, "");
    }

    public boolean saveBookmarkSlide(int slide){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(TAG_BOOKMARK_SLIDE, slide);
        editor.apply();
        return true;
    }

    public int getBookmarkSlide(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getInt(TAG_BOOKMARK_SLIDE, 0);
    }

    public boolean saveReading(boolean isReading){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(TAG_READING, isReading);
        editor.apply();
        return true;
    }

    public boolean getReading(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getBoolean(TAG_READING, false);
    }

    public boolean saveFontSize(int fontsize){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(TAG_FONT_SIZE, fontsize);
        editor.apply();
        return true;
    }

    public int getFontSize(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getInt(TAG_FONT_SIZE, 3);
    }

    public boolean saveGoogleAds(boolean value){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(TAG_ADMOB_SWITCH, value);
        editor.apply();
        return true;
    }

    public boolean getGoogleAds(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getBoolean(TAG_ADMOB_SWITCH, true);
    }

    public boolean saveGoogleInterestial(boolean value){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(TAG_INTERESTIAL_SWITCH, value);
        editor.apply();
        return true;
    }

    public boolean getGoogleInterestial(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getBoolean(TAG_INTERESTIAL_SWITCH, true);
    }
}
package seow.evolution.com;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

public class EvolutionApplication extends Application {
    private static EvolutionApplication instance;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

    }

    public static synchronized EvolutionApplication getInstance() {
        return instance;
    }
}
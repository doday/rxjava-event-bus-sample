package com.doday.rxjavaeventbussample;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.doday.rxjavaeventbussample.BuildConfig;
import com.facebook.stetho.Stetho;

/**
 * Extension de la classe {@link Application} appliquant une configuration spécifique en mode DEBUG.
 */
public class MyApplication extends Application {

    private static Context mContext;

    public static Context getAppContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initStrictMode();
        initStetho();

        mContext = getApplicationContext();
    }

    private void initStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }


    protected void initStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedClosableObjects()
                    //.detectLeakedRegistrationObjects()
                    .detectLeakedSqlLiteObjects()
                    .penaltyLog()
                    .build());
        }
    }

}

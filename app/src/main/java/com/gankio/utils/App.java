package com.gankio.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by gaoyongxing on 2018-11-21.
 */
public class App extends Application {
    private static App app;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
    public static App getInstance() {
        return app;
    }
}

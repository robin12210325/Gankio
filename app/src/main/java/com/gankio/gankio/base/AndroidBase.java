package com.gankio.gankio.base;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * Created by gaoyongxing on 2018-11-22.
 */
public interface AndroidBase {
    public interface BaseView<T extends BasePresenter>{
        void showDialog();
        void dismissDialog();
        void setPresenter(T presenter);
    }
    public interface BasePresenter extends LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        void onCreate();
        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        void onDestory();
    }
}

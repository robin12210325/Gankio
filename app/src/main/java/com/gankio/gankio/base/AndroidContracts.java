package com.gankio.gankio.base;

import com.gankio.gankio.model.AndroidModel;

/**
 * Created by gaoyongxing on 2018-11-22.
 */
public interface AndroidContracts {
    public interface View extends AndroidBase.BaseView<Presenter> {
        void showData(AndroidModel string);
    }
    public interface Presenter extends AndroidBase.BasePresenter{
        void getAndroidData(String type,String pageCount, int pageNum);
    }
}

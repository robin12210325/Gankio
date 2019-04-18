package com.gankio.utils;

import com.gankio.gankio.model.AndroidModel;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

public class DiffUtils extends DiffUtil.Callback{
    List<AndroidModel.ResultsBean> mNewDatas,mOldDatas;
    public DiffUtils(List<AndroidModel.ResultsBean> mNewDatas,List<AndroidModel.ResultsBean> mOldDatas){
        this.mNewDatas = mNewDatas;
        this.mOldDatas = mOldDatas;

    }
    @Override
    public int getOldListSize() {
        return mOldDatas.size();
    }

    @Override
    public int getNewListSize() {
        return mNewDatas.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        if (!mOldDatas.get(oldItemPosition).get_id().equals(mNewDatas.get(newItemPosition).get_id())){
            //如果内容不同 就返回false
            return false;
        }
        return true;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }
}

package com.gankio.utils;

import androidx.annotation.Nullable;

public abstract class CallBack {
    public abstract int getOldListSize();//老数据集size

    public abstract int getNewListSize();//新数据集size

    public abstract boolean areItemsTheSame(int oldItemPosition, int newItemPosition);//新老数据集在同一个postion的Item是否是一个对象？（可能内容不同，如果这里返回true，会调用下面的方法）

    public abstract boolean areContentsTheSame(int oldItemPosition, int newItemPosition);//这个方法仅仅是上面方法返回ture才会调用，我的理解是只有notifyItemRangeChanged()才会调用，判断item的内容是否有变化

    //该方法在DiffUtil高级用法中用到 ，暂且不提
    @Nullable
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return null;
    }
}

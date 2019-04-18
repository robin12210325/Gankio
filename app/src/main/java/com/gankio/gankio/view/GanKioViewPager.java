package com.gankio.gankio.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class GanKioViewPager extends ViewPager {


    public GanKioViewPager(@NonNull Context context) {
        super(context);
    }

    public GanKioViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (this.getCurrentItem() < this.getChildCount()-1){
            System.out.println("GanKioViewPager=" + this.getCurrentItem() + "=" + this.getChildCount());
            this.getParent().requestDisallowInterceptTouchEvent(true);
        }else{
            this.getParent().requestDisallowInterceptTouchEvent(false);
        }
        return super.onTouchEvent(ev);
    }
}

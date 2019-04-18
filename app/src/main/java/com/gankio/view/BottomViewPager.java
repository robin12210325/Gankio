package com.gankio.view;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class BottomViewPager extends FragmentPagerAdapter {
    List<Fragment> list;
    public BottomViewPager(FragmentManager fm,List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list!=null?list.size():0;
    }
}

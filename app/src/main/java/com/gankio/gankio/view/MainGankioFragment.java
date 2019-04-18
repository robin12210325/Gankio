package com.gankio.gankio.view;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.gankio.R;
import com.gankio.gankio.adapter.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainGankioFragment extends Fragment {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    GanKioViewPager viewpager;
    private Unbinder unbinder;

    private List<Fragment> fragmentList;
    private List<String> titles;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_main_gankio, container, false);
        View view = inflater.inflate(R.layout.fragment_main_gankio, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
//        initFragment();
        return view;
    }

    private void initFragment() {
        fragmentList = new ArrayList<>();

        fragmentList.add(AndroidFragment.newInstance("Android"));
        fragmentList.add(AndroidFragment.newInstance("iOS"));
        fragmentList.add(AndroidFragment.newInstance("福利"));
        fragmentList.add(AndroidFragment.newInstance("休息视频"));
        fragmentList.add(AndroidFragment.newInstance("拓展资源"));
        fragmentList.add(AndroidFragment.newInstance("福利"));
        FragmentAdapter adapter = new FragmentAdapter(getActivity().getSupportFragmentManager(), fragmentList, titles);
        viewpager.setAdapter(adapter);

    }


    private void initView() {
        titles = new ArrayList<>();
        titles.add("Android");
        titles.add("IOS");
        titles.add("福利");
        titles.add("休息视频");
        titles.add("拓展资源");
        titles.add("前端");
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
                System.out.println("tab= " + tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.setupWithViewPager(viewpager);
        initFragment();
    }


    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }
}

package com.gankio.view;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.gankio.R;
import com.gankio.base.BaseView;
import com.gankio.gankio.view.AndroidFragment;
import com.gankio.gankio.view.MainGankioFragment;
import com.gankio.guoke.GuokeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomView extends BaseView{
    List<Fragment> list = new ArrayList<>();
    private MenuItem menuItem;
    @BindView(R.id.bottomContent)
    LinearLayout bottomContent;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.BottomNavigationView)
    com.google.android.material.bottomnavigation.BottomNavigationView BottomNavigationView;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_view);
        ButterKnife.bind(this);
        list.add(new MainGankioFragment());
//        list.add(new AndroidFragment());
        list.add(new GuokeFragment());
        list.add(new AndroidFragment());
        BottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomViewPager adapter = new BottomViewPager(getSupportFragmentManager(), list);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    BottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = BottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}

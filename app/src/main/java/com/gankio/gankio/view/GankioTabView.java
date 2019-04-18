package com.gankio.gankio.view;

import android.os.Bundle;
import android.view.ViewGroup;

import com.gankio.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GankioTabView extends AppCompatActivity {

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.tabViewPager)
    ViewPager tabViewPager;

    List<Fragment> list = new ArrayList<>();
    List<String> listTitles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gankio_tab_view);
        ButterKnife.bind(this);
        initFragment();
    }

    private void initFragment() {
        list.add(new AndroidFragment());
        list.add(new AndroidFragment());
        list.add(new AndroidFragment());
        list.add(new AndroidFragment());
        listTitles.add("C");
        listTitles.add("java");
        listTitles.add("flutter");
        listTitles.add("android");
        tabViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
//                return super.getPageTitle(position);
                return listTitles.get(position);
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                super.destroyItem(container, position, object);
            }
        });
        tabs.setupWithViewPager(tabViewPager);
    }
}

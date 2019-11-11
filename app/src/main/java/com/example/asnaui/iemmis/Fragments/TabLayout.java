package com.example.asnaui.iemmis.Fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asnaui.iemmis.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asnaui on 1/17/2018.
 */

public class TabLayout extends Fragment {
    public static android.support.design.widget.TabLayout tableLayout;
    ViewPager viewPager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.tablelayout_logs, null, false);
        tableLayout = view.findViewById(R.id.table_layout);
        viewPager = view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tableLayout.setupWithViewPager(viewPager);
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        MyAdapter adapter = new MyAdapter(getFragmentManager());
        adapter.addFrag(new ServiceRequest(), "Request ");
        adapter.addFrag(new JobReport(), "Report");
        viewPager.setAdapter(adapter);
    }

    static class MyAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

}

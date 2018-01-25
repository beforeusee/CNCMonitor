package com.example.zhengxiaohu.cncmonitor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by XiaoHu Zheng on 2017/5/16.
 * Email:1050087728@qq.com
 * FragmentPagerAdapter,fragmentPager的适配器
 */

public class CNCMonitorFragmentPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<String> mTabList;
    private ArrayList<Fragment> mFragments;

    public CNCMonitorFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public CNCMonitorFragmentPagerAdapter(FragmentManager fm, ArrayList<String> tabList,ArrayList<Fragment> fragments ){
        super(fm);
        this.mTabList=tabList;
        this.mFragments=fragments;
    }
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mTabList.size();
    }
}

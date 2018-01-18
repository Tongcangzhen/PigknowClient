package com.example.ldjg.pigknowclient;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by ldjg on 2017/12/27.
 */

public class MyPageAdapter extends FragmentStatePagerAdapter {

    public MyPageAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        if (position == 0) {
            fragment=ItemFragment.newInstance(1,0);
        }
        if (position == 1) {
            fragment=ItemFragment.newInstance(1,2);
        }
        if (position == 2) {
            fragment=ItemFragment.newInstance(1,1);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position){
        String name = null;
        if (position == 0) {
            name= "待审核";
        }
        if (position == 1) {
            name="未通过";
        }
        if (position == 2) {
            name="审核通过";
        }
        return name;
    }
}

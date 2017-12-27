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
            fragment=new TestBlankFragment();
        }
        if (position == 1) {
            fragment=new TestBlankFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position){
        String name = null;
        if (position == 0) {
            name= "待审核";
        }
        if (position == 1) {
            name="已审核";
        }
        return name;
    }
}

package com.web.whatashot.adapters;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


import com.web.whatashot.fragments.CommonFragment;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class CurrencyPagerAdapter extends FragmentStatePagerAdapter {


    int mNumOfTabs;
    public CurrencyPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;


     }
    @Override
    public Fragment getItem(int position) {

        CommonFragment tab1 = new CommonFragment();
        Bundle bundle=new Bundle();
        bundle.putString("pos",position+"");
        tab1.setArguments(bundle);
        return tab1;


    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
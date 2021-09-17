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
       //bundle.putString("usdtAr",usdtAr+"");
        bundle.putString("pos",position+"");
        tab1.setArguments(bundle);
        return tab1;



//        switch (position)
//           {
//            case 0:
//                USDTFragment tab1 = new USDTFragment();
//                Bundle bundle=new Bundle();
//                bundle.putString("usdtAr",usdtAr+"");
//                tab1.setArguments(bundle);
//                return tab1;
//
//            case 1:
//                INRFragment tab3 = new INRFragment();
//                Bundle bundle3=new Bundle();
//                bundle3.putString("inrAr",inrAr+"");
//                tab3.setArguments(bundle3);
//                return tab3;
//
//
//            case 2:
//                BTCFragment tab2 = new BTCFragment();
//                Bundle bundle2=new Bundle();
//                bundle2.putString("btcAr",btcAr+"");
//                tab2.setArguments(bundle2);
//                return tab2;
//
//            default:
//                return null;
//        }
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
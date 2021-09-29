package com.web.whatashot.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.web.whatashot.fragments.FundFragment;
import com.web.whatashot.fragments.TransactionHistoryFragment;
import com.web.whatashot.orderpackage.HistoryFragment;
import com.web.whatashot.orderpackage.OpenOrderFragment;


public class FundPagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    public FundPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }
    @Override
    public Fragment getItem(int position)
     {
        switch (position) {
            case 0:
                FundFragment tab1 = new FundFragment();
                return tab1;

            case 1:
                TransactionHistoryFragment tab2 = new TransactionHistoryFragment();
                return tab2;

            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
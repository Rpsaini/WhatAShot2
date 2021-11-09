package com.web.whatashot.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.web.whatashot.fragments.DepositHistoryFragment;
import com.web.whatashot.fragments.FundFragment;
import com.web.whatashot.fragments.TransactionHistoryFragment;
import com.web.whatashot.fragments.WithdrawHistoryFrg;
import com.web.whatashot.orderpackage.HistoryFragment;
import com.web.whatashot.orderpackage.OpenOrderFragment;

import android.os.Bundle;



import org.json.JSONArray;


public class FundPagerAdapter extends FragmentStatePagerAdapter {
    private JSONArray depositAr,withdrawAr;
    int mNumOfTabs;
    public FundPagerAdapter(FragmentManager fm, int NumOfTabs, JSONArray depositAr,JSONArray withdrawAr) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.depositAr=depositAr;
        this.withdrawAr=withdrawAr;
    }
    @Override
    public Fragment getItem(int position)
    {
        switch (position) {
            case 0:
                DepositHistoryFragment tab1 = new DepositHistoryFragment();
                Bundle bundle=new Bundle();
                bundle.putString("d_fund",depositAr+"");
                tab1.setArguments(bundle);
                return tab1;

            case 1:
                WithdrawHistoryFrg tab2 = new WithdrawHistoryFrg();
                Bundle w_bundle=new Bundle();
                w_bundle.putString("w_fund",withdrawAr+"");
                tab2.setArguments(w_bundle);

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

//
//
//public class FundPagerAdapter extends FragmentStatePagerAdapter {
//
//    int mNumOfTabs;
//    public FundPagerAdapter(FragmentManager fm, int NumOfTabs) {
//        super(fm);
//        this.mNumOfTabs = NumOfTabs;
//    }
//    @Override
//    public Fragment getItem(int position)
//     {
//        switch (position) {
//            case 0:
//                FundFragment tab1 = new FundFragment();
//                return tab1;
//
//            case 1:
//                TransactionHistoryFragment tab2 = new TransactionHistoryFragment();
//                return tab2;
//
//            default:
//                return null;
//        }
//    }
//    @Override
//    public int getCount() {
//        return mNumOfTabs;
//    }
//}
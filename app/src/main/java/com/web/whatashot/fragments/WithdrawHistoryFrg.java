package com.web.whatashot.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.web.whatashot.FundHistoryActivity;
import com.web.whatashot.R;
import com.web.whatashot.adapters.TransactionHistoryAdapter;
import org.json.JSONArray;


public class WithdrawHistoryFrg extends Fragment {

    private View view;
    public WithdrawHistoryFrg() {

    }

    public static WithdrawHistoryFrg newInstance(String param1, String param2) {
        WithdrawHistoryFrg fragment = new WithdrawHistoryFrg();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            view=inflater.inflate(R.layout.fragment_withdraw_history_frg, container, false);
            init(new JSONArray(getArguments().getString("w_fund")));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        return view;
    }

    private void init(JSONArray dataAr)
    {
        RecyclerView recycler_view_market = view.findViewById(R.id.transaction_history_recycler);
        RelativeLayout relativeLayout = view.findViewById(R.id.rr_nodata_view);

        if (dataAr.length() <=0) {
            relativeLayout.setVisibility(View.VISIBLE);
            recycler_view_market.setVisibility(View.GONE);
        } else {
            relativeLayout.setVisibility(View.GONE);
            recycler_view_market.setVisibility(View.VISIBLE);
        }

        TransactionHistoryAdapter mAdapter = new TransactionHistoryAdapter(dataAr, (FundHistoryActivity)getActivity(),"withdraw");
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recycler_view_market.setLayoutManager(horizontalLayoutManagaer);
        recycler_view_market.setItemAnimator(new DefaultItemAnimator());
        recycler_view_market.setAdapter(mAdapter);

    }
}
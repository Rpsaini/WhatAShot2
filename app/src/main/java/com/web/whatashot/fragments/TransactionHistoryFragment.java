package com.web.whatashot.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.web.whatashot.DefaultConstants;
import com.web.whatashot.FundHistoryActivity;
import com.web.whatashot.MainActivity;
import com.web.whatashot.R;
import com.web.whatashot.adapters.OrderDetailsAdapter;
import com.web.whatashot.adapters.TransactionHistoryAdapter;
import com.web.whatashot.pairdetailfragments.PairDetailView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TransactionHistoryFragment extends Fragment {


    private View view;

    public TransactionHistoryFragment() {
        // Required empty public constructor
    }


    public static TransactionHistoryFragment newInstance(String param1, String param2) {
        TransactionHistoryFragment fragment = new TransactionHistoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      try {
          view = inflater.inflate(R.layout.fragment_transaction_history, container, false);
          init(new JSONArray(getArguments().getString("d_fund")));
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

//        TransactionHistoryAdapter mAdapter = new TransactionHistoryAdapter(new ArrayList<>(), (Depo)getActivity());
//        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        recycler_view_market.setLayoutManager(horizontalLayoutManagaer);
//        recycler_view_market.setItemAnimator(new DefaultItemAnimator());
//        recycler_view_market.setAdapter(mAdapter);


        TransactionHistoryAdapter mAdapter = new TransactionHistoryAdapter(dataAr, (FundHistoryActivity)getActivity(),"deposite");
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recycler_view_market.setLayoutManager(horizontalLayoutManagaer);
        recycler_view_market.setItemAnimator(new DefaultItemAnimator());
        recycler_view_market.setAdapter(mAdapter);

    }
}

package com.web.whatashot.payment_option.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.web.whatashot.R;

public class BankAccountDetailsFragment extends Fragment {


    public BankAccountDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bank_acc_detail_fragment, container, false);

        return view;
    }
}

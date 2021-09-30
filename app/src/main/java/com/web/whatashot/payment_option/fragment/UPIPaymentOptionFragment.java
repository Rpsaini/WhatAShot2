package com.web.whatashot.payment_option.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.web.whatashot.R;

public class UPIPaymentOptionFragment extends Fragment {


    public UPIPaymentOptionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.payment_upi_fragment, container, false);

        return view;
    }
}

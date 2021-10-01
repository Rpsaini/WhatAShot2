package com.web.whatashot.search_currency;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.web.whatashot.BaseActivity;
import com.web.whatashot.R;
import com.web.whatashot.search_currency.adapter.SearchCurrencyAdapter;

public class SearchCurrencyScreen extends BaseActivity {
    private ImageView backIC;
    private RecyclerView activityLogsRV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_currency_screen);

        initView();
        setOnClickListener();
        setAdapterData();
    }

    private void initView(){
        backIC=findViewById(R.id.backIC);
        activityLogsRV=findViewById(R.id.currency_search_rv);
    }
    private void setAdapterData(){
        SearchCurrencyAdapter mAdapter = new SearchCurrencyAdapter(SearchCurrencyScreen.this);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(SearchCurrencyScreen.this, LinearLayoutManager.VERTICAL, false);
        activityLogsRV.setLayoutManager(horizontalLayoutManager);
        activityLogsRV.setItemAnimator(new DefaultItemAnimator());
        activityLogsRV.setAdapter(mAdapter);
    }
    private void setOnClickListener(){
        backIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

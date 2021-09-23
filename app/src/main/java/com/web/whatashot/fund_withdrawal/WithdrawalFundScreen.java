package com.web.whatashot.fund_withdrawal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.web.whatashot.BaseActivity;
import com.web.whatashot.R;

public class WithdrawalFundScreen extends BaseActivity {
    private TextView proceedBT=null;
    private ImageView backIC=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.withdraw_screen);
        init();
        setOnClickListener();
    }

    private void init(){
        proceedBT=findViewById(R.id.proceedBT);
        backIC=findViewById(R.id.backIC);
    }

    private void setOnClickListener(){
        backIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        proceedBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WithdrawalFundScreen.this,ConfirmWithdrawalFundScreen.class);
                startActivity(intent);
            }
        });
    }
}

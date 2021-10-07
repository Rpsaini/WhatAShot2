package com.web.whatashot.kyc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.web.whatashot.BaseActivity;
import com.web.whatashot.R;

public class VerifyKycAccountDetailsScreen extends BaseActivity {

    public static VerifyKycAccountDetailsScreen mVerifyKycAccountDetailsScreen;
    private TextView submitVerifyBT=null;
    private ImageView backIC=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_submit_account_screen);
        mVerifyKycAccountDetailsScreen=this;
        initView();
        setOnClickListener();
    }

    private void initView(){
        submitVerifyBT=findViewById(R.id.submitVerifyBT);
        backIC=findViewById(R.id.backIC);
    }

    private void setOnClickListener(){
        submitVerifyBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(mVerifyKycAccountDetailsScreen, VerifyCompleteSubmitKycScreen.class);
                startActivity(intent);
            }
        });

        backIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

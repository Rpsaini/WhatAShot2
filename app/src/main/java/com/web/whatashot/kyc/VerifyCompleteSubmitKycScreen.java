package com.web.whatashot.kyc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.web.whatashot.BaseActivity;
import com.web.whatashot.R;

public class VerifyCompleteSubmitKycScreen extends BaseActivity {
    private ImageView backIc=null;
    private TextView completeKycBt=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_complete_kyc_screen);
        initView();
        setOnClickListener();
    }

    private void initView(){
        backIc=findViewById(R.id.backIC);
        completeKycBt=findViewById(R.id.complet_kycBT);
    }

    private void setOnClickListener(){
        backIc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        completeKycBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(VerifyCompleteSubmitKycScreen.this,VerifyKycForPersonalInfoScreen.class);
                startActivity(intent);
            }
        });

    }
}

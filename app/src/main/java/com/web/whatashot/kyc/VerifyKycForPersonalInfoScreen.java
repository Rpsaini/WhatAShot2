package com.web.whatashot.kyc;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.web.whatashot.BaseActivity;
import com.web.whatashot.R;

public class VerifyKycForPersonalInfoScreen extends BaseActivity {
    private ImageView backIc=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_profile_details_screen);
        initView();
        setOnClickListener();
    }
    private void initView(){
        backIc=findViewById(R.id.backIC);

    }
    private void setOnClickListener(){
        backIc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

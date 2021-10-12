package com.web.whatashot.kyc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.web.whatashot.BaseActivity;
import com.web.whatashot.DefaultConstants;
import com.web.whatashot.R;

public class VerifyKycAccountDetailsScreen extends BaseActivity {

    public static VerifyKycAccountDetailsScreen mVerifyKycAccountDetailsScreen;
    private TextView submitVerifyBT=null,review_tv,done_tv;
    private ImageView backIC=null,submit_line,review_line;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_submit_account_screen);
        mVerifyKycAccountDetailsScreen=this;
        initiateObj();
        initView();
        setOnClickListener();
    }

    private void initView(){
        submitVerifyBT=findViewById(R.id.submitVerifyBT);
        review_tv=findViewById(R.id.review_tv);
        done_tv=findViewById(R.id.done_tv);
        submit_line=findViewById(R.id.submit_line);
        review_line=findViewById(R.id.review_line);
        backIC=findViewById(R.id.backIC);
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeHeaderLayoutColorByKycStatus();
    }

    private void changeHeaderLayoutColorByKycStatus(){
      String kycStatus=  savePreferences.reterivePreference(mVerifyKycAccountDetailsScreen, DefaultConstants.kyc_status).toString();
      if(kycStatus.equals("1")){
          submitVerifyBT.setVisibility(View.GONE);
          submit_line.setBackground(getResources().getDrawable(R.drawable.ic_select_line));
          review_line.setBackground(getResources().getDrawable(R.drawable.ic_line));
          review_tv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_select_review, 0, 0);
          done_tv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_done, 0, 0);


      }
      else if(kycStatus.equals("2")){
          submitVerifyBT.setVisibility(View.GONE);
          review_tv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_select_review, 0, 0);
          done_tv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_select_done, 0, 0);

          review_line.setBackground(getResources().getDrawable(R.drawable.ic_select_line));
          submit_line.setBackground(getResources().getDrawable(R.drawable.ic_select_line));

      }
      else if(kycStatus.equals("3")){
          review_line.setBackground(getResources().getDrawable(R.drawable.ic_line));
          submit_line.setBackground(getResources().getDrawable(R.drawable.ic_line));
          submitVerifyBT.setVisibility(View.VISIBLE);
          review_tv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_review, 0, 0);
          done_tv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_done, 0, 0);

      }
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

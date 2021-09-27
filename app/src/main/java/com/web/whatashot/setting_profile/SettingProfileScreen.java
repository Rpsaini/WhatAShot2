package com.web.whatashot.setting_profile;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.app.dialogsnpickers.SimpleDialog;
import com.web.whatashot.BaseActivity;
import com.web.whatashot.R;
import com.web.whatashot.kyc.VerifyKycAccountDetailsScreen;

public class SettingProfileScreen extends BaseActivity {
    private ImageView backIC=null;
    private RelativeLayout verify_kyc_layout=null;
    private Dialog mobRegDialog;
    private Dialog mobRegOtpDialog;
    private Dialog mobRegSuccessDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_profile_screen);

        initView();
        setOnClickListener();
    }

    private void initView(){
        backIC =findViewById(R.id.backIC);
        verify_kyc_layout=findViewById(R.id.verify_kyc_layout);
    }

    private void setOnClickListener(){
        backIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        verify_kyc_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SettingProfileScreen.this, VerifyKycAccountDetailsScreen.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.mobileValueTV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMobileRegDialog();
            }
        });
    }
    private void showMobileRegDialog() {
        try
        {
            SimpleDialog simpleDialog = new SimpleDialog();
            mobRegDialog = simpleDialog.simpleDailog(this, R.layout.mobile_reg_dialog, new ColorDrawable(getResources().getColor(R.color.translucent_black)), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, false);
            ConstraintLayout root_layout = mobRegDialog.findViewById(R.id.root_layout);
            TextView send_otpTV = mobRegDialog.findViewById(R.id.send_otpTV);
            ImageView closeIC = mobRegDialog.findViewById(R.id.closeIC);

            mobRegDialog.setCancelable(true);
            closeIC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mobRegDialog.dismiss();
                }
            });
            root_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mobRegDialog.dismiss();
                }
            });
            send_otpTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    mobRegDialog.dismiss();
                    showMobileOtpRegDialog();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showMobileOtpRegDialog() {
        try
        {
            SimpleDialog simpleDialog = new SimpleDialog();
            mobRegOtpDialog = simpleDialog.simpleDailog(this, R.layout.mobile_reg_otp_dialog, new ColorDrawable(getResources().getColor(R.color.translucent_black)), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, false);
            ConstraintLayout root_layout = mobRegOtpDialog.findViewById(R.id.root_layout);
            TextView authenticatTV = mobRegOtpDialog.findViewById(R.id.authenticatTV);
            ImageView closeIC = mobRegOtpDialog.findViewById(R.id.closeIC);

            mobRegOtpDialog.setCancelable(true);
            closeIC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mobRegOtpDialog.dismiss();
                }
            });
            root_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mobRegOtpDialog.dismiss();
                }
            });
            authenticatTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    mobRegOtpDialog.dismiss();
                    showMobileRegSuccessDialog();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void showMobileRegSuccessDialog() {
        try
        {
            SimpleDialog simpleDialog = new SimpleDialog();
            mobRegSuccessDialog = simpleDialog.simpleDailog(this, R.layout.mobile_reg_otp_successfully_dialog, new ColorDrawable(getResources().getColor(R.color.translucent_black)), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, false);
            ConstraintLayout root_layout = mobRegSuccessDialog.findViewById(R.id.root_layout);
            TextView continueTV = mobRegSuccessDialog.findViewById(R.id.continueTV);
            ImageView closeIC = mobRegSuccessDialog.findViewById(R.id.closeIC);

            mobRegSuccessDialog.setCancelable(true);
            closeIC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mobRegSuccessDialog.dismiss();
                }
            });
            root_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mobRegSuccessDialog.dismiss();
                }
            });
            continueTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    mobRegSuccessDialog.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

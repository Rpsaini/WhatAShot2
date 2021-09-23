package com.web.whatashot.fund_withdrawal;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.app.dialogsnpickers.SimpleDialog;
import com.web.whatashot.BaseActivity;
import com.web.whatashot.R;

import java.util.ArrayList;

public class ConfirmWithdrawalFundScreen extends BaseActivity {
    private ImageView backIC=null;
    private TextView confirmBT=null;
    private Dialog confirmDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.witdraw_confrim_screen);
        initView();
        setOnClickListener();
    }

    private void initView(){
        backIC=findViewById(R.id.backIC);
        confirmBT=findViewById(R.id.confirmBT);
    }

    private void setOnClickListener(){
        backIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        confirmBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmWithdrawFundDialog();
            }
        });
    }
    private void confirmWithdrawFundDialog() {
        try
        { SimpleDialog simpleDialog = new SimpleDialog();
            confirmDialog = simpleDialog.simpleDailog(this, R.layout.confirm_dialog, new ColorDrawable(getResources().getColor(R.color.translucent_black)), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, false);
            ConstraintLayout root_layout = confirmDialog.findViewById(R.id.root_layout);
            TextView txtConfirm = confirmDialog.findViewById(R.id.txtConfirm);

            confirmDialog.setCancelable(true);
            root_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmDialog.dismiss();
                }
            });
            txtConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmDialog.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

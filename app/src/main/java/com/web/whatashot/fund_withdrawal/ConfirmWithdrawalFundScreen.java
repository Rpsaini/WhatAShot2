package com.web.whatashot.fund_withdrawal;

import android.app.Dialog;
import android.content.Intent;
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

import com.app.dialogsnpickers.DialogCallBacks;
import com.app.dialogsnpickers.SimpleDialog;
import com.app.vollycommunicationlib.CallBack;
import com.app.vollycommunicationlib.ServerHandler;
import com.web.whatashot.BaseActivity;
import com.web.whatashot.DefaultConstants;
import com.web.whatashot.R;
import com.web.whatashot.utilpackage.UtilClass;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConfirmWithdrawalFundScreen extends BaseActivity
{
    private ImageView backIC=null;
    private TextView confirmBT=null;
    private Dialog confirmDialog;
    String destinationAddressET,currenyAmount,feeapplicable,total,remarks,symbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.witdraw_confrim_screen);
        initiateObj();
        initView();
        setOnClickListener();
    }

    private void initView()
    {
        backIC=findViewById(R.id.backIC);
        confirmBT=findViewById(R.id.confirmBT);


         destinationAddressET= getIntent().getStringExtra(DefaultConstants.destinationAddressET);
         currenyAmount= getIntent().getStringExtra(DefaultConstants.currenyAmount);
         feeapplicable=getIntent().getStringExtra(DefaultConstants.feeapplicable);
         total=getIntent().getStringExtra(DefaultConstants.total);
         remarks=getIntent().getStringExtra(DefaultConstants.remarks);
         symbol=getIntent().getStringExtra(DefaultConstants.symbol);

        TextView destinationAddressValueTV=findViewById(R.id.destinationAddressValueTV);
        TextView BTCAmountValueTV=findViewById(R.id.BTCAmountValueTV);
        TextView withdrawalFeesValueTV=findViewById(R.id.withdrawalFeesValueTV);
        TextView finalAmountTV=findViewById(R.id.finalAmountTV);
        TextView remarksValueTV=findViewById(R.id.remarksValueTV);

        destinationAddressValueTV.setText(destinationAddressET);
        BTCAmountValueTV.setText(currenyAmount);
        withdrawalFeesValueTV.setText(feeapplicable);
        finalAmountTV.setText(total);
        remarksValueTV.setText(remarks);


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
                public void onClick(View v)
                {
                    withdrawWithoutAuth();

                    confirmDialog.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void withdrawWithoutAuth()
    {
        Map<String, String> map = new HashMap<>();
        map.put("token", savePreferences.reterivePreference(this, DefaultConstants.token) + "");
        map.put("currency", symbol);
        map.put("amount", currenyAmount);
        map.put("address", destinationAddressET);
        map.put("", destinationAddressET);

        map.put("DeviceToken", getDeviceToken());

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("X-API-KEY", UtilClass.xApiKey);
        headerMap.put("Rtoken", getNewRToken() + "");


        new ServerHandler().sendToServer(ConfirmWithdrawalFundScreen.this, getApiUrl() + "proceed-withdraw", map, 0, headerMap, 20000, R.layout.progressbar, new CallBack() {
            @Override
            public void getRespone(String dta, ArrayList<Object> respons) {
                try {

                    JSONObject obj = new JSONObject(dta);
                    if (obj.getBoolean("status")) {
                        alertDialogs.alertDialog(ConfirmWithdrawalFundScreen.this, getResources().getString(R.string.Response), obj.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                            @Override
                            public void getDialogEvent(String buttonPressed) {

                                finish();
                            }
                        });
                    } else {
                        alertDialogs.alertDialog(ConfirmWithdrawalFundScreen.this, getResources().getString(R.string.Response), obj.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                            @Override
                            public void getDialogEvent(String buttonPressed) {
                                unauthorizedAccess(obj);
                            }
                        });

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }



}

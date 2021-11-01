package com.web.whatashot.fiatdepositwithdraw;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.dialogsnpickers.DialogCallBacks;
import com.app.dialogsnpickers.SimpleDialog;
import com.app.vollycommunicationlib.CallBack;
import com.app.vollycommunicationlib.ServerHandler;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.web.whatashot.BaseActivity;
import com.web.whatashot.DefaultConstants;
import com.web.whatashot.R;
import com.web.whatashot.adapters.BankDetailsAdapters;
import com.web.whatashot.kyc.VerifyCompleteSubmitKycScreen;
import com.web.whatashot.setting_profile.SettingPasswordScreen;
import com.web.whatashot.utilpackage.UtilClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ShowFiatCurrencyDepositWithdraw extends BaseActivity {
    private String isKycStatus = "";
    private boolean authenticator = false;
    private RecyclerView recycler_view_bankaddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_fiat_currency_deposit_withdraw);

        getSupportActionBar().hide();
        initiateObj();
        init();
        getDepsoitAddress();
    }

    private void init() {
        try {
            JSONObject dataObj = new JSONObject(getIntent().getStringExtra("data"));
            String symbol = dataObj.getString("symbol");
            String availableBalance = dataObj.getString("available_balance");
            final String icon = dataObj.getString("icon");

            ImageView txt_currency_image = findViewById(R.id.txt_currency_image);
            // TextView txt_withdraw = findViewById(R.id.txt_withdraw);
            TextView txt_title = findViewById(R.id.txt_title);

            TextView total_balance = findViewById(R.id.total_balance);
            // TextView txt_deposit = findViewById(R.id.txt_deposit);
            recycler_view_bankaddress = findViewById(R.id.recycler_view_bankaddress);
            total_balance.setText(availableBalance);

            txt_title.setText("PLEASE PAY ATTENTION! THE COIN YOU SELECTED IS : " + symbol);
            showImage(icon, txt_currency_image);

            findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });


            findViewById(R.id.txt_deposit_inr).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ShowFiatCurrencyDepositWithdraw.this, DepositeInrActivity.class);
                    startActivityForResult(intent, 1001);
                }
            });

            findViewById(R.id.txt_withdrawinr).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String bankStatus=  savePreferences.reterivePreference(ShowFiatCurrencyDepositWithdraw.this, DefaultConstants.bank_status).toString();
                    if(bankStatus.equals("0"))
                    {
                        showBankDetailsDialog(symbol,availableBalance,icon);
                    }
                    else  if(bankStatus.equals("1")){
                        Intent intent = new Intent(ShowFiatCurrencyDepositWithdraw.this, WithdrawInrActivity.class);
                        intent.putExtra("isGoogleAuth", authenticator);
                        intent.putExtra("currency", symbol);
                        intent.putExtra("balance", availableBalance);
                        intent.putExtra("icon", icon);
                        startActivityForResult(intent, 1001);
                    }
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showImage(final String url, final ImageView header_img) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(ShowFiatCurrencyDepositWithdraw.this)
                        .load(url)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(3)))
                        .into(header_img);
            }
        });
    }


    private void getDepsoitAddress() {
        try {
            final Map<String, String> m = new HashMap<>();
            m.put("token", savePreferences.reterivePreference(ShowFiatCurrencyDepositWithdraw.this, DefaultConstants.token) + "");
            m.put("Version", getAppVersion());
            m.put("PlatForm", "android");
            m.put("Timestamp", System.currentTimeMillis() + "");
            m.put("DeviceToken", getDeviceToken() + "");

            final Map<String, String> obj = new HashMap<>();
            obj.put("X-API-KEY", getXapiKey());
            obj.put("Rtoken", getNewRToken() + "");

            new ServerHandler().sendToServer(ShowFiatCurrencyDepositWithdraw.this, getApiUrl() + "get-company-bank", m, 0, obj, 20000, R.layout.progressbar, new CallBack() {
                @Override
                public void getRespone(String dta, ArrayList<Object> respons) {
                    try {
                        JSONObject jsonObject = new JSONObject(dta);
                        if(jsonObject.getBoolean("status")) {
                            try {
                                authenticator = jsonObject.getBoolean("authenticator");
                                showBanksAddress(jsonObject.getJSONArray("banks"));
                                }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            alertDialogs.alertDialog(ShowFiatCurrencyDepositWithdraw.this, getResources().getString(R.string.Response), jsonObject.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                                @Override
                                public void getDialogEvent(String buttonPressed)
                                {
                                    unauthorizedAccess(jsonObject);
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showBanksAddress(JSONArray dataAr)
    {
        BankDetailsAdapters mAdapter = new BankDetailsAdapters(this, dataAr);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler_view_bankaddress.setLayoutManager(horizontalLayoutManagaer);
        recycler_view_bankaddress.setItemAnimator(new DefaultItemAnimator());
        recycler_view_bankaddress.setAdapter(mAdapter);
    }
    private void showBankDetailsDialog(String symbol,String availableBalance,String icon) {
        try {

            hideKeyboard(this);
            SimpleDialog simpleDialog = new SimpleDialog();
            final Dialog selectCategoryDialog = simpleDialog.simpleDailog(ShowFiatCurrencyDepositWithdraw.this, R.layout.bank_details_dialog, new ColorDrawable(getResources().getColor(R.color.translucent_black)), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, false);
            ImageView img_hideview = selectCategoryDialog.findViewById(R.id.closeIC);
            final RelativeLayout ll_relativelayout = selectCategoryDialog.findViewById(R.id.root_layout);
            final TextView saveTV=selectCategoryDialog.findViewById(R.id.saveTV);
            final EditText ed_account_holder = selectCategoryDialog.findViewById(R.id.ed_bank_account_holder_name);
            final EditText ed_bank_name = selectCategoryDialog.findViewById(R.id.ed_bank_name);
            final EditText ed_branch_name = selectCategoryDialog.findViewById(R.id.ed_branch_name);
            final EditText ed_account_number = selectCategoryDialog.findViewById(R.id.ed_bank_account_number);
            final EditText ed_confirm_account_number = selectCategoryDialog.findViewById(R.id.ed_bank_confirm_account);
            final EditText ed_bank_ifsc_code = selectCategoryDialog.findViewById(R.id.ed_bank_ifsc_code);

            animateUp(ll_relativelayout);


            img_hideview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    downSourceDestinationView(ll_relativelayout, selectCategoryDialog);
                }
            });

            saveTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validationRule.checkEmptyString(ed_account_holder) == 0) {
                        alertDialogs.alertDialog(ShowFiatCurrencyDepositWithdraw.this, getResources().getString(R.string.app_name), "Enter account holder name.", getString(R.string.ok), "", new DialogCallBacks() {
                            @Override
                            public void getDialogEvent(String buttonPressed) {

                            }
                        });
                        return;
                    }

                    if (validationRule.checkEmptyString(ed_bank_name) == 0) {
                        alertDialogs.alertDialog(ShowFiatCurrencyDepositWithdraw.this, getResources().getString(R.string.app_name), "Enter bank name.", getString(R.string.ok), "", new DialogCallBacks() {
                            @Override
                            public void getDialogEvent(String buttonPressed) {

                            }
                        });
                        return;
                    }
                    if (validationRule.checkEmptyString(ed_branch_name) == 0) {
                        alertDialogs.alertDialog(ShowFiatCurrencyDepositWithdraw.this, getResources().getString(R.string.app_name), "Enter branch name.", getString(R.string.ok), "", new DialogCallBacks() {
                            @Override
                            public void getDialogEvent(String buttonPressed) {

                            }
                        });
                        return;
                    }
                    if (validationRule.checkEmptyString(ed_account_number) == 0) {
                        alertDialogs.alertDialog(ShowFiatCurrencyDepositWithdraw.this, getResources().getString(R.string.app_name), "Enter account number.", getString(R.string.ok), "", new DialogCallBacks() {
                            @Override
                            public void getDialogEvent(String buttonPressed) {

                            }
                        });
                        return;
                    }
                    if (validationRule.checkEmptyString(ed_confirm_account_number) == 0) {
                        alertDialogs.alertDialog(ShowFiatCurrencyDepositWithdraw.this, getResources().getString(R.string.app_name), "Enter confirm account number.", getString(R.string.ok), "", new DialogCallBacks() {
                            @Override
                            public void getDialogEvent(String buttonPressed) {

                            }
                        });
                        return;
                    }
                    if (!ed_account_number.getText().toString().equals(ed_confirm_account_number.getText().toString())) {
                        alertDialogs.alertDialog(ShowFiatCurrencyDepositWithdraw.this, getResources().getString(R.string.app_name), "Confirm account number is not valid.", getString(R.string.ok), "", new DialogCallBacks() {
                            @Override
                            public void getDialogEvent(String buttonPressed) {

                            }
                        });
                        return;
                    }
                    if (validationRule.checkEmptyString(ed_bank_ifsc_code) == 0) {
                        alertDialogs.alertDialog(ShowFiatCurrencyDepositWithdraw.this, getResources().getString(R.string.app_name), "Enter IFSC/IBAN code.", getString(R.string.ok), "", new DialogCallBacks() {
                            @Override
                            public void getDialogEvent(String buttonPressed) {

                            }
                        });
                        return;
                    }
                    try {
                        Map<String, String> m = new LinkedHashMap<>();
                        m.put("token",savePreferences.reterivePreference(ShowFiatCurrencyDepositWithdraw.this, DefaultConstants.token)+"");
                        m.put("account_holder", ed_account_holder.getText().toString().trim());
                        m.put("account_number", ed_account_number.getText().toString().trim());
                        m.put("bank_name", ed_bank_name.getText().toString().trim());
                        m.put("branch", ed_branch_name.getText().toString().trim());
                        m.put("ifsc", ed_bank_ifsc_code.getText().toString().trim());
                        m.put("Version", getAppVersion());
                        m.put("PlatForm", "android");
                        m.put("Timestamp", System.currentTimeMillis() + "");
                        m.put("DeviceToken", getDeviceToken() + "");

                        Map<String, String> headerMap = new HashMap<>();
                        headerMap.put("X-API-KEY", UtilClass.xApiKey);
                        System.out.println("request::"+m);


                        new ServerHandler().sendToServer(ShowFiatCurrencyDepositWithdraw.this, getApiUrl() + "app-bank-details", m, 0, headerMap, 20000, R.layout.progressbar, new CallBack() {
                            @Override
                            public void getRespone(String dta, ArrayList<Object> respons) {
                                try {
                                    System.out.println("response::"+dta);

                                    JSONObject obj = new JSONObject(dta);
                                    if (obj.getBoolean("status")) {
                                        try {
                                            alertDialogs.alertDialog(ShowFiatCurrencyDepositWithdraw.this, getResources().getString(R.string.app_name), obj.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                                                @Override
                                                public void getDialogEvent(String buttonPressed) {
                                                    savePreferences.savePreferencesData(ShowFiatCurrencyDepositWithdraw.this,"1", DefaultConstants.bank_status);

                                                    Intent intent = new Intent(ShowFiatCurrencyDepositWithdraw.this, WithdrawInrActivity.class);
                                                    intent.putExtra("isGoogleAuth", authenticator);
                                                    intent.putExtra("currency", symbol);
                                                    intent.putExtra("balance", availableBalance);
                                                    intent.putExtra("icon", icon);
                                                    startActivityForResult(intent, 1001);
                                                    downSourceDestinationView(ll_relativelayout, selectCategoryDialog);
                                                }
                                            });


                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    } else {
                                        alertDialogs.alertDialog(ShowFiatCurrencyDepositWithdraw.this, getResources().getString(R.string.app_name), obj.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
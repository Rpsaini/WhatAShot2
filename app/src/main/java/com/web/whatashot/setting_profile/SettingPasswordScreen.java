package com.web.whatashot.setting_profile;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.dialogsnpickers.DialogCallBacks;
import com.app.vollycommunicationlib.CallBack;
import com.app.vollycommunicationlib.ServerHandler;
import com.web.whatashot.BaseActivity;
import com.web.whatashot.DefaultConstants;
import com.web.whatashot.R;
import com.web.whatashot.RegisterActivity;
import com.web.whatashot.utilpackage.UtilClass;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SettingPasswordScreen extends BaseActivity {
    private EditText ed_currentPwd, ed_newPwd,ed_confirmPwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_screen);
        initiateObj();
        init();
    }

    private void init() {
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView updateTV = findViewById(R.id.updateTV);
        ed_confirmPwd = findViewById(R.id.ed_confirmPassword);
        ed_currentPwd = findViewById(R.id.ed_currentPassword);
        ed_newPwd = findViewById(R.id.ed_newPassword);

        updateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validationRule.checkEmptyString(ed_currentPwd) == 0) {
                    alertDialogs.alertDialog(SettingPasswordScreen.this, getResources().getString(R.string.app_name), "Enter Current Password.", getString(R.string.ok), "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }
                if (!validatePasswordRule(ed_currentPwd.getText().toString())) {
                    alertDialogs.alertDialog(SettingPasswordScreen.this, getResources().getString(R.string.app_name), "Current Password use at least 1 uppercase, 1 lowercase 1 numeric & at least up to 8 characters", "Ok", "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }
                if (validationRule.checkEmptyString(ed_newPwd) == 0) {
                    alertDialogs.alertDialog(SettingPasswordScreen.this, getResources().getString(R.string.app_name), "Enter New Password.", getString(R.string.ok), "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }
                if (!validatePasswordRule(ed_newPwd.getText().toString())) {
                    alertDialogs.alertDialog(SettingPasswordScreen.this, getResources().getString(R.string.app_name), "New Password use at least 1 uppercase, 1 lowercase 1 numeric & at least up to 8 characters", "Ok", "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }
                if (validationRule.checkEmptyString(ed_confirmPwd) == 0) {
                    alertDialogs.alertDialog(SettingPasswordScreen.this, getResources().getString(R.string.app_name), "Enter Confirm Password.", getString(R.string.ok), "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }
                if (!ed_newPwd.getText().toString().equals(ed_confirmPwd.getText().toString())) {
                    alertDialogs.alertDialog(SettingPasswordScreen.this, getResources().getString(R.string.app_name), "Confirm Password is not valid.", getString(R.string.ok), "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }
                try {
                    Map<String, String> m = new LinkedHashMap<>();
                    m.put("token",savePreferences.reterivePreference(SettingPasswordScreen.this, DefaultConstants.token)+"");
                    m.put("current_password", ed_currentPwd.getText().toString().trim());
                    m.put("new_password", ed_newPwd.getText().toString().trim());
                    m.put("confirm_password", ed_confirmPwd.getText().toString().trim());
                    m.put("Version", getAppVersion());
                    m.put("PlatForm", "android");
                    m.put("Timestamp", System.currentTimeMillis() + "");
                    m.put("DeviceToken", getDeviceToken() + "");

                    Map<String, String> headerMap = new HashMap<>();
                    headerMap.put("X-API-KEY", UtilClass.xApiKey);
                    System.out.println("request::"+m);


                    new ServerHandler().sendToServer(SettingPasswordScreen.this, getApiUrl() + "app-change-password", m, 0, headerMap, 20000, R.layout.progressbar, new CallBack() {
                        @Override
                        public void getRespone(String dta, ArrayList<Object> respons) {
                            try {
                                System.out.println("response::"+dta);

                                JSONObject obj = new JSONObject(dta);
                                if (obj.getBoolean("status")) {
                                    try {
                                        alertDialogs.alertDialog(SettingPasswordScreen.this, getResources().getString(R.string.app_name), obj.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                                            @Override
                                            public void getDialogEvent(String buttonPressed) {
                                               finish();
                                            }
                                        });


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                } else {
                                    alertDialogs.alertDialog(SettingPasswordScreen.this, getResources().getString(R.string.app_name), obj.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
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

    }

}

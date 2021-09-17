package com.web.whatashot;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.dialogsnpickers.DialogCallBacks;
import com.app.vollycommunicationlib.CallBack;
import com.app.vollycommunicationlib.ServerHandler;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.web.whatashot.captcha.VerifyCaptchaJava;
import com.web.whatashot.utilpackage.UtilClass;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends BaseActivity {
    private EditText txt_fname, txt_lastname, txt_email, txt_phonenumber, txt_username, txt_password, txt_conf_password, txt_referral_code;
    private TextView privacyPolicy, termsandcondtion, login_register;
    private ImageView img_back;
    private CheckBox check_captcha;
    private CheckBox checkbox_tems;


    //for captcha
    String TAG = VerifyCaptchaJava.class.getSimpleName();
    String SITE_KEY = "6LeFIn4bAAAAADeh2C7Bq0KciRNCGgXf__Kw5wHC";
    String SECRET_KEY = "6LeFIn4bAAAAAMgHXifb3uN4vlU7W_DMkYgBu8dB";
    RequestQueue queue;
    //end of captcha

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        getSupportActionBar().hide();
        initiateObj();
        init();
        actions();
        setHtmlCode();


    }

    private void init() {
        txt_fname = findViewById(R.id.txt_fname);
        txt_lastname = findViewById(R.id.txt_lastname);
        txt_email = findViewById(R.id.txt_email);
        txt_phonenumber = findViewById(R.id.txt_phonenumber);
        txt_username = findViewById(R.id.txt_username);
        txt_password = findViewById(R.id.txt_password);
        txt_conf_password = findViewById(R.id.txt_conf_password);
        txt_referral_code = findViewById(R.id.txt_referral_code);
        termsandcondtion = findViewById(R.id.termsandcondtion);
        privacyPolicy = findViewById(R.id.privacyPolicy);
        login_register = findViewById(R.id.login_register);
        img_back = findViewById(R.id.img_back);
        check_captcha = findViewById(R.id.check_captcha);
        checkbox_tems = findViewById(R.id.checkbox_tems);
    }

    private void setHtmlCode()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            termsandcondtion.setText(Html.fromHtml("Accept  <u><b>Term of Service</b></u> and ", Html.FROM_HTML_MODE_COMPACT));

        } else {
            termsandcondtion.setText(Html.fromHtml("Accept <u><b>Term of Service</b></u> and "));

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            privacyPolicy.setText(Html.fromHtml("<u><b>Privacy Policy</b></u>", Html.FROM_HTML_MODE_COMPACT));

        } else {
            privacyPolicy.setText(Html.fromHtml("<u><b>Privacy Policy</b></u>"));

        }
    }

    private void actions() {
        login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validationRule.checkEmptyString(txt_fname) == 0) {
                    alertDialogs.alertDialog(RegisterActivity.this, getResources().getString(R.string.app_name), "Enter First Name", "Ok", "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }
                if (validationRule.checkEmptyString(txt_lastname) == 0) {
                    alertDialogs.alertDialog(RegisterActivity.this, getResources().getString(R.string.app_name), "Enter Last Name", "Ok", "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }
                if (validationRule.checkEmptyString(txt_email) == 0) {
                    alertDialogs.alertDialog(RegisterActivity.this, getResources().getString(R.string.app_name), "Enter Email Address", "Ok", "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }
                if(validationRule.checkEmail(txt_email) == 0)
                {
                    alertDialogs.alertDialog(RegisterActivity.this, getResources().getString(R.string.app_name), "Enter Valid Email Address", "Ok", "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                }



                
                if (validationRule.checkEmptyString(txt_phonenumber) == 0) {
                    alertDialogs.alertDialog(RegisterActivity.this, getResources().getString(R.string.app_name), "Enter Phone Number", "Ok", "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }
                if (validationRule.checkEmptyString(txt_username) == 0) {
                    alertDialogs.alertDialog(RegisterActivity.this, getResources().getString(R.string.app_name), "Enter Username", "Ok", "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }
                if (validationRule.checkEmptyString(txt_password) == 0) {
                    alertDialogs.alertDialog(RegisterActivity.this, getResources().getString(R.string.app_name), "Enter Password", "Ok", "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }
                if (!validatePassword(txt_password.getText().toString())) {
                    alertDialogs.alertDialog(RegisterActivity.this, getResources().getString(R.string.app_name), "Use atleast 1 uppercase, 1 lowercase 1 numeric & atleast upto 8 characters", "Ok", "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }
                if (!txt_password.getText().toString().equalsIgnoreCase(txt_conf_password.getText().toString())) {
                    alertDialogs.alertDialog(RegisterActivity.this, getResources().getString(R.string.app_name), "Confirm password is not valid.", "Ok", "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {
                        }
                    });
                    return;
                }

                if (!checkbox_tems.isChecked()) {
                    alertDialogs.alertDialog(RegisterActivity.this, getResources().getString(R.string.app_name), "Please accept terms and privacy policy.", "Ok", "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {
                        }
                    });
                    return;
                }
                doRegistration();
              }
        });

        termsandcondtion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openExternalUrl(getApiUrl() + "/terms-and-conditions");
            }
        });
        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openExternalUrl(getApiUrl() + "/privacy-policy");
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        check_captcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                check_captcha.setChecked(false);
                queue = Volley.newRequestQueue(getApplicationContext());
                generateCaptcha();
            }
        });
//        check_captcha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked)
//                {
//                }
//            }
//        });
     }


    private void doRegistration() {
        try {
            Map<String, String> m = new LinkedHashMap<>();
            m.put("fname", txt_fname.getText().toString());
            m.put("lname", txt_lastname.getText().toString());
            m.put("email", txt_email.getText().toString());
            m.put("password", txt_password.getText().toString());
            m.put("cpassword", txt_conf_password.getText().toString());
            m.put("mobile", txt_phonenumber.getText().toString());
            m.put("sponsor", txt_referral_code.getText().toString());
            m.put("username", txt_username.getText().toString());

            m.put("Version", getAppVersion());
            m.put("PlatForm", "android");
            m.put("Timestamp", System.currentTimeMillis() + "");
            m.put("DeviceToken", getDeviceToken() + "");

            Map<String, String> headerMap = new HashMap<>();
            headerMap.put("X-API-KEY", UtilClass.xApiKey);



            new ServerHandler().sendToServer(RegisterActivity.this, getApiUrl() + "app-signup", m, 0, headerMap, 20000, R.layout.progressbar, new CallBack() {
                @Override
                public void getRespone(String dta, ArrayList<Object> respons) {
                    try {
                        JSONObject obj = new JSONObject(dta);
                        if (obj.getBoolean("status")) {
                            try {
                                alertDialogs.alertDialog(RegisterActivity.this, getResources().getString(R.string.Response), "OTP has been sent to your email id", getResources().getString(R.string.ok), "", new DialogCallBacks() {
                                    @Override
                                    public void getDialogEvent(String buttonPressed) {
                                        try {
                                            savePreferences.savePreferencesData(RegisterActivity.this, obj.getString("token"), DefaultConstants.token);
                                            savePreferences.savePreferencesData(RegisterActivity.this, obj.getString("r_token"), DefaultConstants.r_token);
                                            Intent intent = new Intent(RegisterActivity.this, VerifyOtp.class);
                                            startActivity(intent);

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            alertDialogs.alertDialog(RegisterActivity.this, getResources().getString(R.string.Response), obj.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
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

    public static boolean validatePassword(final String password) {
        String PASSWORD_PATTERN =
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }


    //generate captch====

    private void generateCaptcha() {
        SafetyNet.getClient(this).verifyWithRecaptcha(SITE_KEY)
                .addOnSuccessListener(this, new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                    @Override
                    public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
                        if (!response.getTokenResult().isEmpty()) {
                            handleSiteVerify(response.getTokenResult());
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof ApiException) {
                            ApiException apiException = (ApiException) e;
                            Log.d(TAG, "Error message: " +
                                    CommonStatusCodes.getStatusCodeString(apiException.getStatusCode()));
                        } else {
                            Log.d(TAG, "Unknown type of error: " + e.getMessage());
                        }
                    }
                });
    }

    protected void handleSiteVerify(final String responseToken) {
        //it is google recaptcha siteverify server
        //you can place your server url
        String url = "https://www.google.com/recaptcha/api/siteverify";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getBoolean("success")) {
                                check_captcha.setChecked(true);
                                login_register.setEnabled(true);
                                login_register.setAlpha(1f);
                                // {"success":true,"challenge_ts":"2021-07-07T06:33:55Z","apk_package_name":"com.web
                            } else {
                                check_captcha.setChecked(false);
                                login_register.setEnabled(false);
                                login_register.setAlpha(.7f);
                                Toast.makeText(getApplicationContext(), String.valueOf(jsonObject.getString("error-codes")), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception ex) {
                            Log.d(TAG, "JSON exception: " + ex.getMessage());

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        check_captcha.setChecked(false);
                        login_register.setEnabled(false);
                        login_register.setAlpha(.7f);
                        Log.d(TAG, "Error message: " + error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("secret", SECRET_KEY);
                params.put("response", responseToken);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }
    //end of captcha=====


}


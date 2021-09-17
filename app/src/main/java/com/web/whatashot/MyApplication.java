package com.web.whatashot;

import androidx.multidex.MultiDexApplication;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.androidnetworking.AndroidNetworking;

import fontspackageForTextView.DefineYourAppFont;

public class MyApplication extends MultiDexApplication {

    public static final String TAG = MyApplication.class.getSimpleName();

    private static MyApplication mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;



    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(this);
        DefineYourAppFont.fontNameRegular="fonts/OpenSans-Regular.ttf";
        DefineYourAppFont.fontNameBold="fonts/OpenSans-Bold.ttf";
        DefineYourAppFont.fontNameBoldExtra="fonts/OpenSans-ExtraBold.ttf";
        DefineYourAppFont.fontNameItalic="fonts/OpenSans-Italic.ttf";
        DefineYourAppFont.fontNameBoldItalic="OpenSans-BoldItalic.ttf";
        DefineYourAppFont.fontNameLiteItalic="fonts/OpenSans-LightItalic.ttf";
        DefineYourAppFont.fontNameBoldMedium="fonts/OpenSans-SemiBold.ttf";
        mInstance = this;

    }





}
package com.web.whatashot.captcha

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.safetynet.SafetyNet
import com.web.whatashot.R

class VerifyCaptcha : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val TAG = "MainActivity"
        const val SITE_KEY = "6LcyDX4bAAAAAAgFSbpRMp2HZXY9Ag8dNPwkSZIJ"
    }

    private lateinit var btnverifyCaptcha: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.verify_captcha_layout)
        btnverifyCaptcha = findViewById(R.id.button)
        btnverifyCaptcha.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        SafetyNet.getClient(this).verifyWithRecaptcha(SITE_KEY)
                .addOnSuccessListener(this) { response ->
                    if (!response.tokenResult.isEmpty()) {
                        handleVerify(response.tokenResult)
                    }
                }
                .addOnFailureListener(this) { e ->
                    if (e is ApiException) {
                        Log.d(TAG,("Error message: " + CommonStatusCodes.getStatusCodeString(e.statusCode)))
                    } else {
                        Log.d(TAG, "Unknown type of error: " + e.message)
                    }
                }
    }

    protected fun handleVerify(responseToken: String) {
        //it is google recaptcha siteverify server
        //you can place your server url
        val url = "https://www.google.com/recaptcha/api/siteverify"
        AndroidNetworking.get(url)
                .addHeaders("token", responseToken)
                .setTag("MY_NETWORK_CALL")
                .setPriority(Priority.LOW)
                .build()

    }
}
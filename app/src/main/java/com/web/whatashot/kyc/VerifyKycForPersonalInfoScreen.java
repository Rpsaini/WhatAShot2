package com.web.whatashot.kyc;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.app.dialogsnpickers.DialogCallBacks;
import com.google.gson.Gson;
import com.web.whatashot.BaseActivity;
import com.web.whatashot.BuildConfig;
import com.web.whatashot.DefaultConstants;
import com.web.whatashot.R;
import com.web.whatashot.fileupload.AddEventInterface;
import com.web.whatashot.fileupload.ApiProduction;
import com.web.whatashot.fileupload.RxAPICallHelper;
import com.web.whatashot.fileupload.RxAPICallback;
import com.web.whatashot.fileupload.ServerResponse;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;


public class VerifyKycForPersonalInfoScreen extends BaseActivity
{
    private ImageView backIc = null;
    private ImageView img_one;
    private TextView lbl_one,btn_browse;
    private ImageView pancardImage,docImage,docBackImage,docSelfiImage;
    private RelativeLayout panUploadRL,docUploadRL,docBackUploadRL,docSelfiUploadRL;
    String imageType="pan";
    private ImageView commonImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_profile_details_screen);
        initiateObj();
        initView();
        setOnClickListener();
        actions();
    }

    private void initView() {
        backIc = findViewById(R.id.backIC);

    }

    private void setOnClickListener() {
        backIc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pancardImage=findViewById(R.id.pancardImage);
        docImage=findViewById(R.id.docImage);
        docBackImage=findViewById(R.id.docBackImage);
        docSelfiImage=findViewById(R.id.docSelfiImage);

        panUploadRL=findViewById(R.id.panUploadRL);
        docUploadRL=findViewById(R.id.docUploadRL);
        docBackUploadRL=findViewById(R.id.docBackUploadRL);
        docSelfiUploadRL=findViewById(R.id.docSelfiUploadRL);




    }

    private void actions() {
        panUploadRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageType="pan";
                commonImage=pancardImage;
                browseImage();
            }
        });
        docUploadRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageType="documnt_front";
                commonImage=docImage;
                browseImage();
            }
        });
        docBackUploadRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageType="document_back";
                commonImage=docBackImage;
                browseImage();
            }
        });
        docSelfiUploadRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageType="selfie";
                commonImage=docSelfiImage;
                browseImage();
            }
        });





    }

    //browse image
    int browseMode = 0;

    private void browseImage() {
        alertDialogs.alertDialog(this, getResources().getString(R.string.app_name), "Choose Image from", "Camera", "Gallery", new DialogCallBacks() {
            @Override
            public void getDialogEvent(String buttonPressed) {
                if (buttonPressed.equalsIgnoreCase("Camera")) {

                    browseMode = 0;
                    selectImage(browseMode);
                } else {
                    browseMode = 1;
                    selectImage(browseMode);
                }

            }
        });
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void selectImage(int actionCode) {
        if (checkAndRequestPermissions() == 0) {
            if (actionCode == 0) {
                dispatchTakePictureIntent();
            } else if (actionCode == 1) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, actionCode);
            }
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        if (resultCode != RESULT_CANCELED) {

            switch (requestCode) {
                case 0://camera
                    try {
                        Bitmap bmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoURI);
                        Uri uri = getImageUri(this, bmap);
                        selectedPath = getRealPathFromURI(uri);
                        commonImage.setImageBitmap(bmap);

                        senAndUploadFile();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case 1://gallery
                    if (resultCode == RESULT_OK) {
                        if (imageReturnedIntent != null) {
                            try {
                                Uri selectedImage = imageReturnedIntent.getData();
                                selectedPath = getRealPathFromURI(selectedImage);
                                InputStream image_stream = getContentResolver().openInputStream(selectedImage);
                                Bitmap bmap = BitmapFactory.decodeStream(image_stream);
                                commonImage.setImageBitmap(bmap);

                                senAndUploadFile();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
            }

            // new ConvertImage().execute();
        }
    }

    static final int REQUEST_TAKE_PHOTO = 0;
    Uri photoURI;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                System.out.println("inside exception===" + ex.getMessage());

            }
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(Objects.requireNonNull(getApplicationContext()),
                        BuildConfig.APPLICATION_ID + ".provider", photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                    takePictureIntent.setClipData(ClipData.newRawUri("", photoURI));
                    takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }


    String mCurrentPhotoPath;


    //    String mCurrentPhotoPath;
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",  /* suffix */
                storageDir      /* directory */
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    private int checkAndRequestPermissions() {

        int permissionCAMERA = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
        int readExternal = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writeExternal = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionCAMERA != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
        }
        if (readExternal != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (writeExternal != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
            return 1;
        }

        return 0;
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    //end of browse image


    private String selectedPath = "";

    private void senAndUploadFile() {

        Observable<Response<ServerResponse>> responseObservable = null;
        AddEventInterface contestService = ApiProduction.getInstance(this).provideService(AddEventInterface.class);

        File file = new File(selectedPath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData(imageType, file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        RequestBody DeviceToken = RequestBody.create(MediaType.parse("text/plain"), getDeviceToken());
        RequestBody Version = RequestBody.create(MediaType.parse("text/plain"), getAppVersion());
        RequestBody PlatForm = RequestBody.create(MediaType.parse("text/plain"), "Android");
        RequestBody Timestamp = RequestBody.create(MediaType.parse("text/plain"), System.currentTimeMillis()+"");

        RequestBody token = RequestBody.create(MediaType.parse("text/plain"), savePreferences.reterivePreference(VerifyKycForPersonalInfoScreen.this, "token") + "");
        responseObservable = contestService.uploadKyc(token, DeviceToken, Version, PlatForm,Timestamp, getXapiKey(), body, filename);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        RxAPICallHelper.call(responseObservable, new RxAPICallback<Response<ServerResponse>>() {
            @Override
            public void onSuccess(Response<ServerResponse> t) {
                try {
                    progressDialog.dismiss();
                    selectedPath = "";
                    if (t.body().isStatus()) {
                        ServerResponse.Kyc_info kyc_info = t.body().getKyc_info();
                        Gson gson = new Gson();
                        String json = gson.toJson(kyc_info);
                        System.out.println("Check image---"+json);
                       // savePreferences.savePreferencesData(UploadDocuments.this, json, AppSettings.kyc_details);
//
//
//                        savePreferences.savePreferencesData(UploadDocuments.this, t.body().getToken(), AppSettings.token);
//                        savePreferences.savePreferencesData(UploadDocuments.this, t.body().getR_token(), AppSettings.r_token);
//
//
                        alertDialogs.alertDialog(VerifyKycForPersonalInfoScreen.this, getResources().getString(R.string.app_name), t.body().getMsg(), "ok", "", new DialogCallBacks() {
                            @Override
                            public void getDialogEvent(String buttonPressed) {

                            }
                        });


                    } else {
                        alertDialogs.alertDialog(VerifyKycForPersonalInfoScreen.this, getResources().getString(R.string.app_name), t.body().getMsg(), "ok", "", new DialogCallBacks() {
                            @Override
                            public void getDialogEvent(String buttonPressed) {
                            }
                        });
                    }

//                      {"status":true,"msg":"Your KYC details has been updated successfully","kyc_info":{"attempt":true,"status":"0","address_f_status":"1","address_b_status":"0","pan_status":"0"},"code":200}

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailed(Throwable throwable) {
                progressDialog.dismiss();
                System.out.println("error===" + throwable.getMessage());
                alertDialogs.alertDialog(VerifyKycForPersonalInfoScreen.this, "ok", "Message document not updated.", "ok", "", new DialogCallBacks() {
                    @Override
                    public void getDialogEvent(String buttonPressed) {
                        if (buttonPressed.equalsIgnoreCase("ok")) {

                        }
                    }
                });
            }
        });

    }
}
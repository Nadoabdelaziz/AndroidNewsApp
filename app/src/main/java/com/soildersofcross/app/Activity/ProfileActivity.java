package com.soildersofcross.app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import com.soildersofcross.app.R;
import com.soildersofcross.app.utils.SPmanager;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";
    private static final int CAMERA_REQUEST = 1, GALLERY_REQUEST = 2;
    private ImageView imgViewPassword, imgClose, imgUser, imgMail, imgPass;
    private EditText edtName, edtEmail, edtPassword;
    private TextView txtUpdate, txtTitProfile, txtTitName, txtTitMail, txtTitPsw, txtImgProfile;
    private String password, username, email, userid, picturepath, status;
    private boolean isPassword;
    private LinearLayout linTop, linMain, linUpdate;
    private View view1, view2, view3;
    private CircleImageView imgProfile;
    private String Document_img1 = "", strFirstChar, profile_image_path, loginSuccessfull;
    public static final String KEY_User_Document1 = "doc1";
    public static String strpermision;
    private Uri profile_uri;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private Bitmap bitmap;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();
        getPreferenceData();
        isStoragePermissionGranted();
        init();
        getThemes();

    }

    private void getPreferenceData() {
        userid = SPmanager.getPreference(ProfileActivity.this, "userid");
        username = SPmanager.getPreference(ProfileActivity.this, "username");
        email = SPmanager.getPreference(ProfileActivity.this, "email");
        password = SPmanager.getPreference(ProfileActivity.this, "password");
        profile_image_path = SPmanager.getPreference(ProfileActivity.this, "profile_image_path");
        loginSuccessfull = SPmanager.getPreference(ProfileActivity.this, "loginSuccessfull");

        Log.e(TAG, "getPreferenceData: " + userid + password + username + email + profile_image_path);
    }

    private void init() {

        linTop = findViewById(R.id.linTop);
        linMain = findViewById(R.id.linMain);
        linUpdate = findViewById(R.id.linUpdate);
        txtUpdate = findViewById(R.id.txtUpdate);
        txtTitName = findViewById(R.id.txtTitName);
        txtTitPsw = findViewById(R.id.txtTitPsw);
        txtTitProfile = findViewById(R.id.txtTitProfile);
        txtTitMail = findViewById(R.id.txtTitMail);
        txtImgProfile = findViewById(R.id.txtImgProfile);
        imgProfile = findViewById(R.id.imgProfile);
        imgClose = findViewById(R.id.imgClose);
        imgUser = findViewById(R.id.imgUser);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        imgMail = findViewById(R.id.imgMail);
        imgPass = findViewById(R.id.imgPass);
        imgViewPassword = findViewById(R.id.imgViewPassword);
        edtPassword = findViewById(R.id.edtPassword);
        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        view3 = findViewById(R.id.view3);

        edtName.setText(username);
        edtEmail.setText(email);
        edtName.setFocusable(false);
        edtName.setEnabled(false);
        edtEmail.setFocusable(false);
        edtEmail.setEnabled(false);

        try {
            String userName = edtName.getText().toString();
            strFirstChar = userName.substring(0, 1).toUpperCase();

            if (profile_image_path != null) {
                imgProfile.setVisibility(View.VISIBLE);
                txtImgProfile.setVisibility(View.GONE);
                imgProfile.setImageBitmap(BitmapFactory.decodeFile(profile_image_path));
            } else {
                txtImgProfile.setVisibility(View.VISIBLE);
                imgProfile.setVisibility(View.GONE);
                txtImgProfile.setText(strFirstChar);
            }
        } catch (Exception e) {
            e.getMessage();
            Log.e(TAG, "init: " + e.getMessage());
        }

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginSuccessfull != null) {
                    if (loginSuccessfull.equals("yes")) {
                        if (isStoragePermissionGranted()) {
                            selectImage();
                        } else {
                            Toast.makeText(ProfileActivity.this, "Allow permissition", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ProfileActivity.this, "First register your account", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(ProfileActivity.this, "First register your account", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        txtImgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginSuccessfull != null) {
                    if (loginSuccessfull.equals("yes")) {
                        if (isStoragePermissionGranted()) {
                            selectImage();
                        } else {
                            Toast.makeText(ProfileActivity.this, "Allow permissition", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ProfileActivity.this, "First register your account", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ProfileActivity.this, "First register your account", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imgViewPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        return true;
                    case MotionEvent.ACTION_UP:
                        edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        return true;
                }
                return false;

            }
        });

        txtUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = edtPassword.getText().toString();
                Log.e(TAG, "onClick: " + password);
                if (!password.isEmpty()) {
                    updateData(userid, password);
                } else {
                    Toast.makeText(ProfileActivity.this, "password is empaty", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void updateData(String userid, String password) {

        String url = getString(R.string.server_url) + "webservices/user-update.php?uid=" + userid + "&password=" + password;
        Log.e(TAG, "updateData: " + url);

        if (MainActivity.themeKEY != null) {
            if (MainActivity.themeKEY.equals("1")) {
                progressDialog = new ProgressDialog(ProfileActivity.this, R.style.MyAlertDialogStyleLight);
            } else {
                progressDialog = new ProgressDialog(ProfileActivity.this, R.style.MyAlertDialogStyle);
            }
        } else {
            progressDialog = new ProgressDialog(ProfileActivity.this, R.style.MyAlertDialogStyle);
        }

        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Wait a moment...!");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        AndroidNetworking.post(getString(R.string.server_url) + "webservices/user-update.php")
                .addBodyParameter("uid", userid)
                .addBodyParameter("password", password)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "onResponse: " + response);
                        try {
                            status = response.getString("status");
                            if (status.equals("200")) {

                                JSONObject object = response.getJSONObject("response");
                                JSONArray data = object.getJSONArray("data");
                                for (int i = 0; i < data.length(); i++) {

                                    JSONObject object1 = data.getJSONObject(i);
                                    String uid = object1.getString("uid");
                                    String password = object1.getString("password");
                                    Log.e(TAG, "onResponse: " + uid + password);

                                    SPmanager.saveValue(ProfileActivity.this, "password", password);
                                    Toast.makeText(ProfileActivity.this, "Your password updated", Toast.LENGTH_SHORT).show();
                                    edtPassword.setText("");

                                }
                                progressDialog.dismiss();
                            } else if (status.equals("400")) {
                                Log.e(TAG, "onResponse: " + status);
                            }
                        } catch (Exception e) {
                            e.getMessage();
                            Log.e(TAG, "onResponse: " + e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e(TAG, "onError: " + anError.getMessage());
                    }
                });
    }

    private void postdata_() {
        String url = getString(R.string.server_url) + "webservices/update_profile.php";
        Log.e(TAG, "postdata: " + url);
        if (MainActivity.themeKEY != null) {
            if (MainActivity.themeKEY.equals("1")) {
                progressDialog = new ProgressDialog(ProfileActivity.this, R.style.MyAlertDialogStyleLight);
            } else {
                progressDialog = new ProgressDialog(ProfileActivity.this, R.style.MyAlertDialogStyle);
            }
        } else {
            progressDialog = new ProgressDialog(ProfileActivity.this, R.style.MyAlertDialogStyle);
        }
        progressDialog.setMessage("Uploading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgress(0);
        progressDialog.show();
        AndroidNetworking.upload(url)
                .addMultipartFile("upload_images", new File(picturepath))
                .addMultipartParameter("user_id", userid)
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {

                        final int progress = (int) (bytesUploaded * 100 / totalBytes);
                        progressDialog.setProgress(progress);

                    }
                })
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e(TAG, "onResponse: " + response);
                            JSONObject jObject = new JSONObject(String.valueOf(response));
                            String status = jObject.getString("status");
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();

                                if (status != null) {
                                    if (status.equals("200")) {
                                        Toast.makeText(ProfileActivity.this, "Your profile successfully uploaded.", Toast.LENGTH_LONG).show();
                                    } else if (status.equals("Failed")) {
                                        Toast.makeText(ProfileActivity.this, "Please check internet connection.", Toast.LENGTH_LONG).show();
                                    }
                                } else {

                                }
                            }

                        } catch (Exception e) {
                            e.getMessage();
                            Log.e(TAG, "onResponse: " + e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e(TAG, "onError: " + anError.getErrorDetail());
                        progressDialog.dismiss();
                    }
                });
    }

    private void getThemes() {

        if (MainActivity.themeKEY != null) {
            if (MainActivity.themeKEY.equals("1")) {
                linTop.setBackgroundColor(getResources().getColor(R.color.footercolor));
                linMain.setBackgroundColor(getResources().getColor(R.color.white));
                imgClose.setColorFilter(ContextCompat.getColor(ProfileActivity.this, R.color.darkDray), android.graphics.PorterDuff.Mode.MULTIPLY);
                imgUser.setColorFilter(ContextCompat.getColor(ProfileActivity.this, R.color.darkDray), android.graphics.PorterDuff.Mode.MULTIPLY);
                imgMail.setColorFilter(ContextCompat.getColor(ProfileActivity.this, R.color.darkDray), android.graphics.PorterDuff.Mode.MULTIPLY);
                imgPass.setColorFilter(ContextCompat.getColor(ProfileActivity.this, R.color.darkDray), android.graphics.PorterDuff.Mode.MULTIPLY);
                imgViewPassword.setColorFilter(ContextCompat.getColor(ProfileActivity.this, R.color.darkDray), android.graphics.PorterDuff.Mode.MULTIPLY);
                txtUpdate.setTextColor(getResources().getColor(R.color.white));
                txtTitName.setTextColor(getResources().getColor(R.color.darkDray));
                txtTitMail.setTextColor(getResources().getColor(R.color.darkDray));
                txtTitPsw.setTextColor(getResources().getColor(R.color.darkDray));
                linUpdate.setBackgroundResource(R.drawable.btn_login_darkgrey);
                edtName.setHintTextColor(getResources().getColor(R.color.darkDray));
                edtEmail.setHintTextColor(getResources().getColor(R.color.darkDray));
                edtPassword.setHintTextColor(getResources().getColor(R.color.darkDray));
                edtName.setTextColor(getResources().getColor(R.color.darkDray));
                edtEmail.setTextColor(getResources().getColor(R.color.darkDray));
                edtPassword.setTextColor(getResources().getColor(R.color.darkDray));
                view1.setBackgroundColor(getResources().getColor(R.color.darkDray));
                view2.setBackgroundColor(getResources().getColor(R.color.darkDray));
                view3.setBackgroundColor(getResources().getColor(R.color.darkDray));
                txtImgProfile.setBackgroundResource(R.drawable.rounded_corner_light2);
                txtImgProfile.setTextColor(getResources().getColor(R.color.white));

            } else if (MainActivity.themeKEY.equals("0")) {
                linTop.setBackgroundColor(getResources().getColor(R.color.yellow));
                linMain.setBackgroundColor(getResources().getColor(R.color.darkDray));
                imgClose.setColorFilter(ContextCompat.getColor(ProfileActivity.this, R.color.darkDray), android.graphics.PorterDuff.Mode.MULTIPLY);
                imgUser.setColorFilter(ContextCompat.getColor(ProfileActivity.this, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
                imgMail.setColorFilter(ContextCompat.getColor(ProfileActivity.this, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
                imgPass.setColorFilter(ContextCompat.getColor(ProfileActivity.this, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
                imgViewPassword.setColorFilter(ContextCompat.getColor(ProfileActivity.this, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
                txtUpdate.setTextColor(getResources().getColor(R.color.darkDray));
                txtTitName.setTextColor(getResources().getColor(R.color.white));
                txtTitMail.setTextColor(getResources().getColor(R.color.white));
                txtTitPsw.setTextColor(getResources().getColor(R.color.white));
                linUpdate.setBackgroundResource(R.drawable.btn_login_yellow);
                edtName.setHintTextColor(getResources().getColor(R.color.color_gray));
                edtEmail.setHintTextColor(getResources().getColor(R.color.color_gray));
                edtPassword.setHintTextColor(getResources().getColor(R.color.color_gray));
                edtName.setTextColor(getResources().getColor(R.color.color_gray));
                edtEmail.setTextColor(getResources().getColor(R.color.color_gray));
                edtPassword.setTextColor(getResources().getColor(R.color.color_gray));
                view1.setBackgroundColor(getResources().getColor(R.color.yellow));
                view2.setBackgroundColor(getResources().getColor(R.color.yellow));
                view3.setBackgroundColor(getResources().getColor(R.color.yellow));
                txtImgProfile.setBackgroundResource(R.drawable.rounded_corner2);
                txtImgProfile.setTextColor(getResources().getColor(R.color.darkDray));
            }
        } else {
            linTop.setBackgroundColor(getResources().getColor(R.color.yellow));
            linMain.setBackgroundColor(getResources().getColor(R.color.darkDray));
            imgClose.setColorFilter(ContextCompat.getColor(ProfileActivity.this, R.color.darkDray), android.graphics.PorterDuff.Mode.MULTIPLY);
            imgUser.setColorFilter(ContextCompat.getColor(ProfileActivity.this, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
            imgMail.setColorFilter(ContextCompat.getColor(ProfileActivity.this, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
            imgPass.setColorFilter(ContextCompat.getColor(ProfileActivity.this, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
            imgViewPassword.setColorFilter(ContextCompat.getColor(ProfileActivity.this, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
            txtUpdate.setTextColor(getResources().getColor(R.color.darkDray));
            txtTitName.setTextColor(getResources().getColor(R.color.white));
            txtTitMail.setTextColor(getResources().getColor(R.color.white));
            txtTitPsw.setTextColor(getResources().getColor(R.color.white));
            linUpdate.setBackgroundResource(R.drawable.btn_login_yellow);
            edtName.setHintTextColor(getResources().getColor(R.color.color_gray));
            edtEmail.setHintTextColor(getResources().getColor(R.color.color_gray));
            edtPassword.setHintTextColor(getResources().getColor(R.color.color_gray));
            edtName.setTextColor(getResources().getColor(R.color.color_gray));
            edtEmail.setTextColor(getResources().getColor(R.color.color_gray));
            edtPassword.setTextColor(getResources().getColor(R.color.color_gray));
            view1.setBackgroundColor(getResources().getColor(R.color.yellow));
            view2.setBackgroundColor(getResources().getColor(R.color.yellow));
            view3.setBackgroundColor(getResources().getColor(R.color.yellow));
            txtImgProfile.setBackgroundResource(R.drawable.rounded_corner2);
            txtImgProfile.setTextColor(getResources().getColor(R.color.darkDray));
        }
    }

    private void selectImage() {
        final CharSequence[] options = {"Choose profile picture", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Add Profile!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
//                if (options[item].equals("Take Photo")) {
////                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
////                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
////                    startActivityForResult(intent, 1);
//
//                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(intent, CAMERA_REQUEST);
//                }
//                else
                if (options[item].equals("Choose profile picture")) {
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, GALLERY_REQUEST);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private boolean isStoragePermissionGranted() {

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            return true;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

           /* if (requestCode == CAMERA_REQUEST) {

                bitmap = (Bitmap) data.getExtras().get("data");
                if (bitmap != null) {
                    imgProfile.setVisibility(View.VISIBLE);
                    txtImgProfile.setVisibility(View.GONE);
                    imgProfile.setImageBitmap(bitmap);

                } else {
                    imgProfile.setVisibility(View.GONE);
                    txtImgProfile.setVisibility(View.VISIBLE);
                    txtImgProfile.setText(strFirstChar);
                }

                Bitmap OutImage = Bitmap.createScaledBitmap(bitmap, 1000, 1000, true);
                String camera_ImagePath = MediaStore.Images.Media.insertImage(ProfileActivity.this.getContentResolver(), OutImage, "Title", null);
                Toast.makeText(this, "camera_ImagePath : " + camera_ImagePath, Toast.LENGTH_SHORT).show();

                //File object of camera image
                File file = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");
                Log.e(TAG, "onActivityResult: " + file);

                //Uri of camera image
                Uri uri = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".fileprovider", file);
                Log.e(TAG, "onActivityResult: " + uri);

            } else */

            if (requestCode == GALLERY_REQUEST) {
                Uri selectedImage = data.getData();
                try {
                    if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && null != data) {
                        String[] filePathColumn = {MediaStore.MediaColumns.DATA};
                        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        picturepath = cursor.getString(columnIndex);
                        Log.e(TAG, "onActivityResult: " + picturepath);

                        cursor.close();

                        if (picturepath != null) {
                            imgProfile.setVisibility(View.VISIBLE);
                            txtImgProfile.setVisibility(View.GONE);
                            imgProfile.setImageBitmap(BitmapFactory.decodeFile(picturepath));
                            SPmanager.saveValue(ProfileActivity.this, "profile_image_path", picturepath);
                            postdata_();
                        } else {
                            imgProfile.setVisibility(View.GONE);
                            txtImgProfile.setVisibility(View.VISIBLE);
                            txtImgProfile.setText(strFirstChar);
                        }
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        }
    }

}
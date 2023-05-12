package com.soildersofcross.app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;

import com.soildersofcross.app.R;

public class ReferenceActivity extends AppCompatActivity {

    private static final String TAG = "ReferenceActivity";
    private ImageView imgDown;
    private WebView webViewRef;
    private String reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference);
        getSupportActionBar().hide();

        statusBarColor();
        reference = getIntent().getStringExtra("strReference");
        imgDown = findViewById(R.id.imgDown);
        webViewRef = findViewById(R.id.webViewRef);

        webViewRef.getSettings().setJavaScriptEnabled(true);
        webViewRef.loadUrl(reference);

        imgDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

                openActivityfromBottom();
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);


            }
        });
    }

    protected void openActivityfromBottom() {
        overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
    }

    private void statusBarColor() {
        try {
            if (Build.VERSION.SDK_INT >= 21) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(ContextCompat.getColor(this, R.color.gray));
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

}
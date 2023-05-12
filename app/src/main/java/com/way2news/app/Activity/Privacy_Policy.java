package com.way2news.app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import com.way2news.app.R;

public class Privacy_Policy extends AppCompatActivity {

    private static final String TAG = "Privacy Policy";
    private TextView tx_detail, tx_privacy;
    private ProgressBar progress, progress2;
    private RelativeLayout lay_header, relay_privacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy__policy);

        getSupportActionBar().hide();
        init();
        statusBarColor();
        getPrivacy_Policy();
        if (MainActivity.themeKEY != null) {
            if (MainActivity.themeKEY.equals("1")) {
                lay_header.setBackgroundColor(getResources().getColor(R.color.header));
                relay_privacy.setBackgroundColor(getResources().getColor(R.color.white));
                tx_privacy.setTextColor(getResources().getColor(R.color.darkDray));
                tx_detail.setTextColor(getResources().getColor(R.color.darkDray));
            }
        }
    }

    private void init() {
        tx_detail = findViewById(R.id.tx_detail);
        tx_privacy = findViewById(R.id.tx_privacy);
        progress = findViewById(R.id.progress);
        progress2 = findViewById(R.id.progress2);
        lay_header = findViewById(R.id.lay_header);
        relay_privacy = findViewById(R.id.relay_privacy);

    }

    private void getPrivacy_Policy() {
        if (MainActivity.themeKEY != null) {
            if (MainActivity.themeKEY.equals("1")) {
                progress2.setVisibility(View.VISIBLE);
            } else if (MainActivity.themeKEY.equals("0")) {
                progress.setVisibility(View.VISIBLE);
            }
        } else {
            progress.setVisibility(View.VISIBLE);
        }

        String url = getString(R.string.server_url) + "webservices/privacy.php";
        Log.e(TAG, "getPrivacy_Policy: " + url);

        RequestQueue requestQueue = Volley.newRequestQueue(Privacy_Policy.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e(TAG, "onResponse: " + response);
                try {

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("200")) {

                            JSONObject resp = jsonObject.getJSONObject("response");

                            JSONObject data = resp.getJSONObject("data");
                            JSONObject privacy = data.getJSONObject("privacy");
                            String privacy_policy = privacy.getString("privacy_policy");

                            progress.setVisibility(View.GONE);
                            progress2.setVisibility(View.GONE);
                            tx_detail.setText(Html.fromHtml(privacy_policy));

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                }
                            }, 200);

                        } else {
                            progress.setVisibility(View.GONE);
                            progress2.setVisibility(View.GONE);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e(TAG, "onResponse: " + e.getMessage());
                        progress.setVisibility(View.GONE);
                        progress2.setVisibility(View.GONE);

                    }


                } catch (Exception e) {
                    e.getMessage();
                    Log.e(TAG, "onResponse: " + e.getMessage());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(TAG, "onErrorResponse: " + error);
            }
        });
        requestQueue.add(stringRequest);
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

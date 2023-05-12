package com.way2news.app.Activity;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.way2news.app.BuildConfig;
import com.way2news.app.Fragment.Bookmark_Fragment;
import com.way2news.app.Fragment.CatListFragment;
import com.way2news.app.Fragment.MainFragment;
import com.way2news.app.Fragment.SettingFragment;
import com.way2news.app.R;
import com.way2news.app.utils.SPmanager;


import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "123";
    private static final String TAG = "Main--Actitivty---";
    public static DrawerLayout drawerlayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private boolean mSlideState = false;
    public static View lay_drawer;
    public TextToSpeech tts;
    private TextView title_main, title_cat, title_bookmark, title_Setting;
    private String msg, visibility, ad_type, bann_ads;
    private InterstitialAd fb_banner;
    private FirebaseAnalytics mFirebaseAnalytics;
    public static String themeKEY;
    private RelativeLayout relay_drawer, relBottom, lay_container, relay_adview;
    private LinearLayout lay_bookmark, lay_cat, lay_setting, lay_share, lay_rate, lay_otherApp;
    private LinearLayout lay_privacy, lay_aboutUs, layMenuHeader, lay_latestNews, layBG, ll_ads;
    private ImageView img_drawer, img_search, img_pin, imgLatest, imgCategory, imgBookmarkmenu, imgSetting, imgShareApp, imgRateApp, imgOtherApp, imgPrivacy, imgAboutUs;
    private TextView txtLatest, txtCategory, txtBookmark, txtSetting, txtShareApp,txtQuiz;
    private TextView txtRateApp, txtOtherApp, txtPrivacy, txtAboutUs, txtMenu;
    private String newsid, title, message, image;
    public static String isSearch = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        FirebaseApp.initializeApp(this);
        getSupportActionBar().hide();
        statusBarColor();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "mynotification";
            String description = "hello";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {

                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            msg = "Failed";
                            return;
                        }

                        String token = task.getResult().getToken();
                        Log.e(TAG, "onComplete: " + token);

                        Log.e(TAG, "onComplete: " + msg);
                    }
                });
        init();
        setupDrawer();
        themeKEY = SPmanager.getPreference(MainActivity.this, "themeKEY");
        Log.e(TAG, "onCreate: " + themeKEY);
        if (themeKEY != null) {
            if (themeKEY.equals("1")) {
                lay_drawer.setBackgroundColor(getResources().getColor(R.color.header));
                layBG.setBackgroundColor(getResources().getColor(R.color.darkDray));
                title_main.setTextColor(getResources().getColor(R.color.darkDray));
                title_cat.setTextColor(getResources().getColor(R.color.darkDray));
                title_bookmark.setTextColor(getResources().getColor(R.color.darkDray));
                img_drawer.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.darkDray), android.graphics.PorterDuff.Mode.MULTIPLY);
                img_search.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.darkDray), android.graphics.PorterDuff.Mode.MULTIPLY);
                relay_drawer.setBackgroundColor(getResources().getColor(R.color.white));

                layMenuHeader.setBackgroundColor(getResources().getColor(R.color.gray));
                txtMenu.setTextColor(getResources().getColor(R.color.white));

                txtLatest.setTextColor(getResources().getColor(R.color.white));
                txtBookmark.setTextColor(getResources().getColor(R.color.white));
                txtCategory.setTextColor(getResources().getColor(R.color.white));
                txtSetting.setTextColor(getResources().getColor(R.color.white));
                txtShareApp.setTextColor(getResources().getColor(R.color.white));
                txtRateApp.setTextColor(getResources().getColor(R.color.white));
                txtOtherApp.setTextColor(getResources().getColor(R.color.white));
                txtPrivacy.setTextColor(getResources().getColor(R.color.white));
                txtAboutUs.setTextColor(getResources().getColor(R.color.white));
                txtQuiz.setTextColor(getResources().getColor(R.color.white));

            }
            if (themeKEY.equals("0")) {
                lay_drawer.setBackgroundColor(getResources().getColor(R.color.yellow));
                title_main.setTextColor(getResources().getColor(R.color.darkDray));
                title_cat.setTextColor(getResources().getColor(R.color.darkDray));
                title_bookmark.setTextColor(getResources().getColor(R.color.darkDray));
                img_drawer.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.darkDray), android.graphics.PorterDuff.Mode.MULTIPLY);
                img_search.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.darkDray), android.graphics.PorterDuff.Mode.MULTIPLY);
                relay_drawer.setBackgroundColor(getResources().getColor(R.color.darkDray));
                layBG.setBackgroundColor(getResources().getColor(R.color.darkDray));

                layMenuHeader.setBackgroundColor(getResources().getColor(R.color.yellow));
                txtMenu.setTextColor(getResources().getColor(R.color.darkDray));

                imgLatest.setColorFilter(ContextCompat.getColor(this, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
                imgBookmarkmenu.setColorFilter(ContextCompat.getColor(this, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
                imgCategory.setColorFilter(ContextCompat.getColor(this, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
                imgSetting.setColorFilter(ContextCompat.getColor(this, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
                imgShareApp.setColorFilter(ContextCompat.getColor(this, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
                imgRateApp.setColorFilter(ContextCompat.getColor(this, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
                imgOtherApp.setColorFilter(ContextCompat.getColor(this, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
                imgPrivacy.setColorFilter(ContextCompat.getColor(this, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
                imgAboutUs.setColorFilter(ContextCompat.getColor(this, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
            }

        } else {
            lay_drawer.setBackgroundColor(getResources().getColor(R.color.yellow));
            title_main.setTextColor(getResources().getColor(R.color.darkDray));
            title_cat.setTextColor(getResources().getColor(R.color.darkDray));
            title_bookmark.setTextColor(getResources().getColor(R.color.darkDray));
            img_drawer.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.darkDray), android.graphics.PorterDuff.Mode.MULTIPLY);
            img_search.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.darkDray), android.graphics.PorterDuff.Mode.MULTIPLY);
            relay_drawer.setBackgroundColor(getResources().getColor(R.color.darkDray));
            layBG.setBackgroundColor(getResources().getColor(R.color.darkDray));

            layMenuHeader.setBackgroundColor(getResources().getColor(R.color.yellow));
            txtMenu.setTextColor(getResources().getColor(R.color.darkDray));

            imgLatest.setColorFilter(ContextCompat.getColor(this, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
            imgBookmarkmenu.setColorFilter(ContextCompat.getColor(this, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
            imgCategory.setColorFilter(ContextCompat.getColor(this, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
            imgSetting.setColorFilter(ContextCompat.getColor(this, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
            imgShareApp.setColorFilter(ContextCompat.getColor(this, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
            imgRateApp.setColorFilter(ContextCompat.getColor(this, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
            imgOtherApp.setColorFilter(ContextCompat.getColor(this, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
            imgPrivacy.setColorFilter(ContextCompat.getColor(this, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
            imgAboutUs.setColorFilter(ContextCompat.getColor(this, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);

        }
        Fragment fragment = new MainFragment();
        FragmentManager fragmentManager = ((MainActivity.this).getSupportFragmentManager());
        fragmentManager.beginTransaction().replace(R.id.lay_container, fragment).commit();
        get_Ads();
    }

    private void get_Ads() {

        String url = getString(R.string.server_url) + "webservices/ads.php?platform=android";
        Log.e(TAG, "get_Ads: " + url);

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e(TAG, "onResponse: " + response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("200")) {

                        JSONObject res = jsonObject.getJSONObject("response");
                        JSONObject data = res.getJSONObject("data");
                        visibility = data.getString("visibility");

                        if (visibility.equals("1")) {
                            JSONArray ads = data.getJSONArray("ads");
                            for (int i = 0; i < ads.length(); i++) {

                                JSONObject object = ads.getJSONObject(i);
                                ad_type = object.getString("type");
                                if (ad_type.equals("Admob")) {
                                    bann_ads = object.getString("banner_id");
                                    google_Banner(bann_ads);

                                } else {
                                    bann_ads = object.getString("banner_id");
                                    fB_Banner();
                                }
                            }
                        } else {
                            relBottom.setVisibility(View.GONE);
                            ll_ads.setVisibility(View.GONE);
                        }

                        Log.e(TAG, "onResponse: " + bann_ads);


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "onResponse: " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(TAG, "onErrorResponse: " + error.getMessage());
            }
        });
        requestQueue.add(stringRequest);
    }

    private void fB_Banner() {

        fb_banner = new com.facebook.ads.InterstitialAd(MainActivity.this, bann_ads);
        fb_banner.loadAd();

        final com.facebook.ads.AdView adView = new com.facebook.ads.AdView(MainActivity.this, bann_ads, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
        adView.loadAd();

        adView.setAdListener(new com.facebook.ads.AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e(TAG, "onError: " + adError);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                if (adView.getParent() != null)
                    ((ViewGroup) adView.getParent()).removeView(adView);
                ll_ads.setVisibility(View.VISIBLE);
                ll_ads.addView(adView);
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });
    }

    private void google_Banner(String bann_ads) {
        try {
            relBottom.setVisibility(View.VISIBLE);
            AdView mAdView = new AdView(MainActivity.this);
            mAdView.setAdSize(com.google.android.gms.ads.AdSize.BANNER);

            mAdView.setAdUnitId(bann_ads);
            relay_adview.addView(mAdView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);

        } catch (Exception e) {
            e.getMessage();
            Log.e(TAG, "google_Banner: " + e.getMessage());
        }
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

    @Override
    public void onBackPressed() {

        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        exitApp();
    }

    private void exitApp() {
        try {
            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            View view = getLayoutInflater().inflate(R.layout.dialog_exit_app, null, false);

            LinearLayout linExit = view.findViewById(R.id.linExit);
            FloatingActionButton floatingLight = view.findViewById(R.id.floatingLight);
            FloatingActionButton flotingDark = view.findViewById(R.id.flotingDark);
            TextView txtExit = view.findViewById(R.id.txtExit);
            TextView txtExitMsg = view.findViewById(R.id.txtExitMsg);
            TextView tx_yes = view.findViewById(R.id.tx_yes);
            TextView tx_no = view.findViewById(R.id.tx_no);

            if (themeKEY != null) {
                if (themeKEY.equals("1")) {
                    floatingLight.setVisibility(View.VISIBLE);
                    flotingDark.setVisibility(View.GONE);
                    linExit.setBackgroundResource(R.drawable.shape_light);
                    txtExit.setTextColor(getResources().getColor(R.color.darkDray));
                    txtExitMsg.setTextColor(getResources().getColor(R.color.darkDray));
                    tx_no.setTextColor(getResources().getColor(R.color.darkDray));
                    tx_yes.setTextColor(getResources().getColor(R.color.white));
                    tx_no.setBackgroundResource(R.drawable.button_no_light);
                    tx_yes.setBackgroundResource(R.drawable.button_no);

                } else if (themeKEY.equals("0")) {
                    floatingLight.setVisibility(View.GONE);
                    flotingDark.setVisibility(View.VISIBLE);
                    linExit.setBackgroundResource(R.drawable.shape);
                    txtExit.setTextColor(getResources().getColor(R.color.white));
                    txtExitMsg.setTextColor(getResources().getColor(R.color.white));
                    tx_no.setTextColor(getResources().getColor(R.color.white));
                    tx_yes.setTextColor(getResources().getColor(R.color.darkDray));
                    tx_no.setBackgroundResource(R.drawable.button_no);
                    tx_yes.setBackgroundResource(R.drawable.button_yes);

                }
            } else {
                floatingLight.setVisibility(View.GONE);
                flotingDark.setVisibility(View.VISIBLE);
                linExit.setBackgroundResource(R.drawable.shape);
                txtExit.setTextColor(getResources().getColor(R.color.white));
                txtExitMsg.setTextColor(getResources().getColor(R.color.white));
                tx_no.setTextColor(getResources().getColor(R.color.white));
                tx_yes.setTextColor(getResources().getColor(R.color.darkDray));
                tx_no.setBackgroundResource(R.drawable.button_no);
                tx_yes.setBackgroundResource(R.drawable.button_yes);
            }

            tx_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finishAffinity();
                }
            });

            tx_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
            dialog.setContentView(view);
            dialog.show();
        } catch (Exception e) {
            e.getMessage();
            Log.e(TAG, "exitApp: " + e.getMessage());
        }
    }

    public void stopAnimation(View v) {
        v.clearAnimation();
        if (canCancelAnimation()) {
            v.animate().cancel();
        }
    }

    public static boolean canCancelAnimation() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    private void init() {

        drawerlayout = findViewById(R.id.drawerlayout);
        img_drawer = findViewById(R.id.img_drawer);
        lay_drawer = findViewById(R.id.lay_drawer);
        lay_container = findViewById(R.id.lay_container);
        lay_latestNews = findViewById(R.id.l_latestNews);
        relBottom = findViewById(R.id.relBottom);
        title_main = findViewById(R.id.title_main);
        title_cat = findViewById(R.id.title_cat);
        title_bookmark = findViewById(R.id.title_bookmark);
        title_Setting = findViewById(R.id.title_Setting);
        lay_bookmark = findViewById(R.id.lay_bookmark);
        lay_cat = findViewById(R.id.lay_cat);
        lay_setting = findViewById(R.id.lay_setting);
        lay_share = findViewById(R.id.lay_share);
        lay_rate = findViewById(R.id.lay_rate);
        lay_otherApp = findViewById(R.id.lay_otherApp);
        lay_privacy = findViewById(R.id.lay_privacy);
        lay_aboutUs = findViewById(R.id.lay_aboutUs);
        layMenuHeader = findViewById(R.id.layMenuHeader);
        layBG = findViewById(R.id.layBG);
        txtMenu = findViewById(R.id.txtMenu);
        img_search = findViewById(R.id.img_search);
        img_pin = findViewById(R.id.img_pin);

        imgLatest = findViewById(R.id.imgLatest);
        imgBookmarkmenu = findViewById(R.id.imgBookmarkmenu);
        imgSetting = findViewById(R.id.imgSetting);
        imgCategory = findViewById(R.id.imgCategory);
        imgShareApp = findViewById(R.id.imgShareApp);
        imgRateApp = findViewById(R.id.imgRateApp);
        imgOtherApp = findViewById(R.id.imgOtherApp);
        imgPrivacy = findViewById(R.id.imgPrivacy);
        imgAboutUs = findViewById(R.id.imgAboutUs);

        txtLatest = findViewById(R.id.txtLatest);
        txtCategory = findViewById(R.id.txtCategory);
        txtBookmark = findViewById(R.id.txtBookmark);
        txtSetting = findViewById(R.id.txtSetting);
        txtShareApp = findViewById(R.id.txtShareApp);
        txtRateApp = findViewById(R.id.txtRateApp);
        txtOtherApp = findViewById(R.id.txtOtherApp);
        txtPrivacy = findViewById(R.id.txtPrivacy);
        txtAboutUs = findViewById(R.id.txtAboutUs);
        txtQuiz = findViewById(R.id.txtQuiz);


        ll_ads = findViewById(R.id.ll_ads);
        relay_adview = findViewById(R.id.relay_adview);
        relay_drawer = findViewById(R.id.relay_drawer);


        txtShareApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "SoildersOfCross");
                    String shareMessage= "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id="+ BuildConfig.APPLICATION_ID+"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });

        txtQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,QuizActivity.class);
                startActivity(intent);

            }
        });

        lay_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerlayout.closeDrawers();
                Fragment fragment = new SettingFragment();
                FragmentManager fragmentManager = ((MainActivity.this).getSupportFragmentManager());
                fragmentManager.beginTransaction().replace(R.id.lay_container, fragment).commit();
                title_Setting.setVisibility(View.VISIBLE);
                title_main.setVisibility(View.GONE);
                title_bookmark.setVisibility(View.GONE);
                title_cat.setVisibility(View.GONE);
                img_search.setVisibility(View.GONE);
//                img_pin.setVisibility(View.GONE);
                if (themeKEY != null) {
                    if (themeKEY.equals("1")) {
                        lay_drawer.setBackgroundColor(getResources().getColor(R.color.header));

                    } else if (themeKEY.equals(0)) {
                        lay_drawer.setBackgroundColor(getResources().getColor(R.color.yellow));
                    }
                } else {
                    lay_drawer.setBackgroundColor(getResources().getColor(R.color.yellow));
                }
            }
        });
        lay_latestNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawerlayout.closeDrawers();
                drawerlayout.closeDrawer(Gravity.LEFT);
                Fragment fragment = new MainFragment();
                Bundle bundle = new Bundle();
                bundle.putString("title", title);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = ((MainActivity.this).getSupportFragmentManager());
                fragmentManager.beginTransaction().replace(R.id.lay_container, fragment).commit();

                title_main.setVisibility(View.VISIBLE);
                img_search.setVisibility(View.VISIBLE);
//                img_pin.setVisibility(View.VISIBLE);
                title_bookmark.setVisibility(View.GONE);
                title_cat.setVisibility(View.GONE);
                title_Setting.setVisibility(View.GONE);
                if (themeKEY != null) {
                    if (themeKEY.equals("1")) {
                        lay_drawer.setBackgroundColor(getResources().getColor(R.color.header));

                    } else if (themeKEY.equals(0)) {
                        lay_drawer.setBackgroundColor(getResources().getColor(R.color.yellow));
                    }
                } else {
                    lay_drawer.setBackgroundColor(getResources().getColor(R.color.yellow));
                }
            }
        });
        img_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerlayout.openDrawer(GravityCompat.START);

            }
        });
        lay_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerlayout.closeDrawers();
                drawerlayout.closeDrawer(Gravity.LEFT);

                Fragment fragment = new Bookmark_Fragment();
                FragmentManager fragmentManager = ((MainActivity.this).getSupportFragmentManager());
                fragmentManager.beginTransaction().replace(R.id.lay_container, fragment).commit();
                title_main.setVisibility(View.GONE);
                title_bookmark.setVisibility(View.VISIBLE);
                img_search.setVisibility(View.GONE);
//                img_pin.setVisibility(View.GONE);
                title_cat.setVisibility(View.GONE);
                title_Setting.setVisibility(View.GONE);
                if (themeKEY != null) {
                    if (themeKEY.equals("1")) {
                        lay_drawer.setBackgroundColor(getResources().getColor(R.color.header));

                    } else if (themeKEY.equals(0)) {
                        lay_drawer.setBackgroundColor(getResources().getColor(R.color.yellow));
                    }
                } else {
                    lay_drawer.setBackgroundColor(getResources().getColor(R.color.yellow));
                }
            }
        });
        lay_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerlayout.closeDrawers();
                Fragment fragment = new CatListFragment();
                FragmentManager fragmentManager = ((MainActivity.this).getSupportFragmentManager());
                fragmentManager.beginTransaction().replace(R.id.lay_container, fragment).commit();
                title_cat.setVisibility(View.VISIBLE);
                img_search.setVisibility(View.GONE);
//                img_pin.setVisibility(View.GONE);
                title_main.setVisibility(View.GONE);
                title_bookmark.setVisibility(View.GONE);
                title_Setting.setVisibility(View.GONE);
                if (themeKEY != null) {
                    if (themeKEY.equals("1")) {
                        lay_drawer.setBackgroundColor(getResources().getColor(R.color.header));

                    } else if (themeKEY.equals(0)) {
                        lay_drawer.setBackgroundColor(getResources().getColor(R.color.yellow));
                    }
                } else {
                    lay_drawer.setBackgroundColor(getResources().getColor(R.color.yellow));
                }
            }
        });
        lay_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerlayout.closeDrawers();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.sharing_text) + "\n" +
                        "https://play.google.com/store/apps/details?id=" + getPackageName());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
        lay_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        lay_otherApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        lay_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerlayout.closeDrawers();
                Intent intent = new Intent(MainActivity.this, Privacy_Policy.class);
                startActivity(intent);
            }
        });
        lay_aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerlayout.closeDrawers();
                Intent intent = new Intent(MainActivity.this, About_Us.class);
                startActivity(intent);
            }
        });
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isSearch = "yes";
                Intent intent = new Intent(MainActivity.this, Search_Activity.class);
                startActivity(intent);

            }
        });
    }

    public void onDestroy() {

        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();

    }

    private void setupDrawer() {

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerlayout, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mSlideState = true;//is Opened
                drawerView.setEnabled(true);

            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                view.setEnabled(false);
                mSlideState = false;
                invalidateOptionsMenu();
                stopAnimation(view);

            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
//        drawerlayout.addDrawerListener(mDrawerToggle);
        drawerlayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull final View drawerView) {

                new Handler().post(new Runnable() {
                    public void run() {

                        drawerlayout.closeDrawer(drawerView);
                    }
                });
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                break;
        }
        drawerlayout.openDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);
    }

}

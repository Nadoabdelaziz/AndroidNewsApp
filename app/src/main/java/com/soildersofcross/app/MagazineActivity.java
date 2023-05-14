package com.soildersofcross.app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.soildersofcross.app.Activity.MainActivity;
import com.soildersofcross.app.Adapter.ViewPagerAdapter2;
import com.soildersofcross.app.Adapter.ViewPagerAdapter3;
import com.soildersofcross.app.Model_Class.NewsData;
import com.soildersofcross.app.utils.SPmanager;
import com.soildersofcross.app.utils.SqliteHelper;
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class MagazineActivity extends AppCompatActivity {

    private static final String TAG = "Fragment Main";
    private static final String ADMOB_AD_UNIT_ID = "ca-app-pub-3940256099942544/2247696110";
    private ProgressBar mainProgress, mainProgress2;
    private int pageNo = 1, pos;
    public static int noOfrecords = 10;
    ArrayList<NewsData> imageList = new ArrayList<>();
    private TextToSpeech tts;
    private int counterPageScroll;
    private RelativeLayout  relayMainFrag;
    private SqliteHelper sqliteHelper;
    String[] codelist = new String[]{"ar", "zh", "cs", "da", "nl", "en", "fi", "fr", "de", "el", "he", "hi", "hu", "id", "it", "ja", "ko", "no", "pl", "pt", "ro", "ru", "sk", "es", "sv", "th", "tr", "te", "mr"};
    private TextView tx_noData;
    private String url;
    public static String strSTATEId, strCITYId, mediaLink;
    private ViewPager2 viewPager2;
    private ViewPagerAdapter3 viewPagerAdapter;
    ViewFlipper viewFlipper;
    private String username, email, password;
    private FrameLayout frameLayout;
    private UnifiedNativeAd nativeAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazine);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        mainProgress = (ProgressBar) findViewById(R.id.mainProgress);
        mainProgress2 = (ProgressBar) findViewById(R.id.mainProgress2);

        tx_noData = findViewById(R.id.tx_noData);
        relayMainFrag =findViewById(R.id.relayMainFrag);
        viewPager2 = findViewById(R.id.viewPager2Main);
        viewFlipper =findViewById(R.id.viewFlipper);
        frameLayout = findViewById(R.id.frameLayout);

        getPreferenceData();
        init();
        getData();
        setAppTheme();

        if (getString(R.string.tts_visibility).equals("yes")) {
            if (MainActivity.themeKEY != null) {
                if (MainActivity.themeKEY.equals("1")) {

//                    btn_pause.setVisibility(View.VISIBLE);

                } else if (MainActivity.themeKEY.equals("0")) {
//                    btn_pause.setVisibility(View.VISIBLE);
                         }
            } else {
//                btn_pause.setVisibility(View.VISIBLE);
                }
        } else {
        }

    }



    private void getPreferenceData() {
        password = SPmanager.getPreference(this, "password");
        username = SPmanager.getPreference(this, "username");
        email = SPmanager.getPreference(this, "email");

        Log.e(TAG, "getPreferenceData: " + password + username + email);
    }

    private void init() {

        strSTATEId = SPmanager.getPreference(this, "strSTATEId");
        if (strSTATEId == null) {
            SPmanager.saveValue(this, "strSTATEId", "");
            strSTATEId = "";
        }

        strCITYId = SPmanager.getPreference(this, "strCITYId");
        if (strCITYId == null) {
            SPmanager.saveValue(this, "strCITYId", "");
            strCITYId = "";
        }

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.getDefault());
                } else {
                    Toast.makeText(MagazineActivity.this, "Language Not Suppourted !", Toast.LENGTH_SHORT).show();
                }
            }
        });


        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);

                pos = position;
                Log.d(TAG, "onPageSelected mussadiq: "+position);
                //Log.d(TAG, "onPageScrolled: "+pos);
                try {
                    if (position == imageList.size() - 1 && (int) positionOffset == 0 /*&& !isLastPageSwiped*/) {
                        if (counterPageScroll != 0) {
                            pageNo = pageNo + 1;
                            loadMore();

                        }
                        counterPageScroll++;
                    } else {
                        counterPageScroll = 0;
                    }
                } catch (Exception e) {
                    e.getMessage();
                    Log.e(TAG, "onPageScrolled: " + e.getMessage());
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                pos = position;



               /* if (imageList.get(pos).getIs_image().equals("2") && !(imageList.get(pos).getMediaLink().equals(""))) {
//                Youtube image
                    try {

                        Intent intent = new Intent(this, VideoPlayer_Activity.class);
                        intent.putExtra("mediaLink", imageList.get(pos).getMediaLink());
                        startActivity(intent);
                        this.overridePendingTransition(R.anim.bottom_up, R.anim.bottom_up);

                    } catch (Exception e) {
                        e.getMessage();
                        Log.e(TAG, "onBindViewHolder: " + e.getMessage());
                    }
                }*/

                /*if (imageList.get(pos).getCat_id().equals(imageList.get(pos+1).getCat_id())){

                    viewPager2.post(new Runnable() {
                        @Override
                        public void run() {
                            viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);

                            //viewPager2.notify();
                        }
                    });

                }else {

                    viewPager2.post(new Runnable() {
                        @Override
                        public void run() {
                            viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

                        }
                    });

                }*/



               /* if (imageList.size() != 0) {
                    try {
                        supportLan(imageList.get(pos).getDescription());
                    } catch (Exception e) {
                        Log.e(TAG, "onPageSelected: " + e.getMessage());
                    }
                }*/
//                btn_play.setVisibility(View.GONE);
                showdb();
                Log.e("Selected_Page", String.valueOf(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);

                if (imageList.size() != 0) {
                    try {
                        // supportLan(imageList.get(pos).getDescription());

//                        if (imageList.size() % 4 == 0) {
//                        if (pos == 2) {
//                            refreshAd();
//                        }

                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
                tts.stop();
            }
        });




    }

    private void setAppTheme() {
        if (MainActivity.themeKEY != null) {
            if (MainActivity.themeKEY.equals("1")) {
                mainProgress2.setVisibility(View.VISIBLE);
                } else if (MainActivity.themeKEY.equals("0")) {
                mainProgress.setVisibility(View.VISIBLE);
            }
        } else {
            mainProgress.setVisibility(View.VISIBLE);
        }
    }

    private void showdb() {

       /* try {
            sqliteHelper = new SqliteHelper(getActivity());
            SQLiteDatabase db1 = sqliteHelper.getWritableDatabase();
            Cursor cur = db1.rawQuery("SELECT * FROM bookmark Where newsID ='" + imageList.get(pos).getId1() + "';", null);

            if (cur.moveToFirst()) {

                img_Deletebookmark.setVisibility(View.VISIBLE);
                img_Addbookmark.setVisibility(View.GONE);
//                Toast.makeText(MainActivity.this, "Already Added !", Toast.LENGTH_SHORT).show();
                if (MainActivity.themeKEY != null) {
                    if (MainActivity.themeKEY.equals("1")) {
                        img_Deletebookmark.setColorFilter(ContextCompat.getColor(getActivity(), R.color.darkDray), android.graphics.PorterDuff.Mode.MULTIPLY);
                        img_Addbookmark.setColorFilter(ContextCompat.getColor(getActivity(), R.color.darkDray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    } else if (MainActivity.themeKEY.equals("0")) {
                        img_Deletebookmark.setColorFilter(ContextCompat.getColor(getActivity(), R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
                        img_Addbookmark.setColorFilter(ContextCompat.getColor(getActivity(), R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
                    }
                }
                cur.close();
                db1.close();

            } else {
                img_Deletebookmark.setVisibility(View.GONE);
                img_Addbookmark.setVisibility(View.VISIBLE);

                cur.close();
                db1.close();
            }

        } catch (Exception e) {

            e.getMessage();
            e.printStackTrace();
        }*/
    }

    private void getData() {
        imageList.clear();
        pageNo = 1;
        mainProgress.setVisibility(View.VISIBLE);

        Log.d("TAG", "getData: "+strCITYId + strSTATEId);
        url = getString(R.string.server_url) + "webservices/news.php?page=" + pageNo + "&pp=" + noOfrecords + "&state_id="+strSTATEId  + "&city_id="+strCITYId;
        Log.e(TAG, "getData: " + url);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {

                Log.e(TAG, "onResponse: " + response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("200")) {

                        JSONObject resp = jsonObject.getJSONObject("response");

                        //JSONObject data = resp.getJSONObject("data");
                        JSONArray data = resp.getJSONArray("data");
                        //JSONArray news = data.getJSONArray("news");

                       /* if (news.length() == 0) {
                            tx_noData.setVisibility(View.VISIBLE);
                            tx_noData.setText("No news avilable");
                        }*/


                        for (int i = 0; i < data.length(); i++) {

                            JSONObject object = data.getJSONObject(i);
                            JSONArray newsdata = object.getJSONArray("news");
                            ArrayList<NewsData.Model_News> newsDataList = new ArrayList<>();


                            for (int j = 0; j < newsdata.length(); j++) {

                                NewsData.Model_News model_class = new NewsData.Model_News();
                                JSONObject jsonObject1 = newsdata.getJSONObject(j);

                                String id = jsonObject1.getString("id");
                                String cat_id = jsonObject1.getString("cat_id");
                                String thumbnail = jsonObject1.getString("thambnail");
                                String short_desc = jsonObject1.getString("short_description");
                                String long_desc = jsonObject1.getString("long_description");
                                String media = jsonObject1.getString("media");
                                mediaLink = jsonObject1.getString("Medialink");
                                String is_image = jsonObject1.getString("is_image");
                                String news_date = jsonObject1.getString("news_date");
                                String reference = jsonObject1.getString("reference");
                                String shortname = jsonObject1.getString("shortname");

                                Log.d("media", "onResponse: here "+is_image);
                                if (is_image.equals("1")) {
                                    model_class.setId1(id);
                                    model_class.setCat_id(cat_id);
                                    model_class.setImage(thumbnail);
                                    model_class.setShort_desc(short_desc);
                                    model_class.setDescription(long_desc);
                                    model_class.setMedia(media);
                                    model_class.setMediaLink(mediaLink);
                                    model_class.setIs_image(is_image);
                                    model_class.setNews_date(news_date);
                                    model_class.setReference(reference);
                                    model_class.setShortname(shortname);

                                    newsDataList.add(model_class);
                                }

                            }
                            if (newsDataList.size()!=0){
                                imageList.add(new NewsData(newsDataList));
                            }

                        }

                        Log.d(TAG, "onResponse: "+imageList.size());
                      /*  imageList.sort(new Comparator<Model_News>() {
                            @Override
                            public int compare(Model_News model_news, Model_News t1) {
                                return Integer.valueOf(t1.getCat_id()).compareTo(Integer.valueOf(model_news.getCat_id()));
                            }
                        });*/
                        setui();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (imageList.size() == 0) {
                                    mainProgress.setVisibility(View.GONE);
                                    mainProgress2.setVisibility(View.GONE);
                                } else {
                                    mainProgress.setVisibility(View.GONE);
                                    mainProgress2.setVisibility(View.GONE);
                                }
                            }
                        }, 200);

                    } else {
                        mainProgress.setVisibility(View.GONE);
                        mainProgress2.setVisibility(View.GONE);
                        tx_noData.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "onResponse: " + e.getMessage());
                    mainProgress.setVisibility(View.GONE);
                    mainProgress2.setVisibility(View.GONE);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
                Log.e(TAG, "onErrorResponse: " + error.getMessage());
                Toast.makeText(MagazineActivity.this, "Network Error", Toast.LENGTH_SHORT).show();

            }
        });
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        requestQueue.add(stringRequest);
    }


    private void setui() {

        // viewPagerAdapter = new ViewPagerAdapter(getActivity(), imageList, viewPager2);
        viewPagerAdapter = new ViewPagerAdapter3(this, imageList, viewPager2);
        /* viewPager2.setAdapter(viewPagerAdapter);*/


        // Create an object of page transformer
        BookFlipPageTransformer2 bookFlipPageTransformer = new BookFlipPageTransformer2();
        // CardFlipPageTransformer2 cardFlipPageTransformer = new CardFlipPageTransformer2();
        // Enable / Disable scaling while flipping. If true, then next page will scale in (zoom in). By default, its true.
        // bookFlipPageTransformer.setEnableScale(true);

        // The amount of scale the page will zoom. By default, its 5 percent.
        //  bookFlipPageTransformer.setScaleAmountPercent(5f);
        // viewPager2.setPageTransformer(new DepthTransformation());
        viewPager2.setPageTransformer(bookFlipPageTransformer);
        viewPager2.setOffscreenPageLimit(imageList.size());
        viewPager2.setAdapter(viewPagerAdapter);
        viewPagerAdapter.notifyDataSetChanged();

        try {
            // supportLan(imageList.get(pos).getDescription());
        } catch (Exception e) {
            e.getMessage();
            Log.e(TAG, "setui: " + e.getMessage());
        }
        showdb();

    }

    private void loadMore() {
        if (MainActivity.themeKEY != null) {
            if (MainActivity.themeKEY.equals("1")) {
                mainProgress2.setVisibility(View.VISIBLE);
            } else if (MainActivity.themeKEY.equals("0")) {
                mainProgress.setVisibility(View.VISIBLE);
            }
        } else {
            mainProgress.setVisibility(View.VISIBLE);
        }

        url = getString(R.string.server_url) + "webservices/news.php?page=" + pageNo + "&pp=" + noOfrecords + "&state_id=" + strSTATEId + "&city_id=" + strCITYId;
        Log.e(TAG, "getData: " + url);

        final ArrayList<NewsData> dataList = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e(TAG, "onResponse: " + response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("200")) {

                        JSONObject resp = jsonObject.getJSONObject("response");

                        JSONArray data = resp.getJSONArray("data");

                        if (data.length() == 0) {
                            Toast.makeText(MagazineActivity.this, "No more News !", Toast.LENGTH_SHORT).show();
                        }

                        for (int i = 0; i < data.length(); i++) {

                            JSONObject object = data.getJSONObject(i);
                            JSONArray newsdata = object.getJSONArray("news");
                            ArrayList<NewsData.Model_News> newsDataList = new ArrayList<>();


                            for (int j = 0; j < newsdata.length(); j++) {

                                NewsData.Model_News model_class = new NewsData.Model_News();
                                JSONObject jsonObject1 = newsdata.getJSONObject(j);

                                String id = jsonObject1.getString("id");
                                String cat_id = jsonObject1.getString("cat_id");
                                String thumbnail = jsonObject1.getString("thambnail");
                                String short_desc = jsonObject1.getString("short_description");
                                String long_desc = jsonObject1.getString("long_description");
                                String media = jsonObject1.getString("media");
                                mediaLink = jsonObject1.getString("Medialink");
                                String is_image = jsonObject1.getString("is_image");
                                String news_date = jsonObject1.getString("news_date");
                                String reference = jsonObject1.getString("reference");
                                String shortname = jsonObject1.getString("shortname");

                                if (is_image.equals("1")) 
                                {
                                    model_class.setId1(id);
                                model_class.setCat_id(cat_id);
                                model_class.setImage(thumbnail);
                                model_class.setShort_desc(short_desc);
                                model_class.setDescription(long_desc);
                                model_class.setMedia(media);
                                model_class.setMediaLink(mediaLink);
                                model_class.setIs_image(is_image);
                                model_class.setNews_date(news_date);
                                model_class.setReference(reference);
                                model_class.setShortname(shortname);

                                newsDataList.add(model_class);
                            }

                            }
                            if (newsDataList.size()!=0){
                                dataList.add(new NewsData(newsDataList));
                            }

                        }
                        mainProgress.setVisibility(View.GONE);
                        mainProgress2.setVisibility(View.GONE);

                        if (dataList.size() != 0) {
                            Log.e("adapter", "" + dataList.size());
                            viewPagerAdapter.addItem(dataList, imageList.size());
                        }

                    } else {

                        if (imageList.size() != -1) {
                            mainProgress.setVisibility(View.GONE);
                            mainProgress2.setVisibility(View.GONE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "onResponse: " + e.getMessage());
                    mainProgress.setVisibility(View.GONE);
                    mainProgress2.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: " + error.getMessage());
                mainProgress.setVisibility(View.GONE);
                mainProgress2.setVisibility(View.GONE);
            }
        });
        requestQueue.add(stringRequest);
    }


}
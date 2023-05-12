package com.way2news.app.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.languageid.FirebaseLanguageIdentification;
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import com.way2news.app.Activity.MainActivity;
import com.way2news.app.Adapter.ViewPagerAdapter2;
import com.way2news.app.Model_Class.NewsData;
import com.way2news.app.R;
import com.way2news.app.utils.SPmanager;
import com.way2news.app.utils.SqliteHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements ViewTreeObserver.OnGlobalLayoutListener {

    private static final String TAG = "Fragment Main";
    private static final String ADMOB_AD_UNIT_ID = "ca-app-pub-3940256099942544/2247696110";
    private ProgressBar mainProgress, mainProgress2;
    private int pageNo = 1, pos;
    public static int noOfrecords = 10;
    ArrayList<NewsData> imageList = new ArrayList<>();
    private ImageView btn_play, btn_pause, img_Addbookmark, img_Deletebookmark, imgComment;
    private TextToSpeech tts;
    private int counterPageScroll;
    private RelativeLayout lay_btnplay, layBookmark, relayMainFrag, relay_Bott;
    private SqliteHelper sqliteHelper;
    String[] codelist = new String[]{"ar", "zh", "cs", "da", "nl", "en", "fi", "fr", "de", "el", "he", "hi", "hu", "id", "it", "ja", "ko", "no", "pl", "pt", "ro", "ru", "sk", "es", "sv", "th", "tr", "te", "mr"};
    private TextView tx_noData;
    private String url;
    public static String strSTATEId, strCITYId, mediaLink;
    private ViewPager2 viewPager2;
    private ViewPagerAdapter2 viewPagerAdapter;
    ViewFlipper viewFlipper;
    private String username, email, password;
    private FrameLayout frameLayout;
    private UnifiedNativeAd nativeAd;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mainProgress = (ProgressBar) view.findViewById(R.id.mainProgress);
        mainProgress2 = (ProgressBar) view.findViewById(R.id.mainProgress2);
        btn_play = (ImageView) view.findViewById(R.id.btn_playy);
        btn_pause = (ImageView) view.findViewById(R.id.btn_pause);
        img_Addbookmark = (ImageView) view.findViewById(R.id.img_Addbookmark);
        img_Deletebookmark = (ImageView) view.findViewById(R.id.img_Deletebookmark);
        lay_btnplay = view.findViewById(R.id.lay_btnplay);
        tx_noData = view.findViewById(R.id.tx_noData);
        layBookmark = view.findViewById(R.id.layBookmark);
        relayMainFrag = view.findViewById(R.id.relayMainFrag);
        viewPager2 = view.findViewById(R.id.viewPager2Main);
        relay_Bott = view.findViewById(R.id.relay_Bott);
        viewFlipper = view.findViewById(R.id.viewFlipper);
        imgComment = view.findViewById(R.id.imgComment);
        frameLayout = view.findViewById(R.id.frameLayout);

        getPreferenceData();
        init();
        getData();
        setAppTheme();

        if (getString(R.string.tts_visibility).equals("yes")) {
            lay_btnplay.setVisibility(View.VISIBLE);
            if (MainActivity.themeKEY != null) {
                if (MainActivity.themeKEY.equals("1")) {

//                    btn_pause.setVisibility(View.VISIBLE);
                    btn_play.setVisibility(View.VISIBLE);
                    btn_play.setColorFilter(ContextCompat.getColor(getActivity(), R.color.darkDray), android.graphics.PorterDuff.Mode.MULTIPLY);

                } else if (MainActivity.themeKEY.equals("0")) {
//                    btn_pause.setVisibility(View.VISIBLE);
                    btn_play.setVisibility(View.VISIBLE);
                    btn_play.setColorFilter(ContextCompat.getColor(getActivity(), R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
                }
            } else {
//                btn_pause.setVisibility(View.VISIBLE);
                btn_play.setVisibility(View.VISIBLE);
                btn_play.setColorFilter(ContextCompat.getColor(getActivity(), R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
            }
        } else {
            lay_btnplay.setVisibility(View.GONE);
        }

        return view;

    }

    private void getPreferenceData() {
        password = SPmanager.getPreference(getActivity(), "password");
        username = SPmanager.getPreference(getActivity(), "username");
        email = SPmanager.getPreference(getActivity(), "email");

        Log.e(TAG, "getPreferenceData: " + password + username + email);
    }

    private void init() {

        strSTATEId = SPmanager.getPreference(getActivity(), "strSTATEId");
        if (strSTATEId == null) {
            SPmanager.saveValue(getActivity(), "strSTATEId", "");
            strSTATEId = "";
        }

        strCITYId = SPmanager.getPreference(getActivity(), "strCITYId");
        if (strCITYId == null) {
            SPmanager.saveValue(getActivity(), "strCITYId", "");
            strCITYId = "";
        }

        tts = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.getDefault());
                } else {
                    Toast.makeText(getActivity(), "Language Not Suppourted !", Toast.LENGTH_SHORT).show();
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

                        Intent intent = new Intent(getActivity(), VideoPlayer_Activity.class);
                        intent.putExtra("mediaLink", imageList.get(pos).getMediaLink());
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.bottom_up, R.anim.bottom_up);

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
                btn_pause.setVisibility(View.GONE);
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
                btn_pause.setVisibility(View.GONE);
                tts.stop();
            }
        });

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* btn_pause.setVisibility(View.VISIBLE);
                btn_play.setVisibility(View.INVISIBLE);
                btnPlayTheme();

                try {
                    String speak = imageList.get(pos).getDescription().toString();
                    tts.speak(String.valueOf(Html.fromHtml(speak)), QUEUE_ADD, null);
                    speakOut();

                } catch (Exception e) {
                    e.getMessage();
                    Log.e(TAG, "onClick: " + e.getMessage());
                }*/
            }
        });

        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_play.setVisibility(View.VISIBLE);
                btn_pause.setVisibility(View.INVISIBLE);
                tts.stop();

            }
        });

        img_Addbookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addBookmark();

            }
        });

        img_Deletebookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteData();

            }
        });

        imgComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* MainActivity.isSearch = "no";
                Intent intent = new Intent(getActivity(), CommentActivity.class);
                intent.putExtra("news_id", imageList.get(pos).getId1());
                SPmanager.saveValue(getActivity(), "news_id", imageList.get(pos).getId1());
                startActivity(intent);*/
            }
        });
    }

    private void setAppTheme() {
        if (MainActivity.themeKEY != null) {
            if (MainActivity.themeKEY.equals("1")) {
                layBookmark.setBackgroundColor(getResources().getColor(R.color.footercolor));
                mainProgress2.setVisibility(View.VISIBLE);
                img_Addbookmark.setColorFilter(ContextCompat.getColor(getActivity(), R.color.darkDray), android.graphics.PorterDuff.Mode.MULTIPLY);
                imgComment.setColorFilter(ContextCompat.getColor(getActivity(), R.color.darkDray), android.graphics.PorterDuff.Mode.MULTIPLY);
            } else if (MainActivity.themeKEY.equals("0")) {
                layBookmark.setBackgroundColor(getResources().getColor(R.color.gray));
                img_Deletebookmark.setColorFilter(ContextCompat.getColor(getActivity(), R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
                img_Addbookmark.setColorFilter(ContextCompat.getColor(getActivity(), R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
                imgComment.setColorFilter(ContextCompat.getColor(getActivity(), R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
                mainProgress.setVisibility(View.VISIBLE);
            }
        } else {
            layBookmark.setBackgroundColor(getResources().getColor(R.color.gray));
            img_Deletebookmark.setColorFilter(ContextCompat.getColor(getActivity(), R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
            img_Addbookmark.setColorFilter(ContextCompat.getColor(getActivity(), R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
            imgComment.setColorFilter(ContextCompat.getColor(getActivity(), R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
            mainProgress.setVisibility(View.VISIBLE);
        }
    }

    private void btnPlayTheme() {
        if (MainActivity.themeKEY != null) {
            if (MainActivity.themeKEY.equals("1")) {
                btn_pause.setColorFilter(ContextCompat.getColor(getActivity(), R.color.darkDray), android.graphics.PorterDuff.Mode.MULTIPLY);

            } else if (MainActivity.themeKEY.equals("0")) {
                btn_pause.setColorFilter(ContextCompat.getColor(getActivity(), R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
            }
        } else {
            btn_pause.setColorFilter(ContextCompat.getColor(getActivity(), R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
        }
    }

    private void supportLan(String description) {

        final FirebaseLanguageIdentification languageIdentifier = FirebaseNaturalLanguage.getInstance().getLanguageIdentification();
        languageIdentifier.identifyLanguage(String.valueOf(Html.fromHtml(description)))
                .addOnSuccessListener(
                        new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(@Nullable String languageCode) {
                                if (languageCode != "und") {

                                    if (Arrays.asList(codelist).contains(languageCode)) {
                                        btn_play.setVisibility(View.VISIBLE);
                                    } else {
                                        btn_play.setVisibility(View.INVISIBLE);
//                                        btn_pause.setVisibility(View.INVISIBLE);
                                    }

                                    Log.i("", "Language: " + languageCode);

                                } else {
                                    Log.i("", "Can't identify language.");
                                }
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Log.e(TAG, "onFailure: " + e.getMessage());
                            }
                        });
    }

    private void addBookmark() {
        /*try {
            sqliteHelper = new SqliteHelper(getActivity());
            SQLiteDatabase db1 = sqliteHelper.getWritableDatabase();
            Cursor cur = db1.rawQuery("SELECT * FROM bookmark Where newsID ='" + imageList.get(pos).getId1() + "';", null);

            if (cur.moveToFirst()) {

                deleteData();
                cur.close();
                db1.close();

            } else {

                insertData();
                cur.close();
                db1.close();
            }

        } catch (Exception e) {

            e.getMessage();
            e.printStackTrace();
        }*/

    }

    private void insertData() {
     /*   try {
            sqliteHelper = new SqliteHelper(getActivity());
            SQLiteDatabase db1 = sqliteHelper.getWritableDatabase();
            ContentValues insertValues = new ContentValues();
            insertValues.put("newsID", imageList.get(pos).getId1());
            insertValues.put("short_desc", imageList.get(pos).getShort_desc());
            insertValues.put("long_desc", imageList.get(pos).getDescription());
            insertValues.put("image", imageList.get(pos).getImage());
            insertValues.put("medialink", imageList.get(pos).getMediaLink());
            insertValues.put("media", imageList.get(pos).getMedia());
            insertValues.put("is_image", imageList.get(pos).getIs_image());
            insertValues.put("news_date", imageList.get(pos).getNews_date());
            insertValues.put("reference", imageList.get(pos).getReference());
            insertValues.put("short_name", imageList.get(pos).getShortname());

            Log.e(TAG, "insertData: " + imageList.get(pos).getShort_desc());

            db1.insert("bookmark", null, insertValues);
            db1.close();
            img_Deletebookmark.setVisibility(View.VISIBLE);
            img_Addbookmark.setVisibility(View.GONE);

            if (MainActivity.themeKEY != null) {
                if (MainActivity.themeKEY.equals("1")) {
                    img_Deletebookmark.setColorFilter(ContextCompat.getColor(getActivity(), R.color.darkDray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    img_Addbookmark.setColorFilter(ContextCompat.getColor(getActivity(), R.color.darkDray), android.graphics.PorterDuff.Mode.MULTIPLY);
                } else if (MainActivity.themeKEY.equals("0")) {
                    img_Deletebookmark.setColorFilter(ContextCompat.getColor(getActivity(), R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
                    img_Addbookmark.setColorFilter(ContextCompat.getColor(getActivity(), R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
                }
            } else {
                img_Deletebookmark.setColorFilter(ContextCompat.getColor(getActivity(), R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
                img_Addbookmark.setColorFilter(ContextCompat.getColor(getActivity(), R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
            }

        } catch (Exception e) {

            e.printStackTrace();
            Log.e("table Add bookmark....", "onMinusClicked: " + e.getMessage());
        }*/

    }

    private void deleteData() {

       /* sqliteHelper = new SqliteHelper(getActivity());
        SQLiteDatabase db1 = sqliteHelper.getWritableDatabase();
        db1.execSQL("DELETE FROM bookmark Where newsID ='" + imageList.get(pos).getId1() + "';");
        db1.close();
        img_Deletebookmark.setVisibility(View.GONE);
        img_Addbookmark.setVisibility(View.VISIBLE);*/
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

    private void speakOut() {

        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String s) {

                final String keyword = s;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

            @Override
            public void onDone(String s) {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btn_pause.setVisibility(View.GONE);
                        btn_play.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onError(String s) {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }

        });

        /*Bundle params = new Bundle();
        params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "");
        String text = String.valueOf(Html.fromHtml(imageList.get(pos).getDescription()));
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, params, "");*/

    }

    private void getData() {
        imageList.clear();
        pageNo = 1;
        mainProgress.setVisibility(View.VISIBLE);
        relay_Bott.setVisibility(View.GONE);

        Log.d("TAG", "getData: "+strCITYId + strSTATEId);
        url = getString(R.string.server_url) + "webservices/news.php?page=" + pageNo + "&pp=" + noOfrecords + "&state_id=1"  + "&city_id=10";
        Log.e(TAG, "getData: " + url);

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
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
                        layBookmark.setVisibility(View.GONE);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (imageList.size() == 0) {
                                    mainProgress.setVisibility(View.GONE);
                                    mainProgress2.setVisibility(View.GONE);
                                    relay_Bott.setVisibility(View.GONE);
                                } else {
                                    mainProgress.setVisibility(View.GONE);
                                    mainProgress2.setVisibility(View.GONE);
                                    relay_Bott.setVisibility(View.GONE);
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
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();

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
        viewPagerAdapter = new ViewPagerAdapter2(getActivity(), imageList, viewPager2);
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
        if (pos == 0) {
            try {
                btnPlayTheme();
                btn_play.setVisibility(View.VISIBLE);

            } catch (Exception e) {
                e.getMessage();
                Log.e(TAG, "onClick: " + e.getMessage());
            }

        } else {
            btn_play.setVisibility(View.GONE);
        }

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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
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
                            Toast.makeText(getActivity(), "No more News !", Toast.LENGTH_SHORT).show();
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

    private void refreshAd() {

        AdLoader.Builder builder = new AdLoader.Builder(getActivity(), ADMOB_AD_UNIT_ID);
        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            // OnUnifiedNativeAdLoadedListener implementation.
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                // You must call destroy on old ads when you are done with them,
                // otherwise you will have a memory leak.
                if (nativeAd != null) {
                    nativeAd.destroy();
                }
                nativeAd = unifiedNativeAd;
                UnifiedNativeAdView adView = (UnifiedNativeAdView) getLayoutInflater().inflate(R.layout.ad_unified, null);
                populateUnifiedNativeAdView(unifiedNativeAd, adView);
                frameLayout.removeAllViews();
                frameLayout.addView(adView);
            }

        });

//        VideoOptions videoOptions = new VideoOptions.Builder().setStartMuted(startVideoAdsMuted.isChecked()).build();

//        NativeAdOptions adOptions = new NativeAdOptions.Builder()
//                .setVideoOptions(videoOptions)
//                .build();

//        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                Toast.makeText(getActivity(), "Failed to load native ad: "
                        + errorCode, Toast.LENGTH_SHORT).show();
            }
        }).build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    @Override
    public void onStop() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }

    @Override
    public void onGlobalLayout() {

    }

    private void populateUnifiedNativeAdView___(UnifiedNativeAd nativeAd, UnifiedNativeAdView adView) {
        // Set the media view.
        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));

        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline and mediaContent are guaranteed to be in every UnifiedNativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        adView.setNativeAd(nativeAd);

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
        VideoController vc = nativeAd.getVideoController();

        // Updates the UI to say whether or not this ad has a video asset.
        if (vc.hasVideoContent()) {


            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
            // VideoController will call methods on this object when events occur in the video
            // lifecycle.
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    // Publishers should allow native ads to complete video playback before
                    // refreshing or replacing them with another ad in the same UI location.

                    super.onVideoEnd();
                }
            });
        } else {

        }
    }

    private void populateUnifiedNativeAdView(UnifiedNativeAd nativeAd, UnifiedNativeAdView adView) {
        // Set the media view.
        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));

        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline and mediaContent are guaranteed to be in every UnifiedNativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        adView.setNativeAd(nativeAd);

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
        VideoController vc = nativeAd.getVideoController();

        // Updates the UI to say whether or not this ad has a video asset.
        if (vc.hasVideoContent()) {
//            videoStatus.setText(String.format(Locale.getDefault(), "Video status: Ad contains a %.2f:1 video asset.", vc.getAspectRatio()));

            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
            // VideoController will call methods on this object when events occur in the video
            // lifecycle.
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    // Publishers should allow native ads to complete video playback before
                    // refreshing or replacing them with another ad in the same UI location.
                    super.onVideoEnd();
                }
            });
        } else {

        }
    }

//    private void loadAd() {
//
//        AdLoader.Builder builder = new AdLoader.Builder(getContext(), ADMOB_AD_UNIT_ID);
//        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
//            // OnUnifiedNativeAdLoadedListener implementation.
//            @Override
//            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
//                // You must call destroy on old ads when you are done with them,
//                // otherwise you will have a memory leak.
//                if (nativeAd != null) {
//                    nativeAd.destroy();
//                }
//                nativeAd = unifiedNativeAd;
//                UnifiedNativeAdView adView = (UnifiedNativeAdView) getLayoutInflater()
//                        .inflate(R.layout.ad_unified, null);
//                populateUnifiedNativeAdView(unifiedNativeAd, adView);
//                frameLayout.removeAllViews();
//                frameLayout.addView(adView);
//            }
//
//        });
//
//        VideoOptions videoOptions = new VideoOptions.Builder()
//
//                .build();
//
//        NativeAdOptions adOptions = new NativeAdOptions.Builder()
//                .setVideoOptions(videoOptions)
//                .build();
//
//        builder.withNativeAdOptions(adOptions);
//
//        AdLoader adLoader = builder.withAdListener(new AdListener() {
//            @Override
//            public void onAdFailedToLoad(int errorCode) {
//
//                Toast.makeText(getContext(), "Failed to load native ad: "
//                        + errorCode, Toast.LENGTH_SHORT).show();
//            }
//        }).build();
//
//        adLoader.loadAd(new AdRequest.Builder().build());
//    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (MainActivity.isSearch.equals("yes")) {
                getData();
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }
}

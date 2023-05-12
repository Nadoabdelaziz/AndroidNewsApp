package com.way2news.app.Adapter;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import static android.speech.tts.TextToSpeech.QUEUE_ADD;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.languageid.FirebaseLanguageIdentification;
import com.way2news.app.Activity.CommentActivity;
import com.way2news.app.Activity.Detail_Activity;
import com.way2news.app.Activity.FullImageActivity;
import com.way2news.app.Activity.MainActivity;
import com.way2news.app.Activity.ReferenceActivity;
import com.way2news.app.Activity.VideoPlayer_Activity;
import com.way2news.app.Model_Class.NewsData;
import com.way2news.app.R;
import com.way2news.app.utils.OnSwipeTouchListener;
import com.way2news.app.utils.SPmanager;
import com.way2news.app.utils.SqliteHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    public static Activity activity;
    ArrayList<NewsData.Model_News> newsArrayList;
    LayoutInflater inflater;
    private Bitmap bitmap__, wmBitmap;
    private Spanned Text;
    public static boolean isVisible = false;
    private Bitmap watermarkBitmap;
    // private ImageView imgWaterMark;
    private TextToSpeech tts;
    String[] codelist = new String[]{"ar", "zh", "cs", "da", "nl", "en", "fi", "fr", "de", "el", "he", "hi", "hu", "id", "it", "ja", "ko", "no", "pl", "pt", "ro", "ru", "sk", "es", "sv", "th", "tr", "te", "mr"};

    public ItemsAdapter(Activity activity_, ArrayList<NewsData.Model_News> imageList) {
        try {
            activity = activity_;
            this.newsArrayList = imageList;
            inflater = LayoutInflater.from(activity_);
        } catch (Exception e) {
            e.getMessage();
            Log.e(TAG, "ViewPagerAdapter: " + e.getMessage());
        }
    }

    @NonNull
    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.items, parent, false);
        return new ItemsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter.ViewHolder holder, int position) {


        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    final NewsData.Model_News menuGetset = newsArrayList.get(position);

            /*for (Model_News item : newsArrayList){
                Log.d(TAG, newsArrayList.size()+" onBindViewHolder:  mussadiq "+item.getIs_image() + ".." + item.getMediaLink());
            }*/


                    if (activity.getString(R.string.tts_visibility).equals("yes")) {
                        holder.lay_btnplay.setVisibility(View.VISIBLE);
                        if (MainActivity.themeKEY != null) {
                            if (MainActivity.themeKEY.equals("1")) {

//                    btn_pause.setVisibility(View.VISIBLE);
                                holder.btn_play.setVisibility(View.VISIBLE);
                                holder.btn_play.setColorFilter(ContextCompat.getColor(activity, R.color.darkDray), android.graphics.PorterDuff.Mode.MULTIPLY);

                            } else if (MainActivity.themeKEY.equals("0")) {
//                    btn_pause.setVisibility(View.VISIBLE);
                                holder.btn_play.setVisibility(View.VISIBLE);
                                holder.btn_play.setColorFilter(ContextCompat.getColor(activity, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
                            }
                        } else {
//                btn_pause.setVisibility(View.VISIBLE);
                            holder.btn_play.setVisibility(View.VISIBLE);
                            holder.btn_play.setColorFilter(ContextCompat.getColor(activity, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
                        }
                    } else {
                        holder.lay_btnplay.setVisibility(View.GONE);
                    }

                    supportLan(menuGetset.getDescription(),holder);


                    String imagePath = activity.getString(R.string.server_url) + "uploads/thumbnail/";

                    holder.txt_short_desc.setText(Html.fromHtml(newsArrayList.get(position).getShort_desc()));
                    holder.txt_description.setText(Html.fromHtml(newsArrayList.get(position).getDescription()));
                    String reference = menuGetset.getReference();
                    String shortName = menuGetset.getShortname();

                    Text = Html.fromHtml(menuGetset.getDescription());
                    holder.txt_description.setMovementMethod(LinkMovementMethod.getInstance());
                    holder.txt_description.setText(Text);
                    holder.txtRef.setText(newsArrayList.get(position).getShortname());
                    holder.txtNewsDate.setText(newsArrayList.get(position).getNews_date());

                    Animation animFadeIn = AnimationUtils.loadAnimation(activity, R.anim.fade_in);
                    holder.imageview.startAnimation(animFadeIn);

                    //            CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(activity);
//            circularProgressDrawable.setStrokeWidth(12f);
//            circularProgressDrawable.setCenterRadius(50);
//            if (MainActivity.themeKEY != null) {
//                if (MainActivity.themeKEY.equals("1")) {
//                    circularProgressDrawable.setColorSchemeColors(activity.getResources().getColor(R.color.darkDray));
//                } else if (MainActivity.themeKEY.equals("0")) {
//                    circularProgressDrawable.setColorSchemeColors(activity.getResources().getColor(R.color.yellow));
//                }
//            } else {
//                circularProgressDrawable.setColorSchemeColors(activity.getResources().getColor(R.color.yellow));
//            }
//            circularProgressDrawable.start();

                    Log.d(TAG, position+" onBindViewHolder:  mussadiq "+menuGetset.getIs_image() + ".." +imagePath+ ".........................." +menuGetset.getMediaLink());


                    if (menuGetset.getIs_image().equals("0")) {
                        //  local video image
                        try {
//                    Glide.with(activity).load(imagePath + menuGetset.getImage()).apply(new RequestOptions().placeholder(circularProgressDrawable)).into(holder.imageview);
                            Glide.with(activity).load(imagePath + menuGetset.getImage()).apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(holder.imageview);
                            holder.img_play.setVisibility(View.VISIBLE);
                            holder.imgFull.setVisibility(View.GONE);

                        } catch (Exception e) {
                            e.getMessage();
                            Log.e(TAG, "onBindViewHolder: " + e.getMessage());
                        }
                    }
                    if (menuGetset.getIs_image().equals("1")) {
//                local image
                        try {
                            Glide.with(activity).load(imagePath + menuGetset.getImage()).apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(holder.imageview);
                            holder.img_play.setVisibility(View.GONE);
                            holder.img_playurL.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.getMessage();
                            Log.e(TAG, "onBindViewHolder: " + e.getMessage());
                        }
                    }
                    if (menuGetset.getIs_image().equals("2") && !(menuGetset.getMediaLink().equals(""))) {
//                Youtube image
                        try {
                            Glide.with(activity).load(menuGetset.getImage()).apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(holder.imageview);
                            holder.img_playurL.setVisibility(View.VISIBLE);
                            holder.imgFull.setVisibility(View.GONE);


                    /*Intent intent = new Intent(activity, VideoPlayer_Activity.class);
                    intent.putExtra("mediaLink", menuGetset.getMediaLink());
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.bottom_up, R.anim.bottom_up);*/
                        } catch (Exception e) {
                            e.getMessage();
                            Log.e(TAG, "onBindViewHolder: " + e.getMessage());
                        }
                    }

                    holder.txt_short_desc2.setText(Html.fromHtml(newsArrayList.get(position).getShort_desc()));
                    holder.txt_description2.setMovementMethod(LinkMovementMethod.getInstance());
                    holder.txt_description2.setText(Text);
                    holder.txtRef2.setText(newsArrayList.get(position).getShortname());
                    holder.txtNewsDate2.setText(newsArrayList.get(position).getNews_date());


                    holder.imgFull.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            ViewGroup.LayoutParams rl = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                            Log.d(TAG, "onClick: "+holder.imageview.getHeight());
                            if(holder.imageview.getHeight() != 536){
                                Log.d(TAG, "onClick: hizizo");
                                holder.imageview.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 536));
                            }
                            else{
                                holder.imageview.setLayoutParams(rl);
//                                holder.imageview.setScaleType(ImageView.ScaleType.FIT_XY);

                        }
                        }
                    });
                    holder.img_play.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(activity, VideoPlayer_Activity.class);
                            intent.putExtra("localVideo", newsArrayList.get(position).getMedia());
                            activity.startActivity(intent);
                            activity.overridePendingTransition(R.anim.bottom_up, R.anim.bottom_up);

                        }
                    });
                    holder.img_playurL.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(activity, VideoPlayer_Activity.class);
                            intent.putExtra("mediaLink", newsArrayList.get(position).getMediaLink());
                            activity.startActivity(intent);
                            activity.overridePendingTransition(R.anim.bottom_up, R.anim.bottom_up);

                        }
                    });

                    holder.imgShareM.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (holder.imgShareM.getVisibility() == View.VISIBLE) {
                                holder.imgShareM2.setVisibility(View.GONE);
                                if (holder.imgShareM2.getVisibility() == View.GONE) {
                                    if (newsArrayList.get(position).getIs_image().equals("0")) {
                                        if (holder.img_play.getVisibility() == View.VISIBLE) {
                                            holder.img_play.setVisibility(View.GONE);
                                            screenShot(holder.rel_main2);
                                            holder.img_play.setVisibility(View.VISIBLE);
//                                holder.imgShareM2.setVisibility(View.VISIBLE);
                                        }
                                    } else if (newsArrayList.get(position).getIs_image().equals("1")) {
                                        screenShot(holder.rel_main2);
//                                holder.imgShareM2.setVisibility(View.VISIBLE);

                                    } else if (newsArrayList.get(position).getIs_image().equals("2")) {
                                        if (holder.img_playurL.getVisibility() == View.VISIBLE) {
                                            holder.img_playurL.setVisibility(View.GONE);
                                            screenShot(holder.rel_main2);
                                            holder.img_playurL.setVisibility(View.VISIBLE);

//                                holder.imgShareM2.setVisibility(View.VISIBLE);
                                        }
                                    }
                                }
                            }
                        }
                    });

                    if (holder.img_play.getVisibility() == View.VISIBLE || holder.img_playurL.getVisibility() == View.VISIBLE) {
                        holder.imageview.setEnabled(false);
                    } else {
                        holder.imageview.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(activity, FullImageActivity.class);
                                intent.putExtra("image", imagePath + menuGetset.getImage());
                                intent.putExtra("newsTitle", menuGetset.getShort_desc());

                                ActivityOptionsCompat options = ActivityOptionsCompat.
                                        makeSceneTransitionAnimation((Activity) activity,
                                                view,
                                                ViewCompat.getTransitionName(holder.imageview));
                                activity.startActivity(intent, options.toBundle());
                            }
                        });
                    }

                    holder.imageview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d(TAG, "onClick: toucched");
                            Toast.makeText(activity, "clicked", Toast.LENGTH_SHORT).show();
                            //holder.imageview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                        }
                    });


                    holder.imageview.setOnTouchListener(new OnSwipeTouchListener(activity) {
                        @Override
                        public void onSwipeLeft() {
                            super.onSwipeLeft();
                            Intent intent = new Intent(activity, Detail_Activity.class);
                            intent.putExtra("news_id", newsArrayList.get(position).getId1());
                            intent.putExtra("short_desc", newsArrayList.get(position).getShort_desc());
                            intent.putExtra("long_desc", newsArrayList.get(position).getDescription());
                            intent.putExtra("image", newsArrayList.get(position).getImage());
                            intent.putExtra("media", newsArrayList.get(position).getMedia());
                            intent.putExtra("medialink", newsArrayList.get(position).getMediaLink());
                            intent.putExtra("is_image", newsArrayList.get(position).getIs_image());
                            intent.putExtra("localVideo", newsArrayList.get(position).getMedia());
                            intent.putExtra("news_date", newsArrayList.get(position).getNews_date());
                            intent.putExtra("reference", reference);
                            intent.putExtra("shortname", shortName);
                            activity.startActivity(intent);
                        }

                        @Override
                        public void onSwipeRight() {
                            super.onSwipeRight();

                        }

                    });
                    holder.txt_description.setOnTouchListener(new OnSwipeTouchListener(activity) {
                        @Override
                        public void onSwipeLeft() {
                            super.onSwipeLeft();
                            Intent intent = new Intent(activity, Detail_Activity.class);
                            intent.putExtra("news_id", newsArrayList.get(position).getId1());
                            intent.putExtra("short_desc", newsArrayList.get(position).getShort_desc());
                            intent.putExtra("long_desc", newsArrayList.get(position).getDescription());
                            intent.putExtra("image", newsArrayList.get(position).getImage());
                            intent.putExtra("media", newsArrayList.get(position).getMedia());
                            intent.putExtra("medialink", newsArrayList.get(position).getMediaLink());
                            intent.putExtra("is_image", newsArrayList.get(position).getIs_image());
                            intent.putExtra("localVideo", newsArrayList.get(position).getMedia());
                            intent.putExtra("news_date", newsArrayList.get(position).getNews_date());
                            intent.putExtra("reference", reference);
                            intent.putExtra("shortname", shortName);
                            activity.startActivity(intent);
                        }

                        @Override
                        public void onSwipeRight() {
                            super.onSwipeRight();

                        }
                    });
                    holder.linRef.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(activity, ReferenceActivity.class);
                            intent.putExtra("strReference", newsArrayList.get(position).getReference());
                            activity.startActivity(intent);
                            activity.overridePendingTransition(R.anim.bottom_up, R.anim.bottom_up);
                        }
                    });

                    holder.btn_play.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            holder.btn_pause.setVisibility(View.VISIBLE);
                            holder.btn_play.setVisibility(View.INVISIBLE);
                            btnPlayTheme(holder);

                            try {
                                String speak = menuGetset.getDescription().toString();
                                tts.speak(String.valueOf(Html.fromHtml(speak)), QUEUE_ADD, null);
                                speakOut(holder);

                            } catch (Exception e) {
                                e.getMessage();
                                Log.e(TAG, "onClick: " + e.getMessage());
                            }
                        }
                    });

                    holder.btn_pause.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            holder.btn_play.setVisibility(View.VISIBLE);
                            holder.btn_pause.setVisibility(View.INVISIBLE);
                            tts.stop();

                        }
                    });

                    holder.img_Addbookmark.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            addBookmark(menuGetset,holder);

                        }
                    });


                    holder.img_Deletebookmark.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            deleteData(menuGetset,holder);

                        }
                    });

                    holder.imgComment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MainActivity.isSearch = "no";
                            Intent intent = new Intent(activity, CommentActivity.class);
                            intent.putExtra("news_id",menuGetset.getId1());
                            SPmanager.saveValue(activity, "news_id", menuGetset.getId1());
                            activity.startActivity(intent);
                        }
                    });

                } catch (
                        Exception e) {
                    e.getMessage();
                    Log.e(TAG, "onBindViewHolder: " + e.getMessage());
                }

                try {
                    if (MainActivity.themeKEY != null) {
                        if (MainActivity.themeKEY.equals("0")) {
                            holder.linItem.setBackgroundColor(activity.getResources().getColor(R.color.darkDray));
                            holder.linItem2.setBackgroundColor(activity.getResources().getColor(R.color.darkDray));
                            holder.txt_description.setTextColor(activity.getResources().getColor(R.color.white));
                            holder.txt_description2.setTextColor(activity.getResources().getColor(R.color.white));
                            holder.txt_short_desc.setTextColor(activity.getResources().getColor(R.color.yellow));
                            holder.txt_short_desc2.setTextColor(activity.getResources().getColor(R.color.yellow));
                            holder.imgShareM.setColorFilter(ContextCompat.getColor(activity, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
                            holder.imgShareM2.setColorFilter(ContextCompat.getColor(activity, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
                            holder.txtRef.setTextColor(activity.getResources().getColor(R.color.yellow));
                            holder.txtRef2.setTextColor(activity.getResources().getColor(R.color.yellow));
                            holder.txtNewsDate.setTextColor(activity.getResources().getColor(R.color.white));
                            holder.txtNewsDate2.setTextColor(activity.getResources().getColor(R.color.white));
                            holder.txtWaterMark.setTextColor(activity.getResources().getColor(R.color.yellow));

                        } else if (MainActivity.themeKEY.equals("1")) {
                            holder.linItem.setBackgroundColor(activity.getResources().getColor(R.color.white));
                            holder.linItem2.setBackgroundColor(activity.getResources().getColor(R.color.white));
                            holder.txt_description.setTextColor(activity.getResources().getColor(R.color.darkDray));
                            holder.txt_description2.setTextColor(activity.getResources().getColor(R.color.darkDray));
                            holder.txt_short_desc.setTextColor(activity.getResources().getColor(R.color.darkDray));
                            holder.txt_short_desc2.setTextColor(activity.getResources().getColor(R.color.darkDray));
                            holder.imgShareM.setColorFilter(ContextCompat.getColor(activity, R.color.darkDray), android.graphics.PorterDuff.Mode.MULTIPLY);
                            holder.imgShareM2.setColorFilter(ContextCompat.getColor(activity, R.color.darkDray), android.graphics.PorterDuff.Mode.MULTIPLY);
                            holder.txtRef.setTextColor(activity.getResources().getColor(R.color.darkDray));
                            holder.txtRef2.setTextColor(activity.getResources().getColor(R.color.darkDray));
                            holder.txtNewsDate.setTextColor(activity.getResources().getColor(R.color.darkDray));
                            holder.txtNewsDate2.setTextColor(activity.getResources().getColor(R.color.darkDray));
                            holder.txtWaterMark.setTextColor(activity.getResources().getColor(R.color.yellow));
                            holder.txtWaterMark.setTextColor(activity.getResources().getColor(R.color.darkDray));

                        }
                    } else {
                        holder.linItem.setBackgroundColor(activity.getResources().getColor(R.color.darkDray));
                        holder.linItem2.setBackgroundColor(activity.getResources().getColor(R.color.darkDray));
                        holder.txt_description.setTextColor(activity.getResources().getColor(R.color.white));
                        holder.txt_description2.setTextColor(activity.getResources().getColor(R.color.white));
                        holder.txt_short_desc.setTextColor(activity.getResources().getColor(R.color.yellow));
                        holder.txt_short_desc2.setTextColor(activity.getResources().getColor(R.color.yellow));
                        holder.imgShareM.setColorFilter(ContextCompat.getColor(activity, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
                        holder.imgShareM2.setColorFilter(ContextCompat.getColor(activity, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
                        holder.txtRef.setTextColor(activity.getResources().getColor(R.color.yellow));
                        holder.txtRef2.setTextColor(activity.getResources().getColor(R.color.yellow));
                        holder.txtNewsDate.setTextColor(activity.getResources().getColor(R.color.white));
                        holder.txtNewsDate2.setTextColor(activity.getResources().getColor(R.color.white));
                        holder.txtWaterMark.setTextColor(activity.getResources().getColor(R.color.yellow));

                    }
                } catch (Exception e) {
                    e.getMessage();
                    Log.e(TAG, "onBindViewHolder: " + e.getMessage());
                }



                tts = new TextToSpeech(activity, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {

                        if (status != TextToSpeech.ERROR) {
                            tts.setLanguage(Locale.getDefault());
                        } else {
                            Toast.makeText(activity, "Language Not Suppourted !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });




    }

    private void supportLan(String description , ItemsAdapter.ViewHolder holder) {

        final FirebaseLanguageIdentification languageIdentifier = FirebaseNaturalLanguage.getInstance().getLanguageIdentification();
        languageIdentifier.identifyLanguage(String.valueOf(Html.fromHtml(description)))
                .addOnSuccessListener(
                        new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(@Nullable String languageCode) {
                                if (languageCode != "und") {

                                    if (Arrays.asList(codelist).contains(languageCode)) {
                                        holder.btn_play.setVisibility(View.VISIBLE);
                                    } else {
                                        holder.btn_play.setVisibility(View.INVISIBLE);
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



    private void addBookmark(NewsData.Model_News menuGetset,ItemsAdapter.ViewHolder holder) {
        try {
            SqliteHelper sqliteHelper = new SqliteHelper(activity);
            SQLiteDatabase db1 = sqliteHelper.getWritableDatabase();
            Cursor cur = db1.rawQuery("SELECT * FROM bookmark Where newsID ='" + menuGetset.getId1() + "';", null);

            if (cur.moveToFirst()) {

                deleteData(menuGetset,holder);
                cur.close();
                db1.close();

            } else {

                insertData(menuGetset,holder);
                cur.close();
                db1.close();
            }

        } catch (Exception e) {

            e.getMessage();
            e.printStackTrace();
        }

    }

    private void deleteData(NewsData.Model_News menuGetset,ItemsAdapter.ViewHolder holder) {

        SqliteHelper sqliteHelper = new SqliteHelper(activity);
        SQLiteDatabase db1 = sqliteHelper.getWritableDatabase();
        db1.execSQL("DELETE FROM bookmark Where newsID ='" + menuGetset.getId1() + "';");
        db1.close();
        holder.img_Deletebookmark.setVisibility(View.GONE);
        holder.img_Addbookmark.setVisibility(View.VISIBLE);
    }

    private void btnPlayTheme(ItemsAdapter.ViewHolder holder) {
        if (MainActivity.themeKEY != null) {
            if (MainActivity.themeKEY.equals("1")) {
                holder.btn_pause.setColorFilter(ContextCompat.getColor(activity, R.color.darkDray), android.graphics.PorterDuff.Mode.MULTIPLY);

            } else if (MainActivity.themeKEY.equals("0")) {
                holder.btn_pause.setColorFilter(ContextCompat.getColor(activity, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
            }
        } else {
            holder.btn_pause.setColorFilter(ContextCompat.getColor(activity, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
        }
    }

    private void insertData(NewsData.Model_News menuGetset,ItemsAdapter.ViewHolder holder) {
        try {
            SqliteHelper sqliteHelper = new SqliteHelper(activity);
            SQLiteDatabase db1 = sqliteHelper.getWritableDatabase();
            ContentValues insertValues = new ContentValues();
            insertValues.put("newsID", menuGetset.getId1());
            insertValues.put("short_desc", menuGetset.getShort_desc());
            insertValues.put("long_desc", menuGetset.getDescription());
            insertValues.put("image", menuGetset.getImage());
            insertValues.put("medialink", menuGetset.getMediaLink());
            insertValues.put("media", menuGetset.getMedia());
            insertValues.put("is_image", menuGetset.getIs_image());
            insertValues.put("news_date", menuGetset.getNews_date());
            insertValues.put("reference", menuGetset.getReference());
            insertValues.put("short_name", menuGetset.getShortname());

            Log.e(TAG, "insertData: " + menuGetset.getShort_desc());

            db1.insert("bookmark", null, insertValues);
            db1.close();
            holder.img_Deletebookmark.setVisibility(View.VISIBLE);
            holder.img_Addbookmark.setVisibility(View.GONE);

            if (MainActivity.themeKEY != null) {
                if (MainActivity.themeKEY.equals("1")) {
                    holder.img_Deletebookmark.setColorFilter(ContextCompat.getColor(activity, R.color.darkDray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    holder.img_Addbookmark.setColorFilter(ContextCompat.getColor(activity, R.color.darkDray), android.graphics.PorterDuff.Mode.MULTIPLY);
                } else if (MainActivity.themeKEY.equals("0")) {
                    holder.img_Deletebookmark.setColorFilter(ContextCompat.getColor(activity, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
                    holder.img_Addbookmark.setColorFilter(ContextCompat.getColor(activity, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
                }
            } else {
                holder.img_Deletebookmark.setColorFilter(ContextCompat.getColor(activity, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
                holder.img_Addbookmark.setColorFilter(ContextCompat.getColor(activity, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
            }

        } catch (Exception e) {

            e.printStackTrace();
            Log.e("table Add bookmark....", "onMinusClicked: " + e.getMessage());
        }

    }

    private void speakOut(ItemsAdapter.ViewHolder holder) {

        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String s) {

                final String keyword = s;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

            @Override
            public void onDone(String s) {

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        holder.btn_pause.setVisibility(View.GONE);
                        holder.btn_play.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onError(String s) {

               activity.runOnUiThread(new Runnable() {
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

    private void screenShot(RelativeLayout view) {
//        imgWaterMark.setBackground(activity.getResources().getDrawable(R.drawable.borderrr));
        try {
            bitmap__ = Bitmap.createBitmap(view.getWidth(),
                    view.getHeight(), Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(bitmap__);
            view.draw(canvas);
        } catch (Exception e) {
            e.getMessage();
            Log.e(TAG, "screenShot: " + e.getMessage());
        }

        Uri uri = (Uri) getLocalBitmapUri(bitmap__);
        Intent sharingImage = new Intent(Intent.ACTION_SEND);
        sharingImage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        sharingImage.setType("image/png");
        sharingImage.putExtra(Intent.EXTRA_STREAM, uri);
        sharingImage.putExtra(Intent.EXTRA_TEXT, "FAST WAY OF GETTING UPDATE :" + "\n" + "https://play.google.com/store/apps/details?id=" + activity.getPackageName());
        sharingImage.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        activity.startActivity(Intent.createChooser(sharingImage, "Share news using"));
        isVisible = false;

    }

    public static Object getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file = new File(activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    "share_image_"
                            + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "getLocalBitmapUri: " + e.getMessage());
        }
        return bmpUri;
    }

    @Override
    public int getItemCount() {
        return newsArrayList.size();
    }

   /* public void addItem(ArrayList<Model_News> item, int size) {
        if (item.size() != 0) {
            newsArrayList.addAll(item);
            notifyDataSetChanged();
        }

    }

    public void removeView(int pos) {
        try {
            newsArrayList.remove(pos);
            notifyDataSetChanged();

        } catch (Exception e) {
            notifyDataSetChanged();
            e.getMessage();
            Log.e(TAG, "removeView: " + e.getMessage());
        }

    }*/

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_play, img_playurL, imgShareM, imageview, imgAppIcon, imgFull;
        ImageView imageview2, imgWaterMark2, imgShareM2;

        TextView txt_short_desc, txt_description, txtRef, txtNewsDate;
        TextView txt_short_desc2, txt_description2, txtRef2, txtNewsDate2,txtWaterMark;

        LinearLayout linItem, linRef;
        LinearLayout linItem2, linRef2;
        RelativeLayout rel_main, rel_main2;

        ImageView btn_play, btn_pause, img_Addbookmark, img_Deletebookmark, imgComment;
        RelativeLayout lay_btnplay, layBookmark, relayMainFrag, relay_Bott;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageview = itemView.findViewById(R.id.imageview);
            imgAppIcon = itemView.findViewById(R.id.imgAppIcon);
            img_play = itemView.findViewById(R.id.img_play);
            img_playurL = itemView.findViewById(R.id.img_playurL);

            imgFull = itemView.findViewById(R.id.img_full);

            imgShareM = itemView.findViewById(R.id.imgShareM);
            txt_short_desc = itemView.findViewById(R.id.txt_short_desc);
            txt_description = itemView.findViewById(R.id.txt_description);
            txtRef = itemView.findViewById(R.id.txtRef);
            txtNewsDate = itemView.findViewById(R.id.txtNewsDate);
            linItem = itemView.findViewById(R.id.linItem);
            linRef = itemView.findViewById(R.id.linRef);
            rel_main = itemView.findViewById(R.id.rel_main);
            rel_main2 = itemView.findViewById(R.id.rel_main2);

            imgShareM2 = itemView.findViewById(R.id.imgShareM2);
            imageview2 = itemView.findViewById(R.id.imageview2);
            imgWaterMark2 = itemView.findViewById(R.id.imgWaterMark2);
            txt_short_desc2 = itemView.findViewById(R.id.txt_short_desc2);
            txt_description2 = itemView.findViewById(R.id.txt_description2);
            txtWaterMark = itemView.findViewById(R.id.txtWaterMark);
            txtRef2 = itemView.findViewById(R.id.txtRef2);
            txtNewsDate2 = itemView.findViewById(R.id.txtNewsDate2);
            linItem2 = itemView.findViewById(R.id.linItem2);


            btn_play = (ImageView) itemView.findViewById(R.id.btn_playy);
            btn_pause = (ImageView) itemView.findViewById(R.id.btn_pause);
            img_Addbookmark = (ImageView) itemView.findViewById(R.id.img_Addbookmark);
            img_Deletebookmark = (ImageView) itemView.findViewById(R.id.img_Deletebookmark);
            imgComment = itemView.findViewById(R.id.imgComment);
            lay_btnplay = itemView.findViewById(R.id.lay_btnplay);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}


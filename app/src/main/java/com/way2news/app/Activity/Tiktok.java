package com.way2news.app.Activity;



import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.way2news.app.Adapter.VideoAdapter;
import com.way2news.app.Model_Class.NewsData;
import com.way2news.app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Tiktok extends AppCompatActivity {
   public List<News> newsList = new ArrayList<>();

    private ViewPager2 viewPager;
    private VideoAdapter videoAdapter;
    public static String mediaLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_tiktok);
        viewPager = findViewById(R.id.viewPager);
        List<String> videoUrls = new ArrayList<>();
        videoUrls.add("https://files.allfreehub.com/uploads/VID-20230326-WA0013.mp4");
        videoUrls.add("https://files.allfreehub.com/uploads/VID-20230326-WA0013.mp4");
        videoUrls.add("https://files.allfreehub.com/uploads/VID-20230326-WA0013.mp4");
        // Pass the newsList to your adapter and update the view



fetchNewsData();

        videoAdapter = new VideoAdapter(newsList);
        viewPager.setAdapter(videoAdapter);

        System.out.println(newsList);
//        viewPager.setPageTransformer(new DepthTransformation());



//        viewPager.setPageTransformer(new DepthTransformation());

    }

    private void fetchNewsData() {
        String url = "https://way2news.site/admin/webservices/magazine.php?page=1&pp=10";
//        RequestQueue requestQueue = Volley.newRequestQueue(Tiktok.this);
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                response -> {
//                    try {
//                        JSONObject jsonObject = new JSONObject(response);
//                        JSONArray jsonArray = jsonObject.getJSONArray("response").getJSONObject(0).getJSONArray("news");
//System.out.println(jsonArray);
//                        // Parse the JSON array into a list of news objects
//                        List<News> newsList = new ArrayList<>();
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            JSONObject newsObject = jsonArray.getJSONObject(i);
//                            News news = new News(
//                                    newsObject.getString("id"),
//                                    newsObject.getString("cat_id"),
//                                    newsObject.getString("tag"),
//                                    newsObject.getString("media"),
//                                    newsObject.getString("Medialink"),
//                                    newsObject.getString("thambnail"),
//                                    newsObject.getString("short_description"),
//                                    newsObject.getString("is_image"),
//                                    newsObject.getString("long_description"),
//                                    newsObject.getString("reference"),
//                                    newsObject.getString("shortname"),
//
//                                    newsObject.getString("reporter_id"),
//
//                                    newsObject.getString("news_date"),
//                                    newsObject.getString("date_created")
//                            );
//                            newsList.add(news);
//                        }
//                        viewPager = findViewById(R.id.viewPagertiktok);
//                        // Pass the newsList to your adapter and update the view
//                        videoAdapter = new VideoAdapter(newsList);
//                        System.out.println(newsList);
////        viewPager.setPageTransformer(new DepthTransformation());
//                        viewPager.setAdapter(videoAdapter);
//                        videoAdapter.addItem(newsList);
////                            adapter.setNewsList(newsList);
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }, error -> {
//
//                });
//        requestQueue.add(stringRequest);










        final ArrayList<NewsData> dataList = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(Tiktok.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("200")) {

                        JSONObject resp = jsonObject.getJSONObject("response");

                        JSONArray data = resp.getJSONArray("data");

                        if (data.length() == 0) {
                            Toast.makeText(Tiktok.this, "No more News !", Toast.LENGTH_SHORT).show();
                        }

                        for (int i = 0; i < data.length(); i++) {

                            JSONObject object = data.getJSONObject(i);
                            JSONArray newsdata = object.getJSONArray("news");
                            ArrayList<NewsData.Model_News> newsDataList = new ArrayList<>();

                            for (int j = 0; j < newsdata.length(); j++) {

                                NewsData.Model_News model_class = new NewsData.Model_News();
                                JSONObject newsObject = newsdata.getJSONObject(j);
                                System.out.println(newsObject);
                            News news = new News(
                                    newsObject.getString("id"),
                                    newsObject.getString("cat_id"),
                                    newsObject.getString("tag"),
                                    newsObject.getString("media"),
                                    newsObject.getString("Medialink"),
                                    newsObject.getString("thambnail"),
                                    newsObject.getString("short_description"),
                                    newsObject.getString("is_image"),
                                    newsObject.getString("long_description"),
                                    newsObject.getString("reference"),
                                    newsObject.getString("shortname"),

                                    newsObject.getString("reporter_id"),

                                    newsObject.getString("news_date"),
                                    newsObject.getString("date_created")
                            );
                            newsList.add(news);

                            }


                                dataList.add(new NewsData(newsDataList));


                                videoAdapter.addItem(newsList);
                                videoAdapter.notifyDataSetChanged();


                        }

                        if (dataList.size() != 0) {
                            Log.e("adapter", "" + dataList.size());


                        }

                    } else {


                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);















    }

    public class News {
        private String id;
        private String cat_id;
        private String tag;
        private String media;
        private String medialink;
        private String thumbnail;
        private String short_description;

        private int is_image;
        private String long_description;
        private String reference;
        private String shortname;



        // Constructor
        public News(String id, String cat_id, String tag, String media, String medialink,
                    String thumbnail, String short_description, String is_image,
                    String long_description, String reference, String shortname, String reporter_id,
                    String news_date, String date_created) {
            this.id = id;
            this.cat_id = cat_id;
            this.tag = tag;
            this.media = media;
            this.medialink = medialink;
            this.thumbnail = thumbnail;
            this.short_description = short_description;

            this.is_image = Integer.parseInt(is_image);
            this.long_description = long_description;
            this.reference = reference;
            this.shortname = shortname;


        }

        // Getters and setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCat_id() {
            return cat_id;
        }

        public void setCat_id(String cat_id) {
            this.cat_id = cat_id;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getMedia() {
            return media;
        }

        public void setMedia(String media) {
            this.media = media;
        }

        public String getMedialink() {
            return medialink;
        }

        public void setMedialink(String medialink) {
            this.medialink = medialink;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getShort_description() {
            return short_description;
        }

        public void setShort_description(String short_description) {
            this.short_description = short_description;
        }


        public int getIs_image() {
            return is_image;
        }

        public void setIs_image(int is_image) {
            this.is_image = is_image;
        }

        public String getLong_description() {
            return long_description;
        }

        public void setLong_description(String long_description) {
            this.long_description = long_description;
        }

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        public String getShortname() {
            return shortname;
        }

        public void setShortname(String shortname) {
            this.shortname = shortname;
        }


    }
}
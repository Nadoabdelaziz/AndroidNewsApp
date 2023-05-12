package com.way2news.app.Adapter;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.graphics.Bitmap;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer2;
import com.way2news.app.Model_Class.NewsData;
import com.way2news.app.R;

import java.util.ArrayList;

public class ViewPagerAdapter2 extends RecyclerView.Adapter<ViewPagerAdapter2.ViewHolder>{

    public static Activity activity;
    ArrayList<NewsData> newsArrayList;
    LayoutInflater inflater;
    private ViewPager2 viewPager2;
    private Bitmap bitmap__, wmBitmap;
    private Spanned Text;
    public static boolean isVisible = false;
    private Bitmap watermarkBitmap;
    // private ImageView imgWaterMark;

    public ViewPagerAdapter2(FragmentActivity activity_, ArrayList<NewsData> imageList, ViewPager2 viewPager2_) {
        try {
            activity = activity_;
            this.newsArrayList = imageList;
            inflater = LayoutInflater.from(activity_);
            viewPager2 = viewPager2_;
        } catch (Exception e) {
            e.getMessage();
            Log.e(TAG, "ViewPagerAdapter: " + e.getMessage());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_viewpager, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final NewsData menuGetset = newsArrayList.get(position);

        ItemsAdapter itemsAdapter = new ItemsAdapter(activity, menuGetset.news);
/*
        holder.viewPager2.setAdapter(itemsAdapter);
*/
       // CardFlipPageTransformer2 cardFlipPageTransformer = new CardFlipPageTransformer2();

        BookFlipPageTransformer2 bookFlipPageTransformer = new BookFlipPageTransformer2();
        holder.viewPager2.setPageTransformer(bookFlipPageTransformer);
        holder.viewPager2.setOffscreenPageLimit(menuGetset.news.size());

        holder.viewPager2.setAdapter(itemsAdapter);
        itemsAdapter.notifyDataSetChanged();

        holder.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);

               /* pos = position;
                Log.d(TAG, "onPageSelected mussadiq: "+position);
                //Log.d(TAG, "onPageScrolled: "+pos);
                try {
                    if (position == imageList.size() - 1 && (int) positionOffset == 0 *//*&& !isLastPageSwiped*//*) {
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
                }*/
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);


           /*     activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (menuGetset.news.get(position).getIs_image().equals("2") && !(menuGetset.news.get(position).getMediaLink().equals(""))) {
//                Youtube image
                            try {

                                Intent intent = new Intent(activity, VideoPlayer_Activity.class);
                                intent.putExtra("mediaLink", menuGetset.news.get(position).getMediaLink());
                                activity.startActivity(intent);
                                activity.overridePendingTransition(R.anim.bottom_up, R.anim.bottom_up);

                            } catch (Exception e) {
                                e.getMessage();
                                Log.e(TAG, "onBindViewHolder: " + e.getMessage());
                            }
                        }
                    }
                });*/








            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);

            /*    if (imageList.size() != 0) {
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
                tts.stop();*/
            }
        });

    }



    @Override
    public int getItemCount() {
        return newsArrayList.size();
    }

    public void addItem(ArrayList<NewsData> item, int size) {
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

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ViewPager2 viewPager2;
        ViewFlipper viewFlipper;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            viewPager2 = itemView.findViewById(R.id.viewPager2Main);
            viewFlipper = itemView.findViewById(R.id.viewFlipper);

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


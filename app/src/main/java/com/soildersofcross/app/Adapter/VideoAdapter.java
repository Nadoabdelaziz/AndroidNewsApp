package com.soildersofcross.app.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView;
import com.soildersofcross.app.Activity.Tiktok;
import com.soildersofcross.app.Activity.VideoPlayer_Activity;
import com.soildersofcross.app.R;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private List<Tiktok.News> videoUrls;
    private YouTubePlayerView youtubePlayerView;
    public VideoAdapter(List<Tiktok.News> videoUrls) {
        this.videoUrls = videoUrls;

    }
    public void addItem(List<Tiktok.News> item) {
        if (item.size() != 0) {
            videoUrls.addAll(item);
            notifyDataSetChanged();
        }

    }
    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_video_player_, parent, false);
        return new VideoViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String videoUrl = "https://way2news.site/admin/uploads/news-media/"+ String.valueOf(videoUrls.get(position).getMedia());

                //        setVideoViewDimensions(holder.videoView);
//        holder.videoView.setVideoPath(videoUrl);
//        holder.videoView.start();




System.out.println(videoUrls.get(position).getIs_image());




        if (videoUrls.get(position).getIs_image() == 1) {
            holder.imageView.setVisibility(View.VISIBLE);
            holder.videoView.setVisibility(View.GONE);


            // Load image using a library like Glide or Picasso
            Glide.with( holder.itemView)
                    .load("https://way2news.site/admin/uploads/thumbnail/"+videoUrls.get(position).getMedia())
                    .into(holder.imageView);
        } else if (videoUrls.get(position).getIs_image() == 0) {
            holder.imageView.setVisibility(View.GONE);
            holder.videoView.setVisibility(View.VISIBLE);


            DisplayMetrics displayMetrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) holder.itemView.getContext().getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            int screenWidth = displayMetrics.widthPixels;
            int screenHeight = displayMetrics.heightPixels;

            // Set the VideoView's dimensions to match the screen
            ViewGroup.LayoutParams layoutParams = holder.videoView.getLayoutParams();
            layoutParams.width = screenWidth;
            layoutParams.height = screenHeight;
            holder.videoView.setLayoutParams(layoutParams);

            // Set the video URI to the VideoView
            Uri videoUri = Uri.parse(videoUrl);
            holder.videoView.setVideoURI(videoUri);
            holder.videoView.setOnPreparedListener(mp -> {
                mp.setLooping(true);
                holder.videoView.start();
            });
        } else if (videoUrls.get(position).getIs_image()==2) {

            holder.videoView.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.VISIBLE);



            Glide.with( holder.itemView)
                    .load("https://floodframe.com/wp-content/uploads/2018/01/play_icon.png")
                    .into(holder.imageView);

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(holder.itemView.getContext(), VideoPlayer_Activity.class);
                    intent.putExtra("mediaLink", videoUrls.get(position).getMedialink());
                    holder.itemView.getContext().startActivity(intent);
                }
            });







        }

































    }

    @Override
    public int getItemCount() {
        return videoUrls.size();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {

        public VideoView videoView;
        private ImageView imageView;
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoView);
            imageView = itemView.findViewById(R.id.imageView);

        }

    }


    private void setVideoViewDimensions(VideoView videoView, String videoUrl) {
        // Get the video's dimensions using MediaMetadataRetriever
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(videoUrl);
        int videoWidth = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
        int videoHeight = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));

        // Get the device's dimensions
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) videoView.getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        // Calculate the aspect ratio of the video
        float videoAspectRatio = (float) videoWidth / videoHeight;

        // Calculate the aspect ratio of the screen
        float screenAspectRatio = (float) screenWidth / screenHeight;

        // Set the VideoView's dimensions to match the video's aspect ratio
        ViewGroup.LayoutParams layoutParams = videoView.getLayoutParams();
        if (videoAspectRatio > screenAspectRatio) {
            layoutParams.width = screenWidth;
            layoutParams.height = (int) (screenWidth / videoAspectRatio);
        } else {
            layoutParams.width = (int) (videoAspectRatio * screenHeight);
            layoutParams.height = screenHeight;
        }
        videoView.setLayoutParams(layoutParams);
    }



}

package com.way2news.app.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerListener;
import com.way2news.app.R;

import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerInitListener;

public class VideoPlayer_Activity extends AppCompatActivity {

    private static final String TAG = "Video Player Activity";
    private ImageView down;
    private String mediaLink, localVideo;
    private RelativeLayout lay_main;
    private VideoView videoView;
    private String videoAddress;
    private YouTubePlayerView youtubePlayerView;
    private ProgressBar progressV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player_);

        getSupportActionBar().hide();
        statusBarColor();
        mediaLink = getIntent().getStringExtra("mediaLink");
        localVideo = getIntent().getStringExtra("localVideo");

        Log.e(TAG, "onCreate: " + mediaLink);
        Log.e(TAG, "onCreate: " + localVideo);

        down = (ImageView) findViewById(R.id.down);
        lay_main = (RelativeLayout) findViewById(R.id.lay_main);
        videoView = findViewById(R.id.videoView);
        progressV = findViewById(R.id.progressV);

        youtubePlayerView = findViewById(R.id.youtube_player_view);
//        getLifecycle().addObserver(youtubePlayerView);

        videoAddress = getString(R.string.server_url) + "uploads/news-media/" + localVideo;
        final MediaController mediacontroller = new MediaController(this);
        mediacontroller.setAnchorView(videoView);
        final Uri vidAddress = Uri.parse(videoAddress);
        videoView.setMediaController(mediacontroller);

        try {
            if (!localVideo.equals("null")) {
                youtubePlayerView.setVisibility(View.GONE);
                videoView.setVisibility(View.VISIBLE);
                videoView.requestFocus();
                videoView.setVideoURI(vidAddress);
                videoView.start();
                progressV.setVisibility(View.GONE);

                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {

                        Toast.makeText(VideoPlayer_Activity.this, "Video Complete", Toast.LENGTH_SHORT).show();

                    }
                });

                videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        Log.d("API123", "What " + what + " extra " + extra);
                        return false;
                    }
                });

            }
        } catch (Exception e) {
            e.getMessage();
            Log.e(TAG, "onCreate: " + e.getMessage());
        }

        try {
            if (mediaLink != null) {
                progressV.setVisibility(View.GONE);
                videoView.setVisibility(View.GONE);
                youtubePlayerView.setVisibility(View.VISIBLE);
                getLifecycle().addObserver(youtubePlayerView);
                youtubePlayerView.initialize(new YouTubePlayerInitListener() {
                    @Override
                    public void onInitSuccess(@NonNull final YouTubePlayer initializedYouTubePlayer) {
                        initializedYouTubePlayer.addListener(new YouTubePlayerListener() {
                            @Override
                            public void onReady() {
                                try {
                                    String[] split = mediaLink.split("=");
                                    String vidId = split[1];
                                    initializedYouTubePlayer.loadVideo(vidId, 0);
                                } catch (Exception e) {
                                    e.getMessage();
                                    Log.e(TAG, "onReady: " + e.getMessage());
                                }
                            }

                            @Override
                            public void onStateChange(@NonNull PlayerConstants.PlayerState state) {

                            }

                            @Override
                            public void onPlaybackQualityChange(@NonNull PlayerConstants.PlaybackQuality playbackQuality) {

                            }

                            @Override
                            public void onPlaybackRateChange(@NonNull PlayerConstants.PlaybackRate playbackRate) {

                            }

                            @Override
                            public void onError(@NonNull PlayerConstants.PlayerError error) {
                                Log.d(TAG, "onError: "+error);
                            }

                            @Override
                            public void onApiChange() {
                                Log.d(TAG, "onError: ");
                            }

                            @Override
                            public void onCurrentSecond(float second) {

                            }

                            @Override
                            public void onVideoDuration(float duration) {

                            }

                            @Override
                            public void onVideoLoadedFraction(float loadedFraction) {

                            }

                            @Override
                            public void onVideoId(@NonNull String videoId) {

                            }
                        });
                       /* initializedYouTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                            @Override
                            public void onReady() {
//                        String videoId = "6JYIGclVQdw";
                                try {
                                    String[] split = mediaLink.split("=");
                                    String vidId = split[1];
                                    initializedYouTubePlayer.loadVideo(vidId, 0);
                                } catch (Exception e) {
                                    e.getMessage();
                                    Log.e(TAG, "onReady: " + e.getMessage());
                                }

                            }
                        });*/
                    }
                } , true);
            }
        } catch (Exception e) {
            e.getMessage();
            Log.e(TAG, "onCreate: " + e.getMessage());
        }


        down.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        openActivityfromBottom();
    }
}

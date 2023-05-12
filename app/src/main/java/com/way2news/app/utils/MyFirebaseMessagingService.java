package com.way2news.app.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.way2news.app.Activity.MainActivity;

import com.way2news.app.Fragment.SettingFragment;
import com.way2news.app.R;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import static com.android.volley.VolleyLog.TAG;
import static com.android.volley.VolleyLog.e;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String CHANNEL_ID = "999";
    private SharedPreferences sharedpreferences;
    private String FileName = "myPref";
    private String servicepath = "myPref";
    public static String notificationVisibility;
    String newsid, title, message, image, body;
    private Context context;

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.e("", "onNewToken: " + s);
        sendRegistrationToServer(s);
        context = MyFirebaseMessagingService.this;

    }

    private void sendRegistrationToServer(String s) {

        String str = getString(R.string.server_url) + "webservices/get_token.php?token=" + s + "&device=android";
        str = str.replace(" ", "%20");
        Log.e("url", str);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, str, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("Response", response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject resp = jsonObject.getJSONObject("data");
                    String success = resp.getString("success");
                    String token = resp.getString("token");

                    if (success.equals("1")) {

                        Log.e(TAG, "onResponse: " + success);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
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

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        notificationVisibility = SPmanager.getPreference(this, "notification_Visibility");
        Log.e(TAG, "onMessageReceived: " + notificationVisibility);
        if (notificationVisibility != null) {
            if (notificationVisibility.equals("yes")) {
//        below from firebase ...
                if (remoteMessage.getNotification() != null) {
                    Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
//            showNotificationMessage(getApplicationContext(), remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
                }
//      below from admin ...
                if (remoteMessage.getData().size() > 0) {
                    Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
                    try {
//                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                        JSONObject json = new JSONObject(remoteMessage.getData());
                        Log.e(TAG, "onMessageReceived: " + json.toString());
                        handleDataMessage(json);
                    } catch (Exception e) {
                        Log.e(TAG, "Exception: " + e.getMessage());
                    }
                }
            } else {
//            notification disable
                Log.e(TAG, "onMessageReceived: " + "-- NOTIFICATION DISABLE --");
            }
        } else {
            if (SettingFragment.nKEY.equals("yes")) {
                if (remoteMessage.getNotification() != null) {
                    Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
//            showNotificationMessage(getApplicationContext(), remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
                }
//      below from admin ...
                if (remoteMessage.getData().size() > 0) {
                    Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
                    try {
//                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                        JSONObject json = new JSONObject(remoteMessage.getData());
                        Log.e(TAG, "onMessageReceived: " + json.toString());
                        handleDataMessage(json);
                    } catch (Exception e) {
                        Log.e(TAG, "Exception: " + e.getMessage());
                    }
                }
            }
        }
    }

    private void handleDataMessage(JSONObject json) {

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json.toString());
            String newsid = json.optString("newsid");
            String title = json.optString("title");
            String message = json.optString("message");
            String image = json.optString("image");
            String body = json.optString("body");

            Log.e(TAG, "handleDataMessage: " + title + message);
            if (!message.equals("")) {
                sendNotification(title, message);
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                send_New_News_Notification(newsid, title, body, pendingIntent, image);

            }
        } catch (
                JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "handleNow: " + e.getMessage());
        }
    }

    private void send_New_News_Notification(String newsid, String title, String message, PendingIntent pendingIntent, String image) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(image);
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            System.out.println(e);
        }
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        final int min = 20;
        final int max = 80;
        final int random = new Random().nextInt((max - min) + 1) + min;

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.notification_icon)
//                        .setColor(Color.BLACK)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                        .setAutoCancel(true)
//                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            notificationBuilder.setSubText(message);
        } else {
            notificationBuilder.setContentText(message);
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(random /* ID of notification */, notificationBuilder.build());
        playSound();

    }

    private void sendNotification(String title, String message) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "my_channel";
            String Description = "This is my channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
//            mChannel.setLightColor(Color.RED);
            mChannel.setLightColor(Color.BLACK);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder1 = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(title)
                .setContentText(message)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.drawable.notification_icon))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setColor(getResources().getColor(R.color.black))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);


        notificationManager.notify(123, mBuilder1.build());

        playSound();

    }

    private Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void showNotificationMessage(Context context, String title, String message) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "my_channel";
            String Description = "This is my channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
//            mChannel.setLightColor(Color.RED);
            mChannel.setLightColor(Color.BLACK);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder1 = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(title)
                .setContentText(message)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.notification_icon))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setColor(getResources().getColor(R.color.black))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);


        notificationManager.notify(123, mBuilder1.build());
        playSound();

    }

    private void playSound() {

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();

    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
        Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
//        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSendError(String s, Exception e) {
        super.onSendError(s, e);
//        Toast.makeText(this, s + e, Toast.LENGTH_SHORT).show();
        Log.e(TAG, "onSendError: " + e.getMessage());
    }

}

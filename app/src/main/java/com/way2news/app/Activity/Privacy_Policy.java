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
                            tx_detail.setText("PRIVACY POLICY\n" +
                                    "\n" +
                                    "Last updated May 12, 2023\n" +
                                    "\n" +
                                    "This privacy notice for Soildersofcross (\"Company,” \"we,\" \"us,\" or \"our\"), describes how and why we might collect, store, use, and/or share (\"process\") your information\n" +
                                    "\n" +
                                    "when you use our services (\"Services\"), such as when you:\n" +
                                    "= Download and use our mobile application (Soildersofcross), or any other application of ours that links to this privacy notice\n" +
                                    "= Engage with us in other related ways, including any sales, marketing, or events\n" +
                                    "\n" +
                                    "Questions or concerns? Reading this privacy notice will help you understand your privacy rights and choices. If you do not agree with our policies and practices, please\n" +
                                    "\n" +
                                    "do not use our Services. If you still have any questions or concerns, please contact us at admin@soildersofcross.com." +
                                    "SUMMARY OF KEY POINTS\n" +
                                    "\n" +
                                    "This summary provides key points from our privacy notice, but you can find out more details about any of these topics by clicking the link following each key\n" +
                                    "point or by using our below to find the section you are looking for.\n" +
                                    "\n" +
                                    "‘What personal information do we process? When you visit, use, or navigate our Services, we may process personal information depending on how you interact with\n" +
                                    "Soildersofcross and the Services, the choices you make, and the products and features you use. Learn more about\n" +
                                    "\n" +
                                    "Do we process any sensitive personal information? We do not process sensitive personal information.\n" +
                                    "Do we receive any information from third parties? We do not receive any information from third parties.\n" +
                                    "\n" +
                                    "How do we process your information? We process your information to provide, improve, and administer our Services, communicate with you, for security and fraud\n" +
                                    "\n" +
                                    "prevention, and to comply with law. We may also process your information for other purposes with your consent. We process your information only when we have a valid\n" +
                                    "\n" +
                                    "legal reason to do so. Lean more about\n" +
                                    "\n" +
                                    "In what situations and with which parties do we share personal information? We may share information in specific situations and with specific third parties. Learn\n" +
                                    "more about\n" +
                                    "\n" +
                                    "What are your rights? Depending on where you are located geographically, the applicable privacy law may mean you have certain rights regarding your personal\n" +
                                    "information. Learn more about" +
                                    "" +
                                    "\n" +
                                    "How do you exercise your rights? The easiest way to exercise your rights is by submitting a data subject access request, or by contacting us. We will consider and act\n" +
                                    "\n" +
                                    "upon any request in accordance with applicable data protection laws.\n" +
                                    "\n" +
                                    "Want to learn more about what Soildersofcross does with any information we collect? Review the privacy notice in full.\n" +
                                    "\n" +
                                    "TABLE OF CONTENTS\n" +
                                    "\n" +
                                    ". WHAT INFORMATION DO WE COLLECT?\n" +
                                    ". HOW DO WE PROCESS YOUR INFORMATION?\n" +
                                    ". WHEN AND WITH WHOM DO WE SHARE YOUR PERSONAL INFORMATION?\n" +
                                    ". DO WE USE COOKIES AND OTHER TRACKING TECHNOLOGIES?\n" +
                                    ". HOW LONG DO WE KEEP YOUR INFORMATION?\n" +
                                    ". WHAT ARE YOUR PRIVACY RIGHTS?\n" +
                                    ". CONTROLS FOR DO-NOT-TRACK FEATURES\n" +
                                    ". DO CALIFORNIA RESIDENTS HAVE SPECIFIC PRIVACY RIGHTS?\n" +
                                    "9. DO WE MAKE UPDATES TO THIS NOTICE?\n" +
                                    "10. HOW CAN YOU CONTACT US ABOUT THIS NOTICE?\n" +
                                    "11. HOW CAN YOU REVIEW, UPDATE, OR DELETE THE DATA WE COLLECT FROM YOU?" +
                                    "" +
                                    "" +
                                    "1. WHAT INFORMATION DO WE COLLECT?\n" +
                                    "\n" +
                                    "Personal information you disclose to us\n" +
                                    "\n" +
                                    "In Short: We collect personal information that you provide to us.\n" +
                                    "\n" +
                                    "We collect personal information that you voluntarily provide to us when you express an interest in obtaining information about us or our products and Services, when you\n" +
                                    "\n" +
                                    "participate in activities on the Services, or otherwise when you contact us.\n" +
                                    "\n" +
                                    "Sensitive Information. We do not process sensitive information.\n" +
                                    "\n" +
                                    "Application Data. If you use our application(s), we also may collect the following information if you choose to provide us with access or permission:\n" +
                                    "\n" +
                                    "= Geolocation Information. We may request access or permission to track location-based information from your mobile device, either continuously or while you are\n" +
                                    "using our mobile application(s), to provide certain location-based services. If you wish to change our access or permissions, you may do so in your device's\n" +
                                    "\n" +
                                    "settings.\n" +
                                    "\n" +
                                    "= Push Notifications. We may request to send you push notifications regarding your account or certain features of the application(s). If you wish to opt out from\n" +
                                    "receiving these types of communications, you may turn them off in your device's settings.\n" +
                                    "\n" +
                                    "This information is primarily needed to maintain the security and operation of our application(s), for troubleshooting, and for our internal analytics and reporting purposes.\n" +
                                    "" +
                                    "All personal information that you provide to us must be true, complete, and accurate, and you must notify us of any changes to such personal information.\n" +
                                    "\n" +
                                    "Information automatically collected\n" +
                                    "\n" +
                                    "In Short: Some information — such as your Internet Protocol (IP) address and/or browser and device characteristics — is collected automatically when you visit our\n" +
                                    "\n" +
                                    "Services.\n" +
                                    "\n" +
                                    "We automatically collect certain information when you visit, use, or navigate the Services. This information does not reveal your specific identity (like your name or contact\n" +
                                    "information) but may include device and usage information, such as your IP address, browser and device characteristics, operating system, language preferences,\n" +
                                    "referring URLs, device name, country, location, information about how and when you use our Services, and other technical information. This information is primarily\n" +
                                    "\n" +
                                    "needed to maintain the security and operation of our Services, and for our internal analytics and reporting purposes.\n" +
                                    "\n" +
                                    "Like many businesses, we also collect information through cookies and similar technologies.\n" +
                                    "\n" +
                                    "The information we collect includes:\n" +
                                    "\n" +
                                    "= Location Data. We collect location data such as information about your device's location, which can be either precise or imprecise. How much information we\n" +
                                    "collect depends on the type and settings of the device you use to access the Services. For example, we may use GPS and other technologies to collect geolocation\n" +
                                    "data that tells us your current location (based on your IP address). You can opt out of allowing us to collect this information either by refusing access to the\n" +
                                    "information or by disabling your Location setting on your device. However, if you choose to opt out, you may not be able to use certain aspects of the Services.\n" +
                                    "\n" +
                                    "2. HOW DO WE PROCESS YOUR INFORMATION?" +
                                    "" +
                                    "\n" +
                                    "In Short: We process your information to provide, improve, and administer our Services, communicate with you, for security and fraud prevention, and to comply with law.\n" +
                                    "We may also process your information for other purposes with your consent.\n" +
                                    "\n" +
                                    "We process your personal information for a variety of reasons, depending on how you interact with our Services, including:\n" +
                                    "\n" +
                                    "3. WHEN AND WITH WHOM DO WE SHARE YOUR PERSONAL INFORMATION?\n" +
                                    "\n" +
                                    "In Short: We may share information in specific situations described in this section and/or with the following third parties.\n" +
                                    "\n" +
                                    "We may need to share your personal information in the following situations:\n" +
                                    "\n" +
                                    "= Business Transfers. We may share or transfer your information in connection with, or during negotiations of, any merger, sale of company assets, financing, or\n" +
                                    "acquisition of all or a portion of our business to another company.\n" +
                                    "\n" +
                                    "= When we use Google Maps Platform APIs. We may share your information with certain Google Maps Platform APIs (e.g., Google Maps API, Places API). We\n" +
                                    "obtain and store on your device (\"cache\") your location. You may revoke your consent anytime by contacting us at the contact details provided at the end of this document." +
                                    "" +
                                    "" +
                                    "\n" +
                                    "4. DO WE USE COOKIES AND OTHER TRACKING TECHNOLOGIES?\n" +
                                    "\n" +
                                    "In Short: We may use cookies and other tracking technologies to collect and store your information.\n" +
                                    "\n" +
                                    "We may use cookies and similar tracking technologies (like web beacons and pixels) to access or store information. Specific information about how we use such\n" +
                                    "\n" +
                                    "technologies and how you can refuse certain cookies is set out in our Cookie Notice.\n" +
                                    "\n" +
                                    "5. HOW LONG DO WE KEEP YOUR INFORMATION?\n" +
                                    "\n" +
                                    "In Short: We keep your information for as long as necessary to fulfill the purposes outlined in this privacy notice unless otherwise required by law.\n" +
                                    "\n" +
                                    "We will only keep your personal information for as long as it is necessary for the purposes set out in this privacy notice, unless a longer retention period is required or\n" +
                                    "\n" +
                                    "permitted by law (such as tax, accounting, or other legal requirements).\n" +
                                    "When we have no ongoing legitimate business need to process your personal information, we will either delete or anonymize such information, or, if this is not possible\n" +
                                    "\n" +
                                    "(for example, because your personal information has been stored in backup archives), then we will securely store your personal information and isolate it from any further\n" +
                                    "processing until deletion is possible.\n" +
                                    "\n" +
                                    "6. WHAT ARE YOUR PRIVACY RIGHTS?" +
                                    "" +
                                    "" +
                                    "6. WHAT ARE YOUR PRIVACY RIGHTS?\n" +
                                    "\n" +
                                    "In Short: You may review, change, or terminate your account at any time.\n" +
                                    "\n" +
                                    "If you are located in the EEA or UK and you believe we are unlawfully processing your personal information, you also have the right to complain to your Member State\n" +
                                    "data protection authority or UK data protection authority.\n" +
                                    "\n" +
                                    "If you are located in Switzerland, you may contact the Federal Data Protection and Information Commissioner.\n" +
                                    "\n" +
                                    "Withdrawing your consent: If we are relying on your consent to process your personal information, which may be express and/or implied consent depending on the\n" +
                                    "applicable law, you have the right to withdraw your consent at any time. You can withdraw your consent at any time by contacting us by using the contact details provided\n" +
                                    "in the section \"HOW CAN YOU CONTACT US ABOUT THIS NOTICE?\" below.\n" +
                                    "\n" +
                                    "However, please note that this will not affect the lawfulness of the processing before its withdrawal nor, when applicable law allows, will it affect the processing of your\n" +
                                    "personal information conducted in reliance on lawful processing grounds other than consent.\n" +
                                    "\n" +
                                    "If you have questions or comments about your privacy rights, you may email us at admin@soildersofcross.com.\n\n");

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

package com.example.projektinynierski;

import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.util.Base64;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.projektinynierski.Functionalities.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyService extends Service {
    String status;
    Ringtone ringtone;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate(){
        handler.postDelayed(runnable,5000);
    }

    private Handler handler = new Handler();

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

             RunToast();
            handler.postDelayed(this,12000);
        }
    };

    public void RunToast(){
        Toast.makeText(this, "text", Toast.LENGTH_SHORT).show();
        getStatusRing();
    }


    private void getStatusRing() {
        String urlJsonObj = "http:192.168.1.41:8080/projinz/doctor?docId=" + MainActivity.docInfo.getId();
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    status = response.getString("status");
                    String a = status;
                    if(a.equals("11")){
                        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                        ringtone = RingtoneManager.getRingtone(getApplicationContext(),uri);
                        ringtone.play();

                        Thread th = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(10000);
                                    if (ringtone.isPlaying()){

                                        setDoctorStatus0();
                                        ringtone.stop();
                                    }


                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        th.start();


                    }
                } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String credentials = MainActivity.Login + ":" + MainActivity.Password;
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Basic " + base64EncodedCredentials);
                return headers;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonRequest);
    }

    private void setDoctorStatus0() {
        String urlJsonObj = "http:192.168.1.41:8080/projinz/setStatus0?docId="+MainActivity.docInfo.getId();
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.PUT,
                urlJsonObj, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String credentials = MainActivity.Login + ":" + MainActivity.Password;
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Basic " + base64EncodedCredentials);
                return headers;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonRequest);
    }

}

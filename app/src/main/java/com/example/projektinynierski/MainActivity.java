
package com.example.projektinynierski;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import Functionalities.AppController;
import Functionalities.Md5;

/**
 * @Author Tomasz Cieśliński
 */

public class MainActivity extends AppCompatActivity {
    public EditText login;
    public EditText password;
    public TextView textView;
    private int mStatusCode;
    private String Login;
    private String Password;
    private String token = "";
    private RequestQueue mQueue;
    private int secret = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View button = findViewById(R.id.bLogin);
        mQueue = Volley.newRequestQueue(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = findViewById(R.id.etLogin);
                password = findViewById(R.id.etPass);
                Login = login.getText().toString();
                Password = password.getText().toString();
                Login();

            }
        });
    }

    private void Login() {

        String urlJsonObj = "http:192.168.1.41:8080/projinz/Login?login=" + Login + "&password=" + Password;
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String tokenResponse = response.getString("token").toString();
                    token += tokenResponse;
                    String input = Login + secret + Password;
                    Md5 md5 = new Md5();
                    String checker = md5.md5(input);

                    if (checker.equals(token)) {
                        Toast toastOK = Toast.makeText(getApplicationContext(), "Pomyślnie zalogowano", Toast.LENGTH_SHORT);
                        toastOK.show();
                        Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                        startActivity(intent);
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

                int rCode = error.networkResponse.statusCode;
                switch (rCode) {
                    case 401:
                        Toast toastUnauthorized = Toast.makeText(getApplicationContext(), "Zły login/hasło", Toast.LENGTH_SHORT);
                        toastUnauthorized.show();
                        break;
                }

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String credentials = Login + ":" + Password;
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Basic " + base64EncodedCredentials);
                return headers;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonRequest);
    }




}
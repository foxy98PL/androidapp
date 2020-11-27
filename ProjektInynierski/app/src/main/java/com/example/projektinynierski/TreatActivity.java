package com.example.projektinynierski;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.projektinynierski.Functionalities.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TreatActivity extends AppCompatActivity {
    String pesel;
    ArrayList<String> treat = new ArrayList<String>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treat);
        listView =findViewById(R.id.treatment);
        pesel = getIntent().getStringExtra("pesel");
        getPatientsList();
    }

    private void getPatientsList() {

        String urlJsonObj = "http:192.168.1.41:8080/projinz/PatientTreatment?pesel="+pesel+"&docId="+MainActivity.docInfo.getId();
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET,
                urlJsonObj, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0 ; i<response.length();i++){
                        JSONObject allergy = (JSONObject) response.get(i);
                        String icddisease = allergy.getString("icddisease");
                        String icdnbr = allergy.getString("icdnbr");
                        String icdsubtype = allergy.getString("icdsubtype");
                        String text = icddisease +icdnbr+"."+icdsubtype;

                        treat.add(text);
                    }

                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,treat);
                    listView.setAdapter(adapter);

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
                    case 404:
                        Toast toastNotFound = Toast.makeText(getApplicationContext(), "Brak danych", Toast.LENGTH_SHORT);
                        toastNotFound.show();
                        break;
                }

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
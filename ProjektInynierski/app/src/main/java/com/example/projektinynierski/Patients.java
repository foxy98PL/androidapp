package com.example.projektinynierski;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.projektinynierski.Adapters.PatientsAdapter;
import com.example.projektinynierski.Functionalities.AppController;
import com.example.projektinynierski.Models.BasicInfoPatientModel;

public class Patients extends AppCompatActivity {
    public Long id = MainActivity.docInfo.getId();
    ListView listView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_patients);

        getPatientsList();



    }

    List<BasicInfoPatientModel> info = new ArrayList<BasicInfoPatientModel>();
    private void getPatientsList() {

        String urlJsonObj = "http:192.168.1.41:8080/projinz/Patients?docId="+id;
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET,
                urlJsonObj, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                   for (int i = 0 ; i<response.length();i++){
                       JSONObject patients = (JSONObject) response.get(i);


                       String pesel = patients.getString("pesel");
                       String firstname = patients.getString("firstname");
                       String secondname = patients.getString("secondname");
                       String surrname = patients.getString("surrname");
                       String city = patients.getString("city");
                       String street = patients.getString("street");
                       String  house_nbr = patients.getString("house_nbr");
                       BasicInfoPatientModel model = new BasicInfoPatientModel(pesel,firstname,secondname,surrname,city,street,house_nbr);
                       info.add(model);
                    }

                    listView =findViewById(R.id.patientlist);
                    PatientsAdapter adapter = new PatientsAdapter(getApplicationContext(),info);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Intent i = new Intent(getApplicationContext(),PatientActivity.class);
                            BasicInfoPatientModel patient = info.get(position);
                            i.putExtra("pesel",patient.getPesel());
                            i.putExtra("firstname",patient.getFirstname());
                            i.putExtra("secondname",patient.getSecondname());
                            i.putExtra("surrname",patient.getSurrname());
                            i.putExtra("city",patient.getCity());
                            i.putExtra("street",patient.getStreet());
                            i.putExtra("house_nbr",patient.getHouse_nbr());
                            startActivity(i);

                        }
                    });
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
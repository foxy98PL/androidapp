package com.example.projektinynierski;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.projektinynierski.Adapters.CalendarAdapter;

import com.example.projektinynierski.Functionalities.AppController;

import com.example.projektinynierski.Models.PatientCallendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Callendar extends AppCompatActivity {
    public Long id = MainActivity.docInfo.getId();
    ListView listView ;
    TextView pesel;
    TextView date;
    String pesell;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callendar);



        getPatientsList();
    }
    List<PatientCallendar> info = new ArrayList<PatientCallendar>();
    private void getPatientsList() {

        String urlJsonObj = "http:192.168.1.41:8080/projinz/callendar?docId="+id;
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET,
                urlJsonObj, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0 ; i<response.length();i++){
                        JSONObject patients = (JSONObject) response.get(i);

                        Long pesel = patients.getLong("pesel");
                        Long docId = patients.getLong("docId");
                        String dateTo = patients.getString("dateTo");
                        OffsetDateTime dateto = OffsetDateTime.parse(dateTo);
                        String dateFrom = patients.getString("dateFrom");
                        OffsetDateTime datefrom = OffsetDateTime.parse(dateFrom);
                        PatientCallendar model = new PatientCallendar(pesel,docId,datefrom,dateto);
                        info.add(model);
                    }

                    listView =findViewById(R.id.callendarList);
                    CalendarAdapter adapter = new CalendarAdapter(getApplicationContext(),info);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                            final Dialog dialog= new Dialog(Callendar.this);
                            dialog.setContentView(R.layout.windowdialog_layout);
                            Button delete  = dialog.findViewById(R.id.dButton);
                            pesel = dialog.findViewById(R.id.dPesel);
                            date = dialog.findViewById(R.id.dHour);
                            PatientCallendar one = info.get(position);
                             pesell = one.getPesel().toString();
                            pesel.setText("Pesel: " + pesell );
                            date.setText("Od "+one.getDateFrom().getHour()+":"+one.getDateFrom().getMinute()+" Do " +one.getDateTo().getHour()+":"+one.getDateTo().getMinute());
                            dialog.show();
                            delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                removePatient();
                                dialog.dismiss();
                                    finish();
                                }
                            });



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

    private void removePatient() {
        String urlJsonObj = "http:192.168.1.41:8080/projinz/callendar?pesel="+pesell;
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.DELETE,
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
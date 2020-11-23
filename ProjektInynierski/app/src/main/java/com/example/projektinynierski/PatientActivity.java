package com.example.projektinynierski;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.projektinynierski.Models.BasicInfoPatientModel;

public class PatientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

     final BasicInfoPatientModel patient = new BasicInfoPatientModel(
           getIntent().getStringExtra("pesel"),
                getIntent().getStringExtra("firstname"),
                   getIntent().getStringExtra("secondname"),
                   getIntent().getStringExtra("surrname"),
                   getIntent().getStringExtra("city"),
                   getIntent().getStringExtra("street"),
                   getIntent().getStringExtra("house_nbr")
        );

        TextView pesel = findViewById(R.id.pesel);
        TextView firstname = findViewById(R.id.firstname);
        TextView secondname = findViewById(R.id.secondname);
        TextView surrname = findViewById(R.id.surrname);
        TextView city = findViewById(R.id.city);
        TextView street = findViewById(R.id.street);
        TextView house_nbr = findViewById(R.id.house);
        pesel.setText("Numer pesel pacjenta: "+patient.getPesel());
        firstname.setText("Imie pacjenta: "+patient.getFirstname());
        secondname.setText("Drugie imie pacjenta: "+patient.getSecondname());
        surrname.setText("Nazwisko pacjenta: "+patient.getSurrname());
        city.setText("Miasto pacjenta: "+patient.getCity());
        street.setText("Ulica zamieszkania: "+patient.getStreet());
        house_nbr.setText("Numer domu: "+patient.getHouse_nbr());


        Button allergyButton = findViewById(R.id.allergy);

        allergyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AlergyActivity.class);
                i.putExtra("pesel",patient.getPesel());
                startActivity(i);
            }
        });






}
}
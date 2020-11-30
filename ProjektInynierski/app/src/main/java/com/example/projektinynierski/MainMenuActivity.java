package com.example.projektinynierski;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        startService(new Intent(this,MyService.class));
        CardView cardView = findViewById(R.id.patient);
        CardView callendar = findViewById(R.id.patient2);
        cardView.setOnClickListener(this);
       callendar.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
         case R.id.patient:
             startActivity(new Intent(this,Patients.class));
        break;
            case R.id.patient2:
                startActivity(new Intent(this, Callendar.class));
                break;
        }
    }


}
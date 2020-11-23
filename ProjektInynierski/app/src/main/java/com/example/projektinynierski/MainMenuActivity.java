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
        CardView cardView = findViewById(R.id.patient);
        cardView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
         case R.id.patient:
             startActivity(new Intent(this,Patients.class));
        break;
        }
    }
}
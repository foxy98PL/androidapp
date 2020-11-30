package com.example.projektinynierski.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.projektinynierski.Models.BasicInfoPatientModel;
import com.example.projektinynierski.Models.PatientCallendar;
import com.example.projektinynierski.R;

import java.time.OffsetDateTime;
import java.util.List;


public class CalendarAdapter extends ArrayAdapter<PatientCallendar> {

    Context context;
    List<PatientCallendar> list;

    public CalendarAdapter(Context context, List<PatientCallendar> list) {
        super(context, R.layout.callendarlayout,list);
        this.context=context;
        this.list= list;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.callendarlayout,null,true);
        TextView pesel = row.findViewById(R.id.cPesel);
        TextView dateFrom = row.findViewById(R.id.cFrom);
        TextView dateTo = row.findViewById(R.id.cTo);
        PatientCallendar basic = list.get(position);

        pesel.setText("Pesel: "+basic.getPesel().toString());

        OffsetDateTime datefrom = basic.getDateFrom();
        int dateFromH = datefrom.getHour();
        int dateFromM = datefrom.getMinute();
        String datef = Integer.toString(dateFromH) + ":" + Integer.toString(dateFromM);
        dateFrom.setText("Od: "+datef);

        OffsetDateTime dateto = basic.getDateTo();
        int dateToH = dateto.getHour();
        int dateToM = dateto.getMinute();
        String datet = Integer.toString(dateToH) + ":" + Integer.toString(dateToM);
        dateTo.setText("Do: "+datet);


        return row;



    }
}

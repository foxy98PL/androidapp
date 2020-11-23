package com.example.projektinynierski.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.projektinynierski.R;

import java.util.List;

import com.example.projektinynierski.Models.BasicInfoPatientModel;

public class PatientsAdapter extends ArrayAdapter<BasicInfoPatientModel> {

    Context context;
   List<BasicInfoPatientModel> list;

    public PatientsAdapter(Context context, List<BasicInfoPatientModel> list) {
        super(context,R.layout.item_list_view,list);
        this.context=context;
        this.list= list;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.item_list_view,null,true);
        TextView pesel = row.findViewById(R.id.pesel);
        TextView name = row.findViewById(R.id.name);
        TextView surrname = row.findViewById(R.id.sname);
        BasicInfoPatientModel basic = list.get(position);

        pesel.setText(basic.getPesel());
        name.setText(basic.getFirstname());
        surrname.setText(basic.getSurrname());


        return row;



    }
}

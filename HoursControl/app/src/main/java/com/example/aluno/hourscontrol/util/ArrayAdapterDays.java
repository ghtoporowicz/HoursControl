package com.example.aluno.hourscontrol.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aluno.hourscontrol.R;
import com.example.aluno.hourscontrol.model.WorkDay;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Aluno on 17/11/2016.
 */
public class ArrayAdapterDays extends ArrayAdapter{

    Context context;
    List<WorkDay> days;
    TextView txtDateDay, txtEntry, txtBreak, txtBack, txtOut, txtBalance, txtState;
    SimpleDateFormat dhf = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat hf = new SimpleDateFormat("HH:mm:ss");

    public ArrayAdapterDays(Context context, List<WorkDay> days){
        super(context, 0, days);
        this.context = context;
        this.days = days;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        WorkDay day = days.get(position);

        if(convertView == null){
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.day_item_layout, null);
        }

        txtDateDay = (TextView) convertView.findViewById(R.id.txtDateDay);
        txtEntry = (TextView) convertView.findViewById(R.id.txtEntry);
        txtBreak = (TextView) convertView.findViewById(R.id.txtBreak);
        txtBack = (TextView) convertView.findViewById(R.id.txtBack);
        txtOut = (TextView) convertView.findViewById(R.id.txtOut);
        txtBalance = (TextView) convertView.findViewById(R.id.txtBalance);
        txtState = (TextView) convertView.findViewById(R.id.txtState);

        txtState.setText(day.getStateHours());
        txtDateDay.setText(df.format(day.getDateEntry()));
        txtBalance.setText(hf.format(day.getBalanceOfTheDay()));
        txtEntry.setText(df.format(day.getDateEntry()) + "\n" + hf.format(day.getDateEntry()));
        txtBreak.setText(df.format(day.getDateBreak()) + "\n" + hf.format(day.getDateBreak()));
        txtBack.setText(df.format(day.getDatebackBreak()) + "\n" + hf.format(day.getDatebackBreak()));
        txtOut.setText(df.format(day.getDateOut()) + "\n" + hf.format(day.getDateOut()));

        if(day != null){
            if(day.getStateHours().equals("negativo")) {
                txtBalance.setTextColor(Color.RED);
                txtState.setTextColor(Color.RED);
            }
            else{
                if(day.getStateHours().equals("positivo")){
                    txtBalance.setTextColor(Color.GREEN);
                    txtState.setTextColor(Color.GREEN);
                }
                else{
                    txtBalance.setTextColor(Color.BLUE);
                    txtState.setTextColor(Color.BLUE);
                }
            }
        }

        return convertView;

    }
}

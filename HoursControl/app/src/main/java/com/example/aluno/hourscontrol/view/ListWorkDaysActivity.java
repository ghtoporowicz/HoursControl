package com.example.aluno.hourscontrol.view;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.aluno.hourscontrol.R;
import com.example.aluno.hourscontrol.dao.WorkDayDao;
import com.example.aluno.hourscontrol.model.WorkDay;
import com.example.aluno.hourscontrol.util.ArrayAdapterDays;
import com.example.aluno.hourscontrol.util.Session;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ListWorkDaysActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ListView lstWorkDays;
    Spinner spinnerMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_work_days);

        spinnerMonth = (Spinner) findViewById(R.id.spinnerMonth);
        spinnerMonth.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.month_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnerMonth.setAdapter(adapter);

        lstWorkDays = (ListView) findViewById(R.id.lstWorkDays);
        WorkDayDao dao = new WorkDayDao();

        ArrayAdapterDays arrayAdapterDays =
                null;
        try {
            arrayAdapterDays = new ArrayAdapterDays(this, dao.returnWorkDaysByUser(this, Session.getUser().getId(), 0));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        lstWorkDays.setAdapter(arrayAdapterDays);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        int month;
        month = -1;
        switch (spinnerMonth.getSelectedItem().toString())
        {
            case "January":
            {
                month = 0;
                break;
            }
            case "February":
            {
                month = 1;
                break;
            }
            case "March":
            {
                month = 2;
                break;
            }
            case "April":
            {
                month = 3;
                break;
            }
            case "May":
            {
                month = 4;
                break;
            }
            case "June":
            {
                month = 5;
                break;
            }
            case "July":
            {
                month = 6;
                break;
            }
            case "August":
            {
                month = 7;
                break;
            }
            case "September":
            {
                month = 8;
                break;
            }
            case "October":
            {
                month = 9;
                break;
            }
            case "November":
            {
                month = 10;
                break;
            }
            case "December":
            {
                month = 11;
                break;
            }

            case "Choose a month":{
                month = -1;
                break;
            }
        }

        WorkDayDao dao = new WorkDayDao();

        ArrayAdapterDays arrayAdapterDays =
                null;
        try {
            arrayAdapterDays = new ArrayAdapterDays(this, dao.returnWorkDaysByUser(this, Session.getUser().getId(), month));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        lstWorkDays.setAdapter(arrayAdapterDays);
        Toast.makeText(ListWorkDaysActivity.this, month+1 + "", Toast.LENGTH_SHORT).show();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void btnListWorkDayClick(View view)
    {
        Intent List = new Intent(this, ListWorkDaysActivity.class);
        startActivity(List);
    }

    public void btnPointClick(View view)
    {
        Intent Point = new Intent(this, DayOfWorkActivity.class);
        startActivity(Point);
    }

}

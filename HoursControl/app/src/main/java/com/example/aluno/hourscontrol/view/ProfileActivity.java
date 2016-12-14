package com.example.aluno.hourscontrol.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.aluno.hourscontrol.R;
import com.example.aluno.hourscontrol.model.User;
import com.example.aluno.hourscontrol.util.Session;

public class ProfileActivity extends AppCompatActivity
{

    TextView txtName, txtUsername, txtDailyHour, txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtName = (TextView) findViewById(R.id.txtName);
        txtUsername = (TextView) findViewById(R.id.txtUsername);
        txtDailyHour = (TextView) findViewById(R.id.txtDailyHour);
        txtEmail = (TextView) findViewById(R.id.txtEmail);

        carrega();
    }

    private void carrega()
    {
        txtName.setText(Session.getUser().getFullname());
        txtUsername.setText(Session.getUser().getUsername());
        txtDailyHour.setText(Session.getUser().getDailyhour());
        txtEmail.setText(Session.getUser().getEmail());
    }

    public void btnProfileClick(View view)
    {
        Intent prof = new Intent(this, ProfileActivity.class);
        startActivity(prof);
    }

    public void btnPointClick(View view)
    {
        Intent point = new Intent(this, DayOfWorkActivity.class);
        startActivity(point);
    }

    public void btnListWorkDayClick(View view)
    {
        Intent list = new Intent(this, ListWorkDaysActivity.class);
        startActivity(list);
    }
}

package com.example.aluno.hourscontrol.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aluno.hourscontrol.R;
import com.example.aluno.hourscontrol.dao.UserDao;
import com.example.aluno.hourscontrol.model.User;

public class RegisterActivity extends AppCompatActivity
{
    EditText edtUsername, edtDailyhour, edtPassword, edtEmail, edtFullName;
    Button btnSave, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtFullName = (EditText) findViewById(R.id.edtFullName);
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtDailyhour = (EditText) findViewById(R.id.edtDailyhour);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnSave = (Button) findViewById(R.id.btnSave);
    }

    public void btnBackClick(View view)
    {
        Intent log = new Intent(this, MainActivity.class);
        startActivity(log);
    }

    public void btnSaveClick(View view)
    {
        User u = new User();
        u.setFullname(edtFullName.getText().toString());
        u.setUsername(edtUsername.getText().toString());
        u.setPassword(edtPassword.getText().toString());
        u.setDailyhour(edtDailyhour.getText().toString());
        u.setEmail(edtEmail.getText().toString());

        UserDao uDao = new UserDao();
        if (u.getFullname().equals("") || u.getUsername().equals("") || u.getPassword().equals("") || u.getDailyhour().equals("") || u.getEmail().equals("")) {
            Toast.makeText(this, "Preencha todos os campos corretamente.", Toast.LENGTH_SHORT).show();
        } else {
            uDao.addUser(this, u);
            finish();
        }
    }
}

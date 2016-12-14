package com.example.aluno.hourscontrol.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aluno.hourscontrol.R;
import com.example.aluno.hourscontrol.dao.LoginDao;
import com.example.aluno.hourscontrol.model.Login;
import com.example.aluno.hourscontrol.model.User;
import com.example.aluno.hourscontrol.util.Session;

public class MainActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    TextView txtSingUp;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        txtSingUp = (TextView) findViewById(R.id.txtSingUp);
        btnLogin = (Button) findViewById(R.id.btnLogin);

    }

    public void btnLoginClick(View view)
    {
        // cria um novo login
        Login log = new Login();
        // salva dados no login
        log.setUsername(edtUsername.getText().toString());
        log.setPassword(edtPassword.getText().toString());

        // instancia a Dao
        LoginDao logDao = new LoginDao();
        // faz a validação do login via Dao - retornando boleano
        User user = logDao.LoginValidate(this, log);
        // pergunta se o login está correto.
        if(user != null){
            Toast.makeText(MainActivity.this, "Login com sucesso!", Toast.LENGTH_SHORT).show();
            Session.setUser(user);
            openInitialPage(user);
        }
        else{
            Toast.makeText(MainActivity.this, "Login incorreto.", Toast.LENGTH_SHORT).show();
        }
    }

    public void txtSingUpClick(View view)
    {
        // botão signUp, cria nova intent
        Intent RegisterMain = new Intent(this, RegisterActivity.class);
        startActivity(RegisterMain);
    }

    public void openInitialPage (User user){
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("user", user.getUsername());
        startActivity(intent);
    }

    public void btnProfileClick(View view)
    {
        Intent prof = new Intent(this, ProfileActivity.class);
        startActivity(prof);
    }
}

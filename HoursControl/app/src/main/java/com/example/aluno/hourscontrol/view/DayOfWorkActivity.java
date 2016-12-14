package com.example.aluno.hourscontrol.view;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aluno.hourscontrol.R;
import com.example.aluno.hourscontrol.dao.WorkDayDao;
import com.example.aluno.hourscontrol.model.User;
import com.example.aluno.hourscontrol.model.WorkDay;
import com.example.aluno.hourscontrol.util.Session;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DayOfWorkActivity extends AppCompatActivity {

    TextView txtDateEntry, txtDateBreak, txtDateBackBreak, txtDateOut, txtDateNow;
    Button btnStart, btnFinish;
    Boolean stateDay, breakState;
    Date date;
    Calendar cal;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
    WorkDay day;
    String userLogged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_of_work);

        btnStart = (Button) findViewById(R.id.btnStart);
        btnFinish = (Button) findViewById(R.id.btnFinish);

        txtDateEntry = (TextView) findViewById(R.id.txtDateEntry);
        txtDateBreak = (TextView) findViewById(R.id.txtDateBreak);
        txtDateBackBreak = (TextView) findViewById(R.id.txtDateBackBreak);
        txtDateOut = (TextView) findViewById(R.id.txtDateOut);
        txtDateNow = (TextView) findViewById(R.id.txtDateNow);

        userLogged = getIntent().getStringExtra("user");

        stateDay = false;
        breakState = false;
        day = new WorkDay();
        // imprimi o dia da semana;
        /**SimpleDateFormat sdf_ = new SimpleDateFormat("EEEE");
        Date date = new Date();
        String dayName = sdf_.format(date);**/

    }

    public void btnStartClick(View view) {
        if(stateDay){
            if(breakState){
                backBreak();
            }
            else{
                startBreak();
            }
        }
        else {
            startDay();
        }
    }

    public void btnFinishClick(View view) {
        // pega o dia da semana.
        cal = Calendar.getInstance();
        // transforma em date
        day.setDateOut(cal.getTime());
        // transforma em String
        String currentDate = dateFormat.format(cal.getTime());
        // seta a txt
        txtDateOut.setText(currentDate);
        WorkDayDao dao = new WorkDayDao();
        Date d = dao.calcBalanceOfTheDay(day);
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        String balanceOfDay = hourFormat.format(d.getTime());
        txtDateNow.setText(balanceOfDay);
        day.setBalanceOfTheDay(d);
        try {
            testHours();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dao.addWorkDay(this, day);

        finish();
        Intent List = new Intent(this, ListWorkDaysActivity.class);
        startActivity(List);

    }

    public void startDay(){
        // pega o dia da semana.
        cal = Calendar.getInstance();
        // transforma em date
        day.setDateEntry(cal.getTime());
        // transforma em String
        String currentDate = dateFormat.format(cal.getTime());
        // seta a hora de entrada na txt dele
        txtDateEntry.setText(currentDate);
        // seta o state do dia em true
        stateDay = true;
        btnStart.setText("break");
    }
    public void startBreak(){
        // pega o dia da semana.
        cal = Calendar.getInstance();
        // transforma em date
        day.setDateBreak(cal.getTime());
        // transforma em String
        String currentDate = dateFormat.format(cal.getTime());
        // seta a txt
        txtDateBreak.setText(currentDate);
        btnStart.setText("Back break");
        breakState = true;
    }
    public void backBreak(){
        // pega o dia da semana.
        cal = Calendar.getInstance();
        // transforma em date
        day.setDatebackBreak(cal.getTime());
        // transforma em String
        String currentDate = dateFormat.format(cal.getTime());
        // seta a txt
        txtDateBackBreak.setText(currentDate);
        btnStart.setText("Start day");
        btnStart.setVisibility(View.INVISIBLE);
        btnFinish.setVisibility(View.VISIBLE);
    }

    public void testHours () throws ParseException {
        cal = Calendar.getInstance();
        Date hoursUser;
        hoursUser = cal.getTime();
        hoursUser.setHours(0);
        hoursUser.setMinutes(0);
        hoursUser.setSeconds(0);

        String statusDay = "";

        SimpleDateFormat hf = new SimpleDateFormat("HH:mm");
        Date date  = hf.parse(Session.getUser().getDailyhour());
        hf = new SimpleDateFormat("HH:mm:ss");
        hoursUser.setHours(date.getHours());
        hoursUser.setMinutes(date.getMinutes());

        if(day.getBalanceOfTheDay().getTime() > hoursUser.getTime()){
            day.setStateHours("positivo");
            statusDay = " horas positivas! " + hf.format(day.getBalanceOfTheDay());
        }
        else{
            if (day.getBalanceOfTheDay().getTime() < hoursUser.getTime()){
                day.setStateHours("negativo");
                statusDay = " horas negativas! " + hf.format(day.getBalanceOfTheDay());

                String[] emails = new String[]{
                        Session.getUser().getEmail()
                };

                final Intent openEmail = new Intent(Intent.ACTION_SENDTO);
                openEmail.setType("text/plain");
                openEmail.setData(Uri.parse("mailto:"));
                openEmail.putExtra(Intent.EXTRA_EMAIL, emails);
                openEmail.putExtra(Intent.EXTRA_SUBJECT, "Tome cuidado !");
                openEmail.putExtra(Intent.EXTRA_TEXT, "Suas horas de hoje foram negativas " + hf.format(day.getBalanceOfTheDay()));


                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //Código que vai rodar na thread secundária
                        try {
                            Thread.sleep(1000);
                            startActivity(Intent.createChooser(openEmail, "Selecione um aplicativo"));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                });
                t.start();
            }
            else{
                day.setStateHours("ok");
                statusDay = hf.format(day.getBalanceOfTheDay());
            }
        }


        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.clockicon);
        builder.setContentTitle("Dia concluido!");
        builder.setContentText(statusDay);
        Intent notificationIntent = new Intent(this, DayOfWorkActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity
                (this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        final NotificationManager notificationManager =
                (NotificationManager) getSystemService
                        (Context.NOTIFICATION_SERVICE);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //Código que vai rodar na thread secundária
                try {
                    Thread.sleep(2000);
                    notificationManager.notify(0, builder.build());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        t.start();

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

    public void btnProfileClick(View view)
    {
        Intent prof = new Intent(this, ProfileActivity.class);
        startActivity(prof);
    }
}

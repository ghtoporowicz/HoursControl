package com.example.aluno.hourscontrol.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.aluno.hourscontrol.model.Login;
import com.example.aluno.hourscontrol.model.User;
import com.example.aluno.hourscontrol.model.WorkDay;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by aline on 12/1/2016.
 */

public class Database extends SQLiteOpenHelper {
    // nameDatabase
    public static final String DATABASE_NAME = "PointworkDb.db";
    // version
    public static final int DATABASE_VERSION = 1;

    /** AVISO - SEMPRE SE LEMBRE DA ORDEM QUE OS ATRIBUTOS SÂO INSERIDOS NO BANCO **/


    /* Padronização de variaveis para criação das tabelas */
    public static final String TYPE_TEXT = " TEXT";
    public static final String TYPE_INTEGER = " INTEGER";
    public static final String COMMA = ",";

    /* TableUser */
    public static final String SQL_CREATE_USER =
            "CREATE TABLE IF NOT EXISTS " + Contract.TableUser.TABLE_NAME + " (" +
    /* Primary key*/   Contract.TableUser.COLUMN_ID + TYPE_INTEGER + " PRIMARY KEY AUTOINCREMENT" + COMMA +
                       Contract.TableUser.COLUMN_FULLNAME + TYPE_TEXT + COMMA +
    /* username*/      Contract.TableUser.COLUMN_USERNAME + TYPE_TEXT + COMMA +
    /* password*/      Contract.TableUser.COLUMN_PASSWORD + TYPE_TEXT + COMMA +
    /* DailyHours*/    Contract.TableUser.COLUMN_DAILY_HOURS + TYPE_TEXT + COMMA +
    /* email*/         Contract.TableUser.COLUMN_EMAIL + TYPE_TEXT + ");";

    // dropTable
    public static final String SQL_DELETE_TABLE_USER = "DROP TABLE IF EXISTIS " + Contract.TableUser.TABLE_NAME;
    /* End TableUser */

    /* TableWorkDay */
    public static final String SQL_CREATE_WORKDAY =
            "CREATE TABLE IF NOT EXISTS " + Contract.TableWorkDay.TABLE_NAME + " (" +
    /* Primary key*/   Contract.TableWorkDay .COLUMN_ID + TYPE_INTEGER + " PRIMARY KEY AUTOINCREMENT" + COMMA +
    /* Date entry*/    Contract.TableWorkDay.COLUMN_DATE_ENTRY + TYPE_TEXT + COMMA +
    /* Date break*/    Contract.TableWorkDay.COLUMN_DATE_BREAK + TYPE_TEXT + COMMA +
    /* Back Break*/    Contract.TableWorkDay.COLUMN_DATE_BACK_BREAK + TYPE_TEXT + COMMA +
    /* Back out*/      Contract.TableWorkDay.COLUMN_DATE_OUT + TYPE_TEXT + COMMA +
    /* balance*/       Contract.TableWorkDay.COLUMN_BALANCE_OF_THE_DAY + TYPE_TEXT + COMMA +
    /* StateHours*/    Contract.TableWorkDay.COLUMN_STATE_HOURS + TYPE_TEXT + COMMA +
    /* user*/          Contract.TableWorkDay.COLUMN_USER_ID + TYPE_INTEGER + ");";

    // dropTable
    public static final String SQL_DELETE_TABLE_WORKDAY = "DROP TABLE IF EXISTIS " + Contract.TableWorkDay.TABLE_NAME;
    /* End TableWorkDay */

    /* ****** DataBase functions  ****** */
    @Override
    /* OnCreate dataBase */
    public void onCreate(SQLiteDatabase db) {
        Log.v("Criar Banco", SQL_CREATE_USER);
        db.execSQL(SQL_CREATE_USER);
        Log.v("Criar Banco", SQL_CREATE_WORKDAY);
        db.execSQL(SQL_CREATE_WORKDAY);
    }

    @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE_USER + " " + SQL_DELETE_TABLE_WORKDAY);
        onCreate(db);
    }

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    /* ****** END database functions ****** */

    /* ****** User functions  ****** */


    // função que inseri os usuários no banco
    public long insertUser (User u){

        //Instancia do banco para escrever nele
        SQLiteDatabase database = getWritableDatabase();

        // inserindo dados da classe user no registro
        // sempre pega o nome da coluna da tabela, mais a variavel do usuario que vai ser inserido
        ContentValues register = new ContentValues();
        register.put(Contract.TableUser.COLUMN_FULLNAME, u.getFullname());
        register.put(Contract.TableUser.COLUMN_USERNAME, u.getUsername());
        register.put(Contract.TableUser.COLUMN_PASSWORD, u.getPassword());
        register.put(Contract.TableUser.COLUMN_DAILY_HOURS, u.getDailyhour());
        register.put(Contract.TableUser.COLUMN_EMAIL, u.getEmail());

        /* função da classe SQLiteOpenHelper que inseri no banco, ela retorna o valor do id
        que foi inserido no banco, e esse valor e retornado pela função insertUser() */
        return database.insert(Contract.TableUser.TABLE_NAME, null, register);

    }

    /// lista que retorna os usuarios inseridos no banco
    public List<User> returnUsers() {
        //Instancia do banco
        SQLiteDatabase database = getWritableDatabase();
        // cria uma lista que receberá os usuarios retornados.
        List<User> users = new ArrayList<User>();

        /* Colunas que retornam o que vc deseja que retorne do banco,
         sempre na ordem de criação do banco*/
        String[] columns = new String[]{
                Contract.TableUser.COLUMN_ID,
                Contract.TableUser.COLUMN_FULLNAME,
                Contract.TableUser.COLUMN_USERNAME,
                Contract.TableUser.COLUMN_PASSWORD,
                Contract.TableUser.COLUMN_DAILY_HOURS,
                Contract.TableUser.COLUMN_EMAIL };

        /* função query com todos seus elementos, os não usados estão em nulo */
        Cursor cursor = database.query(
                Contract.TableUser.TABLE_NAME,
                columns,
                null, null, null, null, null);

        // move o cursor para o primeiro elemento
        cursor.moveToFirst();

        // percorre enquanto houver um proximo do cursor
        do {
            /** a ordem dos valores dentro dos gets devem seguir a ordem que o banco foi criado
             * e que o vetor de columns foi criado tbm, sempre na orgem certa (getString(index da tabela); )
             */
            // cria um novo usuario
            User u = new User();
            // seta os valores
            u.setId(cursor.getInt(0));
            u.setFullname(cursor.getString(1));
            u.setUsername(cursor.getString(2));
            u.setPassword(cursor.getString(3));
            u.setDailyhour(cursor.getString(4));
            u.setEmail(cursor.getString(5));
            // adc na lista de users
            users.add(u);
            // while enquanto o cursor tiver um proximo
        } while (cursor.moveToNext());
        {
            // retorna a lista
            return users;
        }
    }

    // Função que retorna o usuário pelo username.
    public User returnUserByUsername(String usernamePesquisa) {
        // usernamePesuisa = String passada para fazer a verificação.

        //Instancia do banco
        SQLiteDatabase database = getWritableDatabase();
        // lista que guarda os elemento que retornam da pesquisa
        List<User> users = new ArrayList<User>();

        /* Colunas que retornam vc deseja que retorne do banco,
         sempre na ordem de criação do banco*/
        String[] columns = new String[]{
                Contract.TableUser.COLUMN_ID,
                Contract.TableUser.COLUMN_FULLNAME,
                Contract.TableUser.COLUMN_USERNAME,
                Contract.TableUser.COLUMN_PASSWORD,
                Contract.TableUser.COLUMN_DAILY_HOURS,
                Contract.TableUser.COLUMN_EMAIL};

        // Filter results = WHERE "NOME_DA_COLUNA_CIDADE" = 'Curitiba'
        // ? = selectionArgs

        // selection args = ao nome da coluna da tabela que vc quer comparar
        String selection = Contract.TableUser.COLUMN_USERNAME + " = ?";
        // vetor com os valores que vc quer comparar com aos da tabela
        String[] selectionArgs = {usernamePesquisa};

        /* função query com todos seus elementos, os não usados estão em nulo */
        Cursor cursor = database.query(
                Contract.TableUser.TABLE_NAME,
                columns,
                selection,
                selectionArgs, null, null, null);

        // move o cursor para o primeiro elemento
        cursor.moveToFirst();

        // verifica se o cursor está nulo ou não tem um proximo, ou seja se não tem nada com o username
        // cadastrado no banco.
        if(cursor!=null && cursor.getCount()!=0) {
            // percorre enquanto houver um proximo
            do {
                User u = new User();
                u.setId(cursor.getInt(0));
                u.setFullname(cursor.getString(1));
                u.setUsername(cursor.getString(2));
                u.setPassword(cursor.getString(3));
                u.setDailyhour(cursor.getString(4));
                u.setEmail(cursor.getString(5));
                // adc na lista de users
                users.add(u);
            } while (cursor.moveToNext());
        }
        // for percorrendo a lista que foi percorrida pelo cursor
        for(User u : users){
            // verifica se o nome do usuario é igual ao recebido pela função
            if(u.getUsername().equals(usernamePesquisa)){
                // se tiver retorna o mesmo
                return u;
            }
        }
        // senão retorna nulo
        return null;
    }

    // função que retorna o usuario por id.
    public User returnUserById(int usernameID) {
        // puxa a lista que a funçao de retornar usuarios passa
        List<User> users = returnUsers();
        // faz um for para percorrer a lista
        for (User u : users){
            // compara o id do usuario com o passado para a função
            if(usernameID == u.getId()){
                // se existir ele retorna o usuario
                return u;
            }
        }
        // senão retorna nulo
        return null;
    }
    /* ****** END User functions  ****** */


    
    /* ****** WorkDay functions  ****** */

    public long insertWorkDay (WorkDay day){

        //Instancia do banco para escrever nele
        SQLiteDatabase database = getWritableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
        String str;

        // inserindo dados da classe user no registro
        ContentValues register = new ContentValues();
        str = dateFormat.format(day.getDateEntry());
        register.put(Contract.TableWorkDay.COLUMN_DATE_ENTRY, str);
        str = dateFormat.format(day.getDateBreak());
        register.put(Contract.TableWorkDay.COLUMN_DATE_BREAK, str);
        str = dateFormat.format(day.getDatebackBreak());
        register.put(Contract.TableWorkDay.COLUMN_DATE_BACK_BREAK, str);
        str = dateFormat.format(day.getDateOut());
        register.put(Contract.TableWorkDay.COLUMN_DATE_OUT, str);
        str = dateFormat.format(day.getBalanceOfTheDay());
        register.put(Contract.TableWorkDay.COLUMN_BALANCE_OF_THE_DAY, str.toString());
        register.put(Contract.TableWorkDay.COLUMN_STATE_HOURS, day.getStateHours());
        register.put(Contract.TableWorkDay.COLUMN_USER_ID, day.getUserLoggin().getId());

        /* função da classe SQLiteOpenHelper que inseri no banco, ela retorna o valor do id
        que foi inserido no banco, e esse valor e retornado pela função insertUser() */
        return database.insert(Contract.TableWorkDay.TABLE_NAME, null, register);
    }

    public List<WorkDay> returnWorkDays() throws ParseException {
        //Instancia do banco
        SQLiteDatabase database = getWritableDatabase();
        List<WorkDay> workDays = new ArrayList<WorkDay>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");

        /* Colunas que retornam vc deseja que retorne do banco,
         sempre na ordem de criação do banco*/
        String[] columns = new String[]{
                Contract.TableWorkDay.COLUMN_ID,
                Contract.TableWorkDay.COLUMN_DATE_ENTRY,
                Contract.TableWorkDay.COLUMN_DATE_BREAK,
                Contract.TableWorkDay.COLUMN_DATE_BACK_BREAK,
                Contract.TableWorkDay.COLUMN_DATE_OUT,
                Contract.TableWorkDay.COLUMN_BALANCE_OF_THE_DAY,
                Contract.TableWorkDay.COLUMN_STATE_HOURS,
                Contract.TableWorkDay.COLUMN_USER_ID };

        /* função query com todos seus elementos, os não usados estão em nulo */
        Cursor cursor = database.query(
                Contract.TableWorkDay.TABLE_NAME,
                columns,
                null, null, null, null, null);

        // move o cursor para o primeiro elemento
        cursor.moveToFirst();

        Date date;
        String str;

        // percorre enquanto houver um proximo
        do {
            WorkDay day = new WorkDay();

            day.setIdWorkDay(cursor.getInt(0));
            str = cursor.getString(1);
            date = dateFormat.parse(str);
            day.setDateEntry(date);

            str = cursor.getString(2);
            date = dateFormat.parse(str);
            day.setDateBreak(date);

            str = cursor.getString(3);
            date = dateFormat.parse(str);
            day.setDatebackBreak(date);

            str = cursor.getString(4);
            date = dateFormat.parse(str);
            day.setDateOut(date);

            str = cursor.getString(5);
            date = dateFormat.parse(str);
            day.setBalanceOfTheDay(date);

            day.setStateHours(cursor.getString(6));

            int id = cursor.getInt(7);
            day.setUserLoggin(returnUserById(id));

            workDays.add(day);
        } while (cursor.moveToNext());
        return workDays;
    }

    public List<WorkDay> returnWorkDaysByUser(int idUser) throws ParseException {
        //Instancia do banco
        SQLiteDatabase database = getWritableDatabase();
        List<WorkDay> workDays = new ArrayList<WorkDay>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");

        /* Colunas que retornam vc deseja que retorne do banco,
         sempre na ordem de criação do banco*/
        String[] columns = new String[]{
                Contract.TableWorkDay.COLUMN_ID,
                Contract.TableWorkDay.COLUMN_DATE_ENTRY,
                Contract.TableWorkDay.COLUMN_DATE_BREAK,
                Contract.TableWorkDay.COLUMN_DATE_BACK_BREAK,
                Contract.TableWorkDay.COLUMN_DATE_OUT,
                Contract.TableWorkDay.COLUMN_BALANCE_OF_THE_DAY,
                Contract.TableWorkDay.COLUMN_STATE_HOURS,
                Contract.TableWorkDay.COLUMN_USER_ID };

        // Filter results WHERE "NOME_DA_COLUNA_CIDADE" = 'Curitiba'
        // ? = selectionArgs
        String selection = Contract.TableWorkDay.COLUMN_ID+ " = ?";
        String[] selectionArgs = {idUser +""};

        /* função query com todos seus elementos, os não usados estão em nulo */
        Cursor cursor = database.query(
                Contract.TableWorkDay.TABLE_NAME,
                columns,
                selection, selectionArgs, null, null, null);

        // move o cursor para o primeiro elemento
        cursor.moveToFirst();

        Date date;
        String str;

        // percorre enquanto houver um proximo
        do {
            WorkDay day = new WorkDay();

            day.setIdWorkDay(cursor.getInt(0));
            str = cursor.getString(1);
            date = dateFormat.parse(str);
            day.setDateEntry(date);

            str = cursor.getString(2);
            date = dateFormat.parse(str);
            day.setDateBreak(date);

            str = cursor.getString(3);
            date = dateFormat.parse(str);
            day.setDatebackBreak(date);

            str = cursor.getString(4);
            date = dateFormat.parse(str);
            day.setDateOut(date);

            str = cursor.getString(5);
            date = dateFormat.parse(str);
            day.setBalanceOfTheDay(date);

            day.setStateHours(cursor.getString(6));

            int id = cursor.getInt(7);
            day.setUserLoggin(returnUserById(id));

            workDays.add(day);
        } while (cursor.moveToNext());
        return workDays;
    }

    /* ****** END WorkDay functions  ****** */
}

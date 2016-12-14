package com.example.aluno.hourscontrol.dao;

import android.content.Context;
import android.widget.Toast;

import com.example.aluno.hourscontrol.database.Database;
import com.example.aluno.hourscontrol.model.User;

import java.util.ArrayList;
import java.util.List;

//Classe DAO, que conrola a conexão do banco com os elementos do usuario
public class UserDao {

    // função que adc um user no banco
    // context = activity que chamou a função
    // user o usuario que será inserido
    public long addUser (Context context, User user){
        // instancia o banco. (cria um novo objeto de banco)
        Database database = new Database(context);
        // inseri o usuario através da função do objeto database intanciado a cima
        // essa função retorna um id que é do usuario inserido no banco
        Long id = database.insertUser(user);
        // retorna o id do usuario inserido no banco
        return id;
    }

    // função que lista os usuarios inseridos no banco
    public static List<User> returnUsers(Context context){
        // instancia o banco.
        Database database = new Database(context);
        // chama a função returnUsers(), que retorna todos os usuarios inseridos no banco
        // retorna a lista de usuarios
        return database.returnUsers();
    }
}

package com.example.aluno.hourscontrol.dao;

import android.content.Context;

import com.example.aluno.hourscontrol.database.Database;
import com.example.aluno.hourscontrol.model.Login;
import com.example.aluno.hourscontrol.model.User;

import java.util.ArrayList;
import java.util.List;

//Classe DAO, que conrola a conexão do banco com os elementos do login
public class LoginDao {

    // função que valida o login, ela retorna um usuario.
    public User LoginValidate(Context context, Login log) {

        // list que recebe a lista que o banco retornará
        List<User> userDataBase = new ArrayList<User>();
        // Booleano que valida o login
        Boolean validate = false;
        // intancia do banco
        Database database = new Database(context);
        // pesquisando no banco pelo username
        User user = database.returnUserByUsername(log.getUsername().toString());
        // if para identificar se o usuario que o banco retornou é nulo, ou seja,
        // se o banco não encontrou nada.
        if (user != null) {
            // testa o username do usuario retornado pelo banco, com o username informado no login
            if (user.getUsername().equals(log.getUsername())) {
                // verifica a senha retornada pelo banco, com o password informado no login
                if (user.getPassword().equals(log.getPassword())) {
                    // se ele validar o username e a senha retorna o usuario
                    return user;
                }
            }
        }
        // se não validar o username ou o password ele retorna nulo
        return null;
    }
}


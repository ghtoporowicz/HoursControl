package com.example.aluno.hourscontrol.util;

import com.example.aluno.hourscontrol.model.User;

/**
 * Created by ghtop on 06/12/2016.
 */

public class Session {

    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Session.user = user;
    }
}

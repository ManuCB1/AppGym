package com.example.appgym.repository;

import android.content.Context;

import com.example.appgym.model.TaskCompleted;
import com.example.appgym.model.User;
import com.example.appgym.model.UserDTO;
import com.example.appgym.persistencia.LoginDAOImpl;

public class UserRepositoryImpl implements UserRepositoryBO{
    private Context context;
    private LoginDAOImpl loginDAO;

    public UserRepositoryImpl(Context context) {
        this.context = context;
        loginDAO = new LoginDAOImpl(context);
    }
    @Override
    public void get(String username, String password, TaskCompleted<User> listener) {
        loginDAO.get(username, password, listener);
    }

    @Override
    public void create(UserDTO user, TaskCompleted<Boolean> listener) {
        loginDAO.create(user, listener);
    }
}

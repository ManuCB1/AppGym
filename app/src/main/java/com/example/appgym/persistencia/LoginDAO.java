package com.example.appgym.persistencia;

import com.example.appgym.model.Rutina;
import com.example.appgym.model.TaskCompleted;
import com.example.appgym.model.User;
import com.example.appgym.model.UserDTO;

import java.util.List;

public interface LoginDAO {
    public void get(String username, String password, TaskCompleted<User> listener);
    public void create(UserDTO user, TaskCompleted<Boolean> listener);
}

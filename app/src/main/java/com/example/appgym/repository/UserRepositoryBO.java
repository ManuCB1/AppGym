package com.example.appgym.repository;

import com.example.appgym.model.TaskCompleted;
import com.example.appgym.model.User;
import com.example.appgym.model.UserDTO;

public interface UserRepositoryBO {
    public void get(String username, String password, TaskCompleted<User> listener);
    public void create(UserDTO user, TaskCompleted<Boolean> listener);

}

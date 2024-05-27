package com.example.appgym.persistencia;

import com.example.appgym.model.Rutina;
import com.example.appgym.model.TaskCompleted;

import java.util.List;

public interface BaseDAO {
    public void getAll(TaskCompleted<List<Rutina>> listener);
    public void send(Rutina rutina);
    public void getByDay(String day, TaskCompleted<List<Rutina>> listener);

}

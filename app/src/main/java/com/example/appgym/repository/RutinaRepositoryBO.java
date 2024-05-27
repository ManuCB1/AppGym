package com.example.appgym.repository;

import com.example.appgym.model.Rutina;
import com.example.appgym.model.TaskCompleted;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface RutinaRepositoryBO {
    public void getRutinas(TaskCompleted<List<Rutina>> task) throws UnsupportedEncodingException;
    public void createRutina(Rutina rutina) throws UnsupportedEncodingException;
    public Rutina getRutinaByName(String name);
    public void getRutinasByDay(String day, TaskCompleted<List<Rutina>> listener);
    public void deleteRutina(String name);
}

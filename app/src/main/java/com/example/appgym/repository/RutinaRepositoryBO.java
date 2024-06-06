package com.example.appgym.repository;

import com.example.appgym.model.Rutina;
import com.example.appgym.model.RutinaDTO;
import com.example.appgym.model.TaskCompleted;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface RutinaRepositoryBO {
    public void getAll(TaskCompleted<List<Rutina>> task) throws UnsupportedEncodingException;
    public void getByDay(String day, TaskCompleted<List<Rutina>> listener);
    public void getHistorialByDate(String fechaActual, TaskCompleted<List<Rutina>> listener);
    public void getHistorialByRutina(int id, TaskCompleted<List<Rutina>> listener);
    public void create(RutinaDTO rutina) throws UnsupportedEncodingException;
    public void createHistorial(Rutina rutina) throws UnsupportedEncodingException;
    public void putDay(String day, int id);
    public void delete(int id);
    public void deleteHistorial(String fecha);
}

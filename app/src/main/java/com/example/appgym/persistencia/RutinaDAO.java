package com.example.appgym.persistencia;

import com.example.appgym.model.Rutina;
import com.example.appgym.model.RutinaDTO;
import com.example.appgym.model.TaskCompleted;

import java.util.List;

public interface RutinaDAO {
    public void getAll(TaskCompleted<List<Rutina>> listener);
    public void getByDay(String day, TaskCompleted<List<Rutina>> listener);
    public void getHistorialByDate(String fechaActual, TaskCompleted<List<Rutina>> listener);
    public void getHistorialByRutina(int id, TaskCompleted<List<Rutina>> listener);
    public void create(RutinaDTO rutina);
    public void createHistorial(Rutina rutina);
    public void putDay(String day, int id);
    public void delete(int id);
    public void deleteHistorial(String fecha);
}

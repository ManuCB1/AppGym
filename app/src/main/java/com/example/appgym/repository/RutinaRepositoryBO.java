package com.example.appgym.repository;

import androidx.lifecycle.LiveData;

import com.example.appgym.model.Rutina;
import com.example.appgym.model.TaskCompleted;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface RutinaRepositoryBO {
    public void getRutinas(TaskCompleted<List<Rutina>> listener) throws UnsupportedEncodingException;
    public Rutina saveRutinas(Rutina rutina);
    public Rutina getRutinaByName(String name);
    public Rutina deleteRutina(String name);
}

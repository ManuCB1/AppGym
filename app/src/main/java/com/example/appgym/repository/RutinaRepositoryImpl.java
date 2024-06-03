package com.example.appgym.repository;

import android.content.Context;

import com.example.appgym.model.Rutina;
import com.example.appgym.model.RutinaDTO;
import com.example.appgym.model.TaskCompleted;
import com.example.appgym.persistencia.RutinaDAOImpl;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class RutinaRepositoryImpl implements RutinaRepositoryBO{
    private Context context;
    private RutinaDAOImpl rutinaDAO;

    public RutinaRepositoryImpl(Context context) {
        this.context = context;
        rutinaDAO = new RutinaDAOImpl(context);
    }

    @Override
    public void getAll(TaskCompleted<List<Rutina>> listener) throws UnsupportedEncodingException {
        rutinaDAO.getAll(listener);
    }

    @Override
    public void create(RutinaDTO rutina) throws UnsupportedEncodingException {
        rutinaDAO.create(rutina);
    }

    @Override
    public void createHistorial(Rutina rutina) throws UnsupportedEncodingException {
        rutinaDAO.createHistorial(rutina);
    }

    @Override
    public void putDay(String day, int id) {
        rutinaDAO.putDay(day, id);
    }

    @Override
    public void getByDay(String day, TaskCompleted<List<Rutina>> listener) {
        rutinaDAO.getByDay(day, listener);
    }

    @Override
    public void getHistorialByDate(String fechaActual, TaskCompleted<List<Rutina>> listener) {
        rutinaDAO.getHistorialByDate(fechaActual, listener);
    }

    @Override
    public void getHistorialByRutina(int id, TaskCompleted<List<Rutina>> listener) {
        rutinaDAO.getHistorialByRutina(id, listener);
    }

    @Override
    public void delete(int id) {
        rutinaDAO.delete(id);
    }

    @Override
    public void deleteHistorial(String fecha) {
        rutinaDAO.deleteHistorial(fecha);
    }
}

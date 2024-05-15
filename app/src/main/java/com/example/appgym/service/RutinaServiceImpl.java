package com.example.appgym.service;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.appgym.model.Rutina;
import com.example.appgym.model.TaskCompleted;
import com.example.appgym.persistencia.BaseBBDD;
import com.example.appgym.repository.RutinaRepositoryImpl;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class RutinaServiceImpl implements RutinaServiceIO {
    private RutinaRepositoryImpl rutinaRepository;

    public RutinaServiceImpl(Context context) {
        this.rutinaRepository = new RutinaRepositoryImpl(context);
    }

    @Override
    public void getRutinas(TaskCompleted<List<Rutina>> listener) throws UnsupportedEncodingException {
        rutinaRepository.getRutinas(listener);
    }

    @Override
    public Rutina saveRutinas(Rutina rutina) {
        return rutinaRepository.saveRutinas(rutina);
    }

    @Override
    public Rutina getRutinaByName(String name) {
        return rutinaRepository.getRutinaByName(name);
    }

    @Override
    public Rutina deleteRutina(String name) {
        return rutinaRepository.deleteRutina(name);
    }
}

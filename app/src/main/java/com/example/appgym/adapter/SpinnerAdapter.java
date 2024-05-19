package com.example.appgym.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appgym.R;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter {
    private Context ctx;
    private int miLayout;
    private List<String> listaSpinner;
    public SpinnerAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.ctx = context;
        this.miLayout = resource;
        this.listaSpinner = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        View v = LayoutInflater.from(ctx).inflate(miLayout, parent, false);
        View v = LayoutInflater.from(ctx).inflate(R.layout.spinner_item, parent, false);

        TextView textSpinner = v.findViewById(R.id.textSpinner);
        String elementoActual = listaSpinner.get(position);
        if (elementoActual != null) {
            textSpinner.setText(elementoActual);
        }

        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}

package com.example.appgym.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appgym.R;
import com.example.appgym.model.Ejercicio;

import java.util.ArrayList;
import java.util.List;

public class RecyclerChildDetailHistorialAdapter extends RecyclerView.Adapter<RecyclerChildDetailHistorialAdapter.RecyclerChildHolder> {

    private List<Integer> series;
    private List<String> repeticiones;
    private List<String> pesos;

    public RecyclerChildDetailHistorialAdapter(List<Integer> series, List<String> repeticiones, List<String> pesos) {
        this.series = series;
        this.repeticiones = repeticiones;
        this.pesos = pesos;
    }

    @NonNull
    @Override
    public RecyclerChildHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_child_detail_historial_item, parent, false);
        return new RecyclerChildHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerChildHolder holder, int position) {
        holder.textSeries.setText("Serie: "+series.get(position).toString());
        holder.textRepeticiones.setText("Reps.: "+repeticiones.get(position));
        holder.textPeso.setText("Peso: "+pesos.get(position));
    }

    @Override
    public int getItemCount() {
        return series.size();
    }


    public class RecyclerChildHolder extends RecyclerView.ViewHolder{
        private TextView textSeries, textRepeticiones, textPeso;
        public RecyclerChildHolder(@NonNull View itemView) {
            super(itemView);
            textSeries = itemView.findViewById(R.id.textSeries);
            textRepeticiones = itemView.findViewById(R.id.textRepeticiones);
            textPeso = itemView.findViewById(R.id.textPeso);
        }
    }
}

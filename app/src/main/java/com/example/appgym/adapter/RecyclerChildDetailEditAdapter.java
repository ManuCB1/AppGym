package com.example.appgym.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appgym.R;
import com.example.appgym.model.Ejercicio;
import com.example.appgym.utils.Validation;

import java.util.ArrayList;
import java.util.List;

public class RecyclerChildDetailEditAdapter extends RecyclerView.Adapter<RecyclerChildDetailEditAdapter.RecyclerChildHolder> {

    private Ejercicio ejercicioData;
    private List<Integer> series;
    private List<String> repeticiones;
    private List<String> pesos;

    public RecyclerChildDetailEditAdapter(Ejercicio ejercicioData, List<Integer> series, List<String> repeticiones) {
        this.ejercicioData = ejercicioData;
        this.series = series;
        this.repeticiones = repeticiones;
        this.pesos = new ArrayList<>();
        initPesos(series);
    }

    @NonNull
    @Override
    public RecyclerChildHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_child_detail_edit_item, parent, false);
        return new RecyclerChildHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerChildHolder holder, int position) {
        holder.textSeries.setText("Serie: "+series.get(position).toString());
        holder.textRepeticiones.setHint("Reps.: "+repeticiones.get(position));
        repeticiones.set(position, "");
        holder.textPeso.setHint("Peso: 0");

        holder.textRepeticiones.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                repeticiones.set(holder.getAdapterPosition(), editable.toString());
            }
        });

        holder.textPeso.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                pesos.set(holder.getAdapterPosition(), editable.toString());
            }
        });
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

    private void initPesos(List<Integer> series) {
        for (int serie: series){
            pesos.add("");
        }
    }

    public Integer getSeries(){
        return series.size();
    }
    public String getRepeticiones(){
        return getString(repeticiones);
    }
    public String getPesos(){
        return getString(pesos);
    }
    public Ejercicio getEjercicio(){
        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setNombre(ejercicioData.getNombre());
        ejercicio.setSeries(getSeries());
        ejercicio.setRepeticiones(getRepeticiones());
        ejercicio.setPeso(getPesos());
        return ejercicio;
    }
    @NonNull
    private String getString(List<String> lista) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < lista.size(); i++) {
            if (i > 0){
                builder.append(",");
            }
            if (lista.get(i).isEmpty()){
                return "";
            }
            builder.append(lista.get(i));
        }
        return builder.toString();
    }
}

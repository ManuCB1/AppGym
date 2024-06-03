package com.example.appgym.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appgym.R;
import com.example.appgym.model.Ejercicio;
import com.example.appgym.model.Rutina;

import java.util.List;

public class RecyclerChildDetailAdapter extends RecyclerView.Adapter<RecyclerChildDetailAdapter.RecyclerChildHolder> {

    private List<Integer> series;
    private List<String> repeticiones;

    public RecyclerChildDetailAdapter(List<Integer> series, List<String> repeticiones) {
        this.series = series;
        this.repeticiones = repeticiones;
    }

    @NonNull
    @Override
    public RecyclerChildHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_child_detail_item, parent, false);
        return new RecyclerChildHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerChildHolder holder, int position) {
        holder.titleText.setText("Serie: "+series.get(position).toString()+" , "+repeticiones.get(position)+" repeticiones");
    }

    @Override
    public int getItemCount() {
        return series.size();
    }


    public class RecyclerChildHolder extends RecyclerView.ViewHolder{
        private TextView titleText;
        public RecyclerChildHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.titleChild);
        }
    }
}

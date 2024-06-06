package com.example.appgym.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appgym.R;
import com.example.appgym.model.ClickListener;
import com.example.appgym.model.Ejercicio;
import com.example.appgym.model.EjercicioResponse;

import java.util.List;

public class RecyclerAPIAdapter extends RecyclerView.Adapter<RecyclerAPIAdapter.ViewHolder> {
    private List<EjercicioResponse> mData;
    private ClickListener listener;
    public RecyclerAPIAdapter(List<EjercicioResponse> mData, ClickListener listener) {
        this.mData = mData;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_api_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EjercicioResponse ejercicioResponse = mData.get(position);
        holder.textView.setText(ejercicioResponse.getNombre());
    }

    @Override
    public int getItemCount() {
            return mData.size();
        }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView textView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.textName);
        itemView.setOnClickListener(this);
    }

        @Override
        public void onClick(View view) {
            if (listener != null){
                listener.onClick(getAdapterPosition());
            }
        }
    }
}
package com.example.appgym.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appgym.R;
import com.example.appgym.model.Ejercicio;
import com.example.appgym.model.TypeAdapter;
import com.example.appgym.utils.Constantes;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerDetailAdapter extends RecyclerView.Adapter<RecyclerDetailAdapter.ViewHolder> {

    private List<Ejercicio> mData;
    private TypeAdapter typeAdapter;
    private ViewHolder viewHolder;
    private RecyclerView recycler;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public RecyclerDetailAdapter(List<Ejercicio> data, TypeAdapter typeAdapter) {
        this.mData = data;
        this.typeAdapter = typeAdapter;
    }
    public RecyclerDetailAdapter(List<Ejercicio> data, TypeAdapter typeAdapter, RecyclerView recycler) {
        this.mData = data;
        this.typeAdapter = typeAdapter;
        this.recycler = recycler;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_detail_item, parent, false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }




    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ejercicio ejercicio = mData.get(position);
        holder.titleText.setText(ejercicio.getNombre());

        List<Integer> series = new ArrayList<>();
        for (int i = 1; i<=ejercicio.getSeries(); i++){
            series.add(i);
        }
        List<String> repeticiones = new ArrayList<>();
        for (String repeticion : ejercicio.getRepeticiones().split(",")) {
            repeticiones.add(repeticion);
        }
        if (typeAdapter == TypeAdapter.Historial){
            List<String> pesos = new ArrayList<>();
            for (String peso : ejercicio.getPeso().split(",")) {
                pesos.add(peso);
            }
            RecyclerChildDetailHistorialAdapter childAdapter = new RecyclerChildDetailHistorialAdapter(series, repeticiones, pesos);
            holder.recyclerChild.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
            holder.recyclerChild.setHasFixedSize(true);
            holder.recyclerChild.setAdapter(childAdapter);
        }
        if (typeAdapter == TypeAdapter.Editable){
            RecyclerChildDetailEditAdapter childAdapter = new RecyclerChildDetailEditAdapter(ejercicio, series, repeticiones);
            holder.setChildAdapter(childAdapter);
            holder.recyclerChild.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
            holder.recyclerChild.setHasFixedSize(true);
            holder.recyclerChild.setAdapter(childAdapter);
        }
        if (typeAdapter == TypeAdapter.Info){
            RecyclerChildDetailAdapter childAdapter = new RecyclerChildDetailAdapter(series, repeticiones);
            holder.recyclerChild.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
            holder.recyclerChild.setHasFixedSize(true);
            holder.recyclerChild.setAdapter(childAdapter);
        }

        if (ejercicio.getImagen().isEmpty() || ejercicio.getImagen() == null){
            holder.image_info.setVisibility(View.GONE);
        }
        else {
            Log.i("imagen", ejercicio.getImagen());
            holder.image_info.setOnClickListener(view -> {
                setPosition(holder.getAdapterPosition());
                showDialogImage(view.getContext(), ejercicio.getImagen());
            });
        }

    }

    private void showDialogImage(Context context, String imagen){
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_image, null);
        ImageView image = dialogView.findViewById(R.id.image_dialog);
        String url = Constantes.URL_IMAGEN_API +imagen;
        Picasso.get().load(url).into(image);

        new AlertDialog.Builder(context)
                .setView(dialogView)
                .setPositiveButton("Aceptar", null)
                .create()
                .show();
    }


    @Override
    public void onViewRecycled(ViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

public class ViewHolder extends RecyclerView.ViewHolder {

    private LinearLayout linearLayout;
    private RecyclerView recyclerChild;
    private RecyclerChildDetailEditAdapter childAdapter;
    private TextView titleText;
    private ImageView image_info;

    ViewHolder(View itemView) {
        super(itemView);
        linearLayout = itemView.findViewById(R.id.linearLayout);
        titleText = itemView.findViewById(R.id.titleText);
        image_info = itemView.findViewById(R.id.image_info);
        recyclerChild = itemView.findViewById(R.id.recyclerChild);
    }

    public void setChildAdapter(RecyclerChildDetailEditAdapter adapter) {
        this.childAdapter = adapter;
    }

    public RecyclerChildDetailEditAdapter getChildAdapter() {
        return childAdapter;
    }

}

    public List<Ejercicio> getEjercicios() {
        List<Ejercicio> ejercicios = new ArrayList<>();
        for (int i = 0; i < getItemCount(); i++) {
            ViewHolder holder = (ViewHolder) recycler.findViewHolderForAdapterPosition(i);
            if (holder != null && holder.getChildAdapter() != null) {
                ejercicios.add(holder.getChildAdapter().getEjercicio());
            }
        }
        return ejercicios;
    }
}


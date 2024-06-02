package com.example.appgym.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appgym.R;
import com.example.appgym.model.Ejercicio;
import com.example.appgym.utils.Constantes;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerDetailAdapter extends RecyclerView.Adapter<RecyclerDetailAdapter.ViewHolder> {

    private List<Ejercicio> mData = new ArrayList<>();
//    private LayoutInflater mInflater;
//    private ItemClickListener mClickListener;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public RecyclerDetailAdapter(List<Ejercicio> data) {
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_detail_item, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ejercicio ejercicio = mData.get(position);
        holder.titleText.setText(ejercicio.getNombre());

        List<Integer> series = new ArrayList<>();
        List<String> repeticiones = new ArrayList<>();
        for (int i = 1; i<=ejercicio.getSeries(); i++){
            series.add(i);
        }
        for (String repeticion : ejercicio.getRepeticiones().split(",")) {
                repeticiones.add(repeticion);
            }

        RecyclerChildDetailAdapter childAdapter = new RecyclerChildDetailAdapter(series, repeticiones);
        holder.recyclerChild.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.recyclerChild.setHasFixedSize(true);
        holder.recyclerChild.setAdapter(childAdapter);

        if (ejercicio.getImagen().isEmpty() || ejercicio.getImagen() == null){
            holder.image_info.setVisibility(View.GONE);
        }
        else {
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
        String url = Constantes.url_imagenAPI +imagen;
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

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

    private LinearLayout linearLayout;
    private RelativeLayout expandableLayout;
    private TextView titleText;
    private ImageView image_info;
    private RecyclerView recyclerChild;

    ViewHolder(View itemView) {
        super(itemView);
        linearLayout = itemView.findViewById(R.id.linearLayout);
        expandableLayout = itemView.findViewById(R.id.expandableLayout);
        titleText = itemView.findViewById(R.id.titleText);
        image_info = itemView.findViewById(R.id.image_info);
        recyclerChild = itemView.findViewById(R.id.recyclerChild);
        itemView.setOnCreateContextMenuListener(this);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("--Selecciona una opción--");
//        Context context = v.getContext();
//        MenuInflater inflater = new MenuInflater(context);
//        inflater.inflate(R.menu.menu_contextual, menu);
    }

//    @Override
//    public void onClick(View view) {
//        if (mClickListener != null){
//            mClickListener.onItemClick(view, getAdapterPosition());
//
//        }
//    }
}

    String getItem(int id){
        return mData.get(id).toString();
    }

//    void setClickListener(ItemClickListener itemClickListener){
//        this.mClickListener = itemClickListener;
//    }
//
//    public interface ItemClickListener{
//        void onItemClick(View activista, int position);
//    }
}


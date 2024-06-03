package com.example.appgym.adapter;

import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appgym.R;
import com.example.appgym.model.DayRecycler;
import com.example.appgym.model.Rutina;
import com.example.appgym.utils.Constantes;
import com.example.appgym.utils.DateConverter;

import java.util.List;

public class RecyclerHistorialAdapter extends RecyclerView.Adapter<RecyclerHistorialAdapter.ViewHolder> {

    private List<Rutina> mData;
    private int position;
    private int menuLayout = Constantes.menuHistorial;

    public RecyclerHistorialAdapter(List<Rutina> data) {
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View parentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_historial_item, parent, false);
        return new ViewHolder(parentView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Rutina rutina = mData.get(position);
        holder.textNombreR.setText(rutina.getNombre());
//        holder.textContenidoR.setText(rutina.getContenido());
        holder.textFechaR.setText(DateConverter.getOrderDateTime(rutina.getDate()));

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getAdapterPosition());
                return false;
            }
        });

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

    TextView textNombreR;
//    TextView textContenidoR;
    TextView textFechaR;

    ViewHolder(View itemView) {
        super(itemView);
        textNombreR = itemView.findViewById(R.id.textName);
//        textContenidoR = itemView.findViewById(R.id.textContenidoR);
        textFechaR = itemView.findViewById(R.id.textDate);
        itemView.setOnCreateContextMenuListener(this);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("--Selecciona una opción--");
        Context context = v.getContext();
        MenuInflater inflater = new MenuInflater(context);
        inflater.inflate(menuLayout, menu);
    }

}

    public Rutina getItem(int id){
        return mData.get(id);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}


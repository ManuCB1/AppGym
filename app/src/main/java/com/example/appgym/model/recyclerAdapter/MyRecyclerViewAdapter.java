package com.example.appgym.model.recyclerAdapter;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appgym.R;
import com.example.appgym.model.Datos;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<Datos> mData;
    private List<String> datosDeDatos = new ArrayList<>();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public MyRecyclerViewAdapter(List<Datos> data) {
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Datos datos = mData.get(position);
        holder.titleText.setText(datos.getTitle());

        if (datos.isExpandable()){
            holder.expandableLayout.setVisibility(View.VISIBLE);
            holder.arrowImage.setImageResource(R.drawable.arrow_up);
        }else {
            holder.expandableLayout.setVisibility(View.GONE);
            holder.arrowImage.setImageResource(R.drawable.arrow_down);
        }
        RecyclerChildAdapter childAdapter = new RecyclerChildAdapter(datosDeDatos);
        holder.recyclerChild.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.recyclerChild.setHasFixedSize(true);
        holder.recyclerChild.setAdapter(childAdapter);

        holder.linearLayout.setOnClickListener(view -> {
            setPosition(holder.getAdapterPosition());
            datos.setExpandable(!datos.isExpandable());
            Log.i("pulsado", ""+datos.isExpandable());
            datosDeDatos = datos.getDatos();
            notifyItemChanged(holder.getAdapterPosition());
        });
//        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                setPosition(holder.getAdapterPosition());
//                datos.setExpandable(!datos.isExpandable());
//                Log.i("pulsado", ""+datos.isExpandable());
//                datosDeDatos = datos.getDatos();
//                notifyItemChanged(holder.getAdapterPosition());
//                return false;
//            }
//        });

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
    private ImageView arrowImage;
    private RecyclerView recyclerChild;

    ViewHolder(View itemView) {
        super(itemView);
        linearLayout = itemView.findViewById(R.id.linearLayout);
        expandableLayout = itemView.findViewById(R.id.expandableLayout);
        titleText = itemView.findViewById(R.id.titleText);
        arrowImage = itemView.findViewById(R.id.arrowImage);
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

    void setClickListener(ItemClickListener itemClickListener){
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener{
        void onItemClick(View activista, int position);
    }
}


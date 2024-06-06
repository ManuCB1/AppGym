package com.example.appgym.adapter;

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
import com.example.appgym.model.Rutina;
import com.example.appgym.model.DayRecycler;

import java.util.ArrayList;
import java.util.List;

public class RecyclerRoutineAdapter extends RecyclerView.Adapter<RecyclerRoutineAdapter.ViewHolder> {

    private List<DayRecycler> mData;
    private List<Rutina> rutinas = new ArrayList<>();
    private int position;

    public RecyclerRoutineAdapter(List<DayRecycler> data) {
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_routine_item, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DayRecycler datos = mData.get(position);
        holder.titleText.setText(datos.getTitle());

        if (datos.isExpandable()){
            holder.expandableLayout.setVisibility(View.VISIBLE);
            holder.arrowImage.setImageResource(R.drawable.arrow_up);
        }else {
            holder.expandableLayout.setVisibility(View.GONE);
            holder.arrowImage.setImageResource(R.drawable.arrow_down);
        }
        if (datos.getRutinas().size() == 0){
            holder.arrowImage.setVisibility(View.INVISIBLE);
        }else {
            holder.arrowImage.setVisibility(View.VISIBLE);
        }
        RecyclerChildAdapter childAdapter = new RecyclerChildAdapter(rutinas);
        holder.recyclerChild.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.recyclerChild.setHasFixedSize(true);
        holder.recyclerChild.setAdapter(childAdapter);

        holder.linearLayout.setOnClickListener(view -> {
            setPosition(holder.getAdapterPosition());
            datos.setExpandable(!datos.isExpandable());
            rutinas = datos.getRutinas();
            notifyItemChanged(holder.getAdapterPosition());
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

public class ViewHolder extends RecyclerView.ViewHolder {

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
    }

}
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}


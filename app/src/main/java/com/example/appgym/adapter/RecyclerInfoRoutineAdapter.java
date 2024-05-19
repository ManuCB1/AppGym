package com.example.appgym.adapter;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appgym.R;
import com.example.appgym.model.PopupListener;
import com.example.appgym.model.Rutina;

import java.util.List;

public class RecyclerInfoRoutineAdapter extends RecyclerView.Adapter<RecyclerInfoRoutineAdapter.ViewHolder> {

    private List<Rutina> mData;
    private PopupListener listener;

    public RecyclerInfoRoutineAdapter(List<Rutina> data,PopupListener listener) {
        this.mData = data;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View parentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_info_routine_item, parent, false);
        return new ViewHolder(parentView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Rutina libros = mData.get(position);
        holder.myTextView.setText(libros.getNombre());

        holder.contextInfoRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(view.getContext(), holder.contextInfoRoutine);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.menu_context_info_routine, popup.getMenu());
                popup.setOnMenuItemClickListener(item -> {
                    listener.selectedItem(holder.getAdapterPosition(), item);
                    return true;
                });
                popup.show();
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

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView myTextView;
    ImageView contextInfoRoutine;

    ViewHolder(View itemView) {
        super(itemView);
        myTextView = itemView.findViewById(R.id.titleInfoRoutine);
        contextInfoRoutine = itemView.findViewById(R.id.contextInfoRoutine);
    }

}
//    String getItem(int id){
//        return mData.get(id).toString();
//    }

}


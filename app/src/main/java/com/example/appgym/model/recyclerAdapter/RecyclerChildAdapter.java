package com.example.appgym.model.recyclerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appgym.R;

import org.w3c.dom.Text;

import java.util.List;

public class RecyclerChildAdapter extends RecyclerView.Adapter<RecyclerChildAdapter.RecyclerChildHolder> {

    private List<String> mData;

    public RecyclerChildAdapter(List<String> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public RecyclerChildHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_child_item, parent, false);
        return new RecyclerChildHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerChildHolder holder, int position) {
        holder.titleText.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class RecyclerChildHolder extends RecyclerView.ViewHolder{
        private TextView titleText;
        public RecyclerChildHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.titleChild);
        }
    }
}

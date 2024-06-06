package com.example.appgym.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appgym.R;
import com.example.appgym.persistencia.api.youtube.YoutubeVideo;

import java.util.List;

public class RecyclerYoutubeAdapter extends RecyclerView.Adapter<RecyclerYoutubeAdapter.ViewHolder> {

    private List<YoutubeVideo> videos;
    private Context context;

    public RecyclerYoutubeAdapter(List<YoutubeVideo> videos, Context context) {
        this.videos = videos;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_web_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        YoutubeVideo video = videos.get(position);
        holder.titleTextView.setText(video.getTitle());
        Glide.with(context).load(video.getImageUrl()).into(holder.imageYoutube);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(video.getLink()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageYoutube;
        TextView titleTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageYoutube = itemView.findViewById(R.id.imageYoutube);
            titleTextView = itemView.findViewById(R.id.titleYoutube);
        }
    }
}

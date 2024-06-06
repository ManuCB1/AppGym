package com.example.appgym.adapter;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appgym.R;
import com.example.appgym.model.MessageChat;

import java.util.List;

public class MessageChatAdapter extends RecyclerView.Adapter<MessageChatAdapter.ViewHolder> {

    private List<MessageChat> messages;

    public MessageChatAdapter(List<MessageChat> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_chat_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MessageChat message = messages.get(position);
        holder.setMessage(message);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout layoutLeft, layoutRight;
        TextView textLeftMessage, textRightMessage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutLeft = itemView.findViewById(R.id.layoutLeft);
            layoutRight = itemView.findViewById(R.id.layoutRight);
            textLeftMessage = itemView.findViewById(R.id.textLeftMessage);
            textRightMessage = itemView.findViewById(R.id.textRightMessage);
        }

        public void setMessage(MessageChat message) {
            if (message.isUSer()){
                layoutLeft.setVisibility(View.GONE);
                layoutRight.setVisibility(View.VISIBLE);
                textRightMessage.setText(message.getMessage());
            }
            else {
                layoutLeft.setVisibility(View.VISIBLE);
                layoutRight.setVisibility(View.GONE);
                textLeftMessage.setText(message.getMessage());
            }
        }
    }

}



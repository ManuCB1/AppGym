package com.example.appgym.control.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.appgym.R;
import com.example.appgym.adapter.MessageChatAdapter;
import com.example.appgym.model.MessageChat;
import com.example.appgym.utils.Constantes;
import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SharedFragment extends BaseFragment {

    private RecyclerView recycler;
    private MessageChatAdapter chatAdapter;
    private List<MessageChat> messages;
    private TextView textSendMessage;
    private ImageButton btnSend;
    private int title = R.string.title_shared;
    private int menu = 0;

    public SharedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shared, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setMenu(getString(title), menu);

        textSendMessage = view.findViewById(R.id.textSendMessage);
        btnSend = view.findViewById(R.id.btnSend);
        recycler = view.findViewById(R.id.recycler);
        messages = new ArrayList<>();
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        chatAdapter = new MessageChatAdapter(messages);
        recycler.setAdapter(chatAdapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = textSendMessage.getText().toString();
                if (!message.isEmpty()){
                    messages.add(new MessageChat(message, true));
                    int position = messages.size() - 1;
                    chatAdapter.notifyItemInserted(position);
                    recycler.scrollToPosition(position);
                    textSendMessage.setText("");
                    sendRequestChat(message);
                }
            }
        });

    }

    private void sendRequestChat(String message) {
        // Gemini 1.5 model
        GenerativeModel gm = new GenerativeModel("gemini-1.5-flash", Constantes.apiKeyGemini);
        GenerativeModelFutures model = GenerativeModelFutures.from(gm);

        Content content = new Content.Builder()
                .addText(message)
                .build();

        Executor executor = Executors.newSingleThreadExecutor();
        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);
        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                String resultText = result.getText();
                Log.i("resultado", resultText);
                addMessageByChat(resultText, true);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                Log.i("error", t.getMessage().toString());
                addMessageByChat(t.getMessage().toString(), false);
            }
        }, executor);
    }
//    TODO: Cambiar Imagen Enviar Chat
//    TODO: Ajustes cuenta Gemini
    private void addMessageByChat(String message, boolean isUSer) {
        getActivity().runOnUiThread(() -> {
            messages.add(new MessageChat(message, isUSer));
            int position = messages.size() - 1;
            chatAdapter.notifyDataSetChanged();
            recycler.scrollToPosition(messages.size()-1);
        });
    }
}
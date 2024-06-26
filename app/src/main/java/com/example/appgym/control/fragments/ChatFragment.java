package com.example.appgym.control.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appgym.R;
import com.example.appgym.adapter.MessageChatAdapter;
import com.example.appgym.model.MessageChat;
import com.example.appgym.utils.Constantes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatFragment extends BaseFragment {

    private RecyclerView recycler;
    private MessageChatAdapter chatAdapter;
    private List<MessageChat> messages;
    private OkHttpClient httpClient = new OkHttpClient();
    private TextView textSendMessage;
    private ImageView btnSend;
    private int title = R.string.title_chat;
    private int menu = 0;

    public ChatFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
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
                sendMessage();
            }
        });

    }

    private void sendMessage() {
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

    private void sendRequestChat(String message) {
        OkHttpClient client = new OkHttpClient();

        JSONObject json = new JSONObject();
        try {
            json.put("inputs", message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(Constantes.URL_API_HUHHING_UNO)
                .header("Authorization", "Bearer " + Constantes.API_KEY_HUGGING)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addMessageByChat(e.getMessage(), false);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    try {
                        JSONArray jsonArray = new JSONArray(responseBody);
                        String generatedText = jsonArray.getJSONObject(0).getString("generated_text");
                        addMessageByChat(generatedText, false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    addMessageByChat(response.body().toString(), false);
                }
            }
        });
    }

    private void addMessageByChat(String message, boolean isUSer) {
        getActivity().runOnUiThread(() -> {
            messages.add(new MessageChat(message, isUSer));
            int position = messages.size() - 1;
            chatAdapter.notifyDataSetChanged();
            recycler.scrollToPosition(messages.size()-1);
        });
    }
}
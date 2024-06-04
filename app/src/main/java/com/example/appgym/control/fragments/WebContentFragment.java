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

import com.example.appgym.R;
import com.example.appgym.adapter.RecyclerYoutubeAdapter;
import com.example.appgym.persistencia.api.youtube.YouTubeApi;
import com.example.appgym.persistencia.api.youtube.YouTubeItem;
import com.example.appgym.persistencia.api.youtube.YouTubeResponse;
import com.example.appgym.persistencia.api.youtube.YoutubeVideo;
import com.example.appgym.utils.Constantes;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebContentFragment extends BaseFragment {
    private RecyclerView recycler;
    private int title = R.string.title_web_content;
    private int menu = 0;
    public WebContentFragment() {
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
        return inflater.inflate(R.layout.fragment_web_content, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recycler = view.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));

        setMenu(getString(title), menu);
        setData();
    }

    private void setData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        YouTubeApi youTubeApi = retrofit.create(YouTubeApi.class);
        Call<YouTubeResponse> call = youTubeApi.getVideos("snippet", "gym workout", "video", Constantes.apiKeyYoutube);
        call.enqueue(new Callback<YouTubeResponse>() {
            @Override
            public void onResponse(Call<YouTubeResponse> call, Response<YouTubeResponse> response) {
                if (response.isSuccessful()) {
                    List<YoutubeVideo> videos = new ArrayList<>();
                    for (YouTubeItem item : response.body().getItems()) {
                        String title = item.getSnippet().getTitle();
                        String videoId = item.getId().getVideoId();
                        String link = "https://www.youtube.com/watch?v=" + videoId;
                        String thumbnailUrl = item.getSnippet().getThumbnailUrl();
                        videos.add(new YoutubeVideo(title, link, thumbnailUrl));
                    }

                    RecyclerYoutubeAdapter adapter = new RecyclerYoutubeAdapter(videos, requireContext());
                    recycler.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<YouTubeResponse> call, Throwable t) {

            }
        });

    }
}
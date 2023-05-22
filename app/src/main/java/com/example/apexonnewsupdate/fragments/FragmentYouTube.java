package com.example.apexonnewsupdate.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.apexonnewsupdate.R;
import com.example.apexonnewsupdate.adapters.YoutubeAdapter;
import com.example.apexonnewsupdate.models.HomepageModel;
import com.example.apexonnewsupdate.models.OurYModel;
import com.example.apexonnewsupdate.models.YtModel;
import com.example.apexonnewsupdate.rest.ApiClient;
import com.example.apexonnewsupdate.rest.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentYouTube extends Fragment {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progressBar;
    Context context;
    String cid, pageToken="";
    YoutubeAdapter youtubeAdapter;
    List<YtModel.Item> items = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_you_tube,container,false);

      recyclerView = view.findViewById(R.id.video_recy);
      swipeRefreshLayout = view.findViewById(R.id.swipe);
      progressBar = view.findViewById(R.id.progressBarYouTube);

      youtubeAdapter = new YoutubeAdapter(context,items);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);

        cid = getArguments().getString("cid");

        LoaddataFromYouTubeServer();

      return view;
       }

    private void LoaddataFromYouTubeServer() {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Map<String, String> params = new HashMap<>();
        params.put("part", "snippet");
        params.put("channelId",cid);
        params.put("maxResults","5");
        params.put("pageToken",pageToken);

        params.put("key",getString(R.string.google_api_key));
        Call<YtModel> call = apiInterface.getYoutubeServerData(params);

        call.enqueue(new Callback<YtModel>() {
            @Override
            public void onResponse(Call<YtModel> call, Response<YtModel> response) {

                items.clear();
                items.addAll(response.body().getItems());
                recyclerView.setAdapter(youtubeAdapter);

            }

            @Override
            public void onFailure(Call<YtModel> call, Throwable t) {
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }
        });

    }
}
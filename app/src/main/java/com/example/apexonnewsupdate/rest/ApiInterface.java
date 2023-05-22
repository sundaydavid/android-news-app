package com.example.apexonnewsupdate.rest;

import com.example.apexonnewsupdate.models.HomepageModel;
import com.example.apexonnewsupdate.models.OurYModel;
import com.example.apexonnewsupdate.models.YtModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiInterface {

    //youtube_api_key = AIzaSyA0fgjeT9ES1xVmiXkdRrSjHSLMnDvycQs
    //Master Coding = UCb0VX3DhuwYY21ZFqf8LSqQ

    @GET("homepage_api")
    Call<HomepageModel> getHomepageApi(
            @QueryMap Map<String, String> params
            );

    @GET("news_by_pid")
    Call<HomepageModel> getNewsDetailById(
            @QueryMap Map<String, String> params
    );

    @GET("youtube")
    Call<OurYModel> getYoutubeDetailsFromServer(
    );

    //Connecting to cloud console
    @GET("https://www.googleapis.com/youtube/v3/activities")
    Call<YtModel> getYoutubeServerData(
            @QueryMap Map<String, String> params
    );

}

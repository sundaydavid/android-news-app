package com.example.apexonnewsupdate.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.apexonnewsupdate.R;
import com.example.apexonnewsupdate.adapters.ViewPagerAdapter;
import com.example.apexonnewsupdate.models.OurYModel;
import com.example.apexonnewsupdate.rest.ApiClient;
import com.example.apexonnewsupdate.rest.ApiInterface;
import com.google.android.material.tabs.TabLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YouTubeActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube);
        
        InitializeViews();
        
    }

    private void InitializeViews() {

        toolbar = findViewById(R.id.toolbar_youtube);
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tablayout);

        //Tablayout
        tabLayout.setupWithViewPager(viewPager);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("YouTube Videos");
        toolbar.setNavigationIcon(R.drawable.icon_arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getYoutubeData();

    }

    private void getYoutubeData() {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<OurYModel> call = apiInterface.getYoutubeDetailsFromServer();

        call.enqueue(new Callback<OurYModel>() {
            @Override
            public void onResponse(@NonNull Call<OurYModel> call, @NonNull Response<OurYModel> response) {
                viewPagerAdapter = new ViewPagerAdapter(
                        getSupportFragmentManager(),
                        YouTubeActivity.this,
                        response.body()
                );

                viewPager.setAdapter(viewPagerAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<OurYModel> call, @NonNull Throwable t) {

                Toast.makeText(YouTubeActivity.this, "Sorry!!!", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
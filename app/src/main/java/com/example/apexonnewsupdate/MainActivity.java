package com.example.apexonnewsupdate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.bumptech.glide.request.RequestOptions;
import com.example.apexonnewsupdate.activities.YouTubeActivity;
import com.example.apexonnewsupdate.adapters.GridCategoryAdaptor;
import com.example.apexonnewsupdate.adapters.NewsAdapter;
import com.example.apexonnewsupdate.models.HomepageModel;
import com.example.apexonnewsupdate.rest.ApiClient;
import com.example.apexonnewsupdate.rest.ApiInterface;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.slidertypes.BaseSliderView;
import com.glide.slider.library.slidertypes.DefaultSliderView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    
    SliderLayout sliderLayout;
    FloatingActionButton fab;

    GridView gridView;
    GridCategoryAdaptor adaptor;

    RecyclerView recyclerView;
    NewsAdapter newsAdapter;
    List<HomepageModel.News> news;
    List<HomepageModel.CategoryBotton> categoryBottons;

    int posts = 3;
    int page = 1;
    boolean isFromStart = true;
    ProgressBar progressBar;

    NestedScrollView nestedScrollView;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        InitializeViews();

//        AddImageToSlider();

        page = 1;
        isFromStart = true;

        //Getting Data
        getHomeData();

        //Getting new data on scrolling and swiping
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())){
                    isFromStart = false;
                    progressBar.setVisibility(View.VISIBLE);
                    page++;
                    getHomeData();

                }
            }
        });
    }

    private void getHomeData() {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Map<String, String> params = new HashMap<>();
        params.put("page",page+"");
        params.put("posts",posts+"");

        Call<HomepageModel> call = apiInterface.getHomepageApi(params);
        call.enqueue(new Callback<HomepageModel>() {
            @Override
            public void onResponse(Call<HomepageModel> call, Response<HomepageModel> response) {
                UpdateDataOnHomePage(response.body());
            }

            @Override
            public void onFailure(Call<HomepageModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void UpdateDataOnHomePage(HomepageModel body) {

        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);

        if (isFromStart){
            news.clear();
            categoryBottons.clear();
        }

        //Adding Slider image from server
        for (int i = 0; i < body.getBanners().size(); i++){
            DefaultSliderView defaultSliderView = new DefaultSliderView(this);
            defaultSliderView.setRequestOption(new RequestOptions().centerCrop());

            defaultSliderView.image(body.getBanners().get(i).getImage());
            defaultSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {

                }
            });

            sliderLayout.addSlider(defaultSliderView);
        }

        sliderLayout.startAutoCycle();
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Stack);
        sliderLayout.setDuration(3000);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);

        //Making new variable for progress bar
        int beforeNewsSize = news.size();

        for (int i = 0; i < body.getNews().size(); i++){
            news.add(body.getNews().get(i));
        }

        categoryBottons.addAll(body.getCategoryBotton());
        if (isFromStart){
            recyclerView.setAdapter(newsAdapter);
            gridView.setAdapter(adaptor);
        }else {
            newsAdapter.notifyItemRangeChanged(beforeNewsSize, body.getNews().size());
        }

    }

    @SuppressLint("ResourceAsColor")
    private void InitializeViews() {

        fab = findViewById(R.id.floatings);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, YouTubeActivity.class);
                startActivity(i);
            }
        });

        categoryBottons = new ArrayList<>();

        sliderLayout = findViewById(R.id.slider);
        gridView = findViewById(R.id.grid_view);
        adaptor = new GridCategoryAdaptor(this,categoryBottons);

        progressBar = findViewById(R.id.progressBar);
        nestedScrollView = findViewById(R.id.nestedScrollView);

        recyclerView = findViewById(R.id.recy_news);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        news = new ArrayList<>();
        newsAdapter = new NewsAdapter(this, news);

        swipeRefreshLayout = findViewById(R.id.swipy);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setColorSchemeColors(R.color.orange,R.color.blue,R.color.green);

        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sliderLayout.stopAutoCycle();
    }

    @Override
    public void onRefresh() {
        isFromStart = true;
        page = 1;
        getHomeData();
    }
}
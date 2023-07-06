package com.example.newsupdate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryClickInterface {
// a519489ba5a6492ba88f72844d41f450

    private RecyclerView newsRv,categoryRv;
    private ProgressBar loadingPB;
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<CategoryRVModal> categoryRVModalArrayList;
    private CategoryRVAdapter categoryRVAdapter;
    private NewsRVAdapter newsRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsRv = findViewById(R.id.idRVNews);
        categoryRv = findViewById(R.id.idRVCategories);
        loadingPB = findViewById(R.id.idPBLoading);
        articlesArrayList = new ArrayList<>();
        categoryRVModalArrayList = new ArrayList<>();
        newsRVAdapter = new NewsRVAdapter(articlesArrayList,this);
        categoryRVAdapter = new CategoryRVAdapter(categoryRVModalArrayList,this,this::onCategoryClick);
        newsRv.setLayoutManager(new LinearLayoutManager(this));
        newsRv.setAdapter(newsRVAdapter);
        categoryRv.setAdapter(categoryRVAdapter);
        getCategories();
        getNews("All");
        newsRVAdapter.notifyDataSetChanged();
    }
    private void getCategories(){
        categoryRVModalArrayList.add(new CategoryRVModal("All","https://www.istockphoto.com/photos/technology"));
        categoryRVModalArrayList.add(new CategoryRVModal("Technology","https://images.unsplash.com/photo-1461749280684-dccba630e2f6?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8dGVjaG5vbG9neSUyMHJlbGF0ZWQlMjBuZXdzfGVufDB8fDB8fHww&auto=format&fit=crop&w=600&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Science","https://media.istockphoto.com/id/1210918328/photo/social-networking-service-concept-communication-network.webp?b=1&s=170667a&w=0&k=20&c=StOyQCUZ4o-d9psjw_BXmXZPg78uq4d0ZaH34tl9QPU="));
        categoryRVModalArrayList.add(new CategoryRVModal("Sports","https://media.istockphoto.com/id/165088078/photo/focus-on-the-sports.webp?b=1&s=170667a&w=0&k=20&c=meHo0wLUq6qczkXg-6sdI9ta0QVLQ7KvYfWN6RdTvMw="));
        categoryRVModalArrayList.add(new CategoryRVModal("General","https://media.istockphoto.com/id/184625088/photo/breaking-news-headline.webp?b=1&s=170667a&w=0&k=20&c=GHLPyyDGpBGbn24lw8DoKshb2Uvuo3uT-Oq8iuG-XkM="));
        categoryRVModalArrayList.add(new CategoryRVModal("Business","https://media.istockphoto.com/id/147036034/photo/crisis-in-news.webp?b=1&s=170667a&w=0&k=20&c=X0J3Sckvs37a8AJiNWyKjDn9m2iysOWzoXIkkyLuXNk="));
        categoryRVModalArrayList.add(new CategoryRVModal("Entertainment","https://media.istockphoto.com/id/1189780272/photo/video-camera-camcorder-operator-working-at-live-event-broadcasting-blue-color-tone.webp?b=1&s=170667a&w=0&k=20&c=M7AYKOo8FkAZM1961wyiONWD4L1S6YAlKRYfSBkXe3M="));
        categoryRVModalArrayList.add(new CategoryRVModal("Health","https://media.istockphoto.com/id/1273886962/photo/close-up-of-doctor-is-touching-digital-virtual-screen-for-analytics-medical-data-medical.webp?b=1&s=170667a&w=0&k=20&c=R3TjWLqc9zNoQSKOT4TFihMyaE_7C745hsyIEfpJb5Q="));
        categoryRVAdapter.notifyDataSetChanged();
    }
    private void getNews(String category){
        loadingPB.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryURL = "https://newsapi.org/v2/top-headlines?country=in&category=" + category + "&apikey=a519489ba5a6492ba88f72844d41f450";
        String url = "https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=a519489ba5a6492ba88f72844d41f450";
        String Base_URL = "https://newsapi.org";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<NewsModal> call;
        if(category.equals("All")){
            call = retrofitAPI.getAllNews(url);
        }else {
            call = retrofitAPI.getNewsByCategory(categoryURL);
        }
        call.enqueue(new Callback<NewsModal>() {
            @Override
            public void onResponse(Call<NewsModal> call, Response<NewsModal> response) {
                NewsModal newsModal = response.body();
                loadingPB.setVisibility(View.GONE);
                ArrayList<Articles> articles = newsModal.getArticles();
                for (int i = 0; i<articles.size();i++){
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(), articles.get(i).getDescription(),articles.get(i).getUrlToImage(),articles.get(i).getUrl(),articles.get(i).getContent()));
                }
                newsRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<NewsModal> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fail to get news", Toast.LENGTH_SHORT).show();


            }
        });
    }

    @Override
    public void onCategoryClick(int position) {
     String category = categoryRVModalArrayList.get(position).getCategory();
     getNews(category);
    }
}
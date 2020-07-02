package com.azapps.moviesreviewer.ui;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.azapps.moviesreviewer.R;
import com.azapps.moviesreviewer.adapter.MovieAdapter;
import com.azapps.moviesreviewer.adapter.OnMovieClickListener;
import com.azapps.moviesreviewer.pojo.Movie;

import com.azapps.moviesreviewer.pojo.Results;
import com.azapps.moviesreviewer.repository.Constant;
import com.azapps.moviesreviewer.repository.MovieApi;

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.azapps.moviesreviewer.repository.Constant.RESULT_EXTRA;


public class MainActivity extends AppCompatActivity implements OnMovieClickListener {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ImageView previous;

    private List<Results> movieList;
    private MovieApi movieApi;
    private MovieAdapter adapter;
    private int pageNumber = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler);
        progressBar = findViewById(R.id.progress_bar);
        previous = findViewById(R.id.previous_btn);
        previous.setVisibility(View.GONE);

        buildRetrofit();

        getResultsFromRetrofit(pageNumber);


    }

    private void buildRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieApi = retrofit.create(MovieApi.class);
    }

    private void getResultsFromRetrofit(int pageId) {
        Call<Movie> call = movieApi.getMovies(Constant.API_KEY, pageId);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie movie = response.body();
                // the result returned correctly
                buildRecyclerView(movie.getResults());

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Internet error please check your connection", Toast.LENGTH_SHORT).show();
                stopProgressBar();
            }
        });
    }

    private void buildRecyclerView(List<Results> movies) {
        movieList = movies;
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.hasFixedSize();
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new MovieAdapter(this, this);
        stopProgressBar();
        adapter.submitList(movieList);
        recyclerView.setAdapter(adapter);
    }

    private void stopProgressBar(){
        progressBar.setVisibility(View.GONE);
    }



    @Override
    public void onMovieClick(int position) {
        Results clickedResult = movieList.get(position);
        Intent intent = new Intent(MainActivity.this,MovieDescriptionActivity.class);
        intent.putExtra(RESULT_EXTRA,clickedResult);

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
        startActivity(intent, options.toBundle());
    }


    public void next(View view) {
        progressBar.setVisibility(View.VISIBLE);
        pageNumber ++;
        getResultsFromRetrofit(pageNumber);
        if (pageNumber != 1){
            previous.setVisibility(View.VISIBLE);
        }
    }


    public void previous(View view) {
        progressBar.setVisibility(View.VISIBLE);
        pageNumber --;
        getResultsFromRetrofit(pageNumber);
        if (pageNumber == 1){
            previous.setVisibility(View.GONE);
        }
    }
}
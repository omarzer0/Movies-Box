package com.azapps.moviesreviewer.repository;


import com.azapps.moviesreviewer.pojo.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApi {
    // https://api.themoviedb.org/3/movie/popular?api_key=49066942591aa4286806177d1fc935a0&page=1
    @GET("movie/popular")
    Call<Movie> getMovies(@Query("api_key") String api_key, @Query("page") int pageId);
}

package com.azapps.moviesreviewer.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

import com.azapps.moviesreviewer.R;
import com.azapps.moviesreviewer.pojo.Results;
import com.bumptech.glide.Glide;

public class MovieAdapter extends ListAdapter<Results, MovieViewHolder> {
    private Context context;
    OnMovieClickListener listener;
    private int lastPosition = -1;
    private static final DiffUtilMovieCallback UTIL_MOVIE_CALLBACK = new DiffUtilMovieCallback();

    public MovieAdapter(Context context, OnMovieClickListener onMovieClickListener) {
        super(UTIL_MOVIE_CALLBACK);
        this.context = context;
        listener = onMovieClickListener;
    }


    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Results currentMovie = getItem(position);
        //holder.cardView.setAnimation(AnimationUtils.loadAnimation(context,android.R.anim.fade_in));
        Uri image_url = Uri.parse(currentMovie.getPoster_path());
        Glide.with(context).load(image_url).into(holder.movieImageView);
        holder.releaseDateTV.setText(currentMovie.getRelease_date());
        holder.titleTV.setText(currentMovie.getTitle());
        holder.ratingTv.setText(currentMovie.getVote_average());
        setAnimation(holder.cardView, position);
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public void setOnMovieClickListener(OnMovieClickListener listener) {
        this.listener = listener;
    }

}

package com.azapps.moviesreviewer.adapter;

import androidx.recyclerview.widget.DiffUtil;

import com.azapps.moviesreviewer.pojo.Results;

public class DiffUtilMovieCallback extends DiffUtil.ItemCallback<Results> {

    @Override
    public boolean areItemsTheSame(Results oldItem, Results newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(Results oldItem, Results newItem) {
        return (oldItem.getTitle().equals(newItem.getTitle()));
    }
}

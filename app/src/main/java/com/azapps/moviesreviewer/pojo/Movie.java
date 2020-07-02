package com.azapps.moviesreviewer.pojo;

import java.util.List;

public class Movie {
    private List<Results> results;

    public Movie(List<Results> results) {
        this.results = results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }

    public List<Results> getResults() {
        return results;
    }

}

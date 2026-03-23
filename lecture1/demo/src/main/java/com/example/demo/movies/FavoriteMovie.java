package com.example.demo.movies;

import java.util.List;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class FavoriteMovie {
    private List<Movie> movies;
    public FavoriteMovie() {
        this.movies = new ArrayList<>();
    }

    public List<Movie> getMovies() {
        return this.movies;
    }
    public void addMovie(Movie movie) {
        this.movies.add(movie);
    }
    public void removeMovieById(Long id) {
        this.movies.removeIf(movie -> movie.getId().equals(id));
    }
}

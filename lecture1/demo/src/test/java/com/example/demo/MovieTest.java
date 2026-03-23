package com.example.demo;

import com.example.demo.movies.Movie;
import com.example.demo.movies.MovieType;
import com.example.demo.movies.FavoriteMovie;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MovieTest {
    
    @Test
    void testMovie() {
        // Create a new movie
        Movie movie = new Movie(1L, "Inception", MovieType.SINGLE);
        
        // Test getters
        assertEquals(1L, movie.getId());
        assertEquals("Inception", movie.getTitle());
        assertEquals(MovieType.SINGLE, movie.getType());
    }

    @Test       
    void testLikeMovie() {
        // Create a new movie       
        Movie movie = new Movie(2L, "The Matrix", MovieType.SINGLE);
        
        // Test liking a movie
        FavoriteMovie favoriteMovie = new FavoriteMovie();
        favoriteMovie.addMovie(movie);
        
        assertEquals(1, favoriteMovie.getMovies().size());
        assertTrue(favoriteMovie.getMovies().contains(movie));
    }
    @Test
    void testUnlikeMovie() {
        // Create a new movie
        Movie movie = new Movie(3L, "Interstellar", MovieType.SINGLE);
        
        // Test unliking a movie
        FavoriteMovie favoriteMovie = new FavoriteMovie();
        favoriteMovie.addMovie(movie);
        favoriteMovie.removeMovieById(3L);
        
        assertTrue(!favoriteMovie.getMovies().contains(movie));
    }
}

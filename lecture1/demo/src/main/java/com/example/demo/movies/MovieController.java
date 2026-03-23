package com.example.demo.movies;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping("/api")
public class MovieController {
    private FavoriteMovie favoriteMovie;

    public MovieController(FavoriteMovie favoriteMovie) {
        this.favoriteMovie = favoriteMovie;
    }
    @GetMapping("/")
    public String home() {
        return "Welcome to the Movie API!".toUpperCase();
    }
    
    @GetMapping("/movies")
    public String getMovies(@RequestParam(name = "type", defaultValue = "SINGLE") MovieType type) {
        List<Movie> movies = favoriteMovie.getMovies();
        return "You requested for movies of type: " + type + movies.toString();
    }

    @PostMapping("/movies/like")
    public String likeMovie(@RequestBody Movie movie) {
        favoriteMovie.addMovie(movie);
        return "You liked the movie: " + movie.getTitle();
    }

    @DeleteMapping("/movies/{id}")
    public String unlikeMovie(@PathVariable Long id) {  
        favoriteMovie.removeMovieById(id);
        return "You unliked the movie with ID: " + id;
    }
}

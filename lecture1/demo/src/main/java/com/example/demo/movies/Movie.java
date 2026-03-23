package com.example.demo.movies;

public class Movie {
    private Long id;
    private String title;
    private MovieType type;

    public Movie() {
    }

    public Movie(Long id, String title, MovieType type) {
        this.id = id;
        this.title = title;
        this.type = type;
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public MovieType getType() {
        return this.type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Movie)) {
            return false;
        }
        Movie movie = (Movie) o;
        return this.id.equals(movie.id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}

package com.example.demo.Model;

import jakarta.persistence.*;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long parentMovieId;
    private String name;
    @Column(length = 5000)
    private String description;
    private double rating;
    private String posterPath;

    public Movie(String name, String description, String posterPath) {
        this.name = name;
        this.description = description;
        this.posterPath = posterPath;
    }

    public Movie() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentMovieId() {
        return parentMovieId;
    }

    public void setParentMovieId(Long parentMovieId) {
        this.parentMovieId = parentMovieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
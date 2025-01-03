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

    @Column(nullable = false)
    private Integer ratingCount = 0;

    public Movie(String name, String description, String posterPath) {
        this.name = name;
        this.description = description;
        this.posterPath = posterPath;
        this.ratingCount = 0; // Начальное значение
    }

    public Movie() {
        this.ratingCount = 0; // Начальное значение
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

    public Integer getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(Integer ratingCount) {
        this.ratingCount = ratingCount;
    }

    public void incrementRatingCount() {
        if (this.ratingCount == null) {
            this.ratingCount = 0;
        }
        this.ratingCount++;
    }
}

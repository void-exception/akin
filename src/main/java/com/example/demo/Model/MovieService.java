package com.example.demo.Model;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieElasticRepository movieElasticRepository;

    public MovieService(MovieRepository movieRepository, MovieElasticRepository movieElasticRepository) {
        this.movieRepository = movieRepository;
        this.movieElasticRepository = movieElasticRepository;
    }
    public Movie AddMovie(Movie movie){
        Movie savedMovie = movieRepository.save(movie);

        MovieElastic movieElastic = new MovieElastic(
                savedMovie.getId().toString(),
                savedMovie.getName(),
                savedMovie.getDescription()
        );


        movieElasticRepository.save(movieElastic);

        return savedMovie;
    }

    public List<Movie> searchMovieElastic(String searchText)
    {
        List<MovieElastic> searchOnElastic = movieElasticRepository.searchByQuery(searchText);

        if(searchOnElastic == null || searchOnElastic.isEmpty())
        {
            return Collections.emptyList();
        }

        Set<Long> ids = searchOnElastic.stream().map(movieElastic -> Long.valueOf(movieElastic.getId())).collect(Collectors.toSet());
        List<Movie> searchMovie = movieRepository.findAllById(ids);
        return searchMovie;
    }

}

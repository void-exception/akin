package com.example.demo.Model;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieElasticRepository movieElasticRepository;
    private final MovieRecomendationRepository movieRecomendationRepository;

    public MovieService(MovieRepository movieRepository, MovieElasticRepository movieElasticRepository, MovieRecomendationRepository movieRecomendationRepository) {
        this.movieRepository = movieRepository;
        this.movieElasticRepository = movieElasticRepository;
        this.movieRecomendationRepository = movieRecomendationRepository;
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

    public List<Long> getTop5RelatedMovies (Long movieId)
    {
        Optional<MovieRecomendation> recomendation = movieRecomendationRepository.findById(movieId);
        if (recomendation.isPresent()) {
            List<Long> top5Movies = recomendation.get().getRelatedMovies().entrySet()
                    .stream()
                    .sorted(Map.Entry.<Long, Integer>comparingByValue().reversed())
                    .limit(5)
                    .map(Map.Entry::getKey)
                    .toList();
            if (top5Movies.isEmpty()) {
                return movieRepository.findTop5ByOrderByRatingDesc().stream().map(Movie::getId).toList();
            }
            return top5Movies;
        }
        return movieRepository.findTop5ByOrderByRatingDesc().stream().map(Movie::getId).toList();
    }


}

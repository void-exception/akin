package com.example.demo.Model;

import com.example.demo.Regestration.Model.AppUser;
import com.example.demo.Regestration.Model.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ScoreService {
    private final MovieRecomendationRepository movieRecomendationRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public ScoreService(MovieRecomendationRepository movieRecomendationRepository, UserRepository userRepository, MovieRepository movieRepository) {
        this.movieRecomendationRepository = movieRecomendationRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    public String submitScore(Movie movie,Long idUser, Integer score)
    {
        movie.setRating((movie.getRating() * movie.getRatingCount() + score) / (movie.getRatingCount() + 1));
        movie.incrementRatingCount();

        if(score==5) {
            AppUser appUser = userRepository.findById(idUser)
                    .orElseThrow(() -> new IllegalArgumentException("AppUser not found with id: " + idUser));//Убрать

            Long movieGetId = movie.getId();

            if (appUser.getFavoriteMovies().add(movieGetId)) { // Добавляем фильм, только если его ещё нет
                userRepository.save(appUser);
            }

            Optional<MovieRecomendation> existingRecomendation = movieRecomendationRepository.findById(movie.getId());
            MovieRecomendation movieRecomendation;

            if (existingRecomendation.isPresent()) {
                movieRecomendation = existingRecomendation.get();
            } else {
                movieRecomendation = new MovieRecomendation();
                movieRecomendation.setId(movieGetId);
            }

            for (Long movieId : appUser.getFavoriteMovies()) {
                if (movieId.equals(movieGetId))
                {
                    continue;
                }
                movieRecomendation.getRelatedMovies().merge(movieId, 1, Integer::sum);
            }
            movieRecomendationRepository.save(movieRecomendation);
        }
        movieRepository.save(movie);
        return "redirect:/home";
    }
}
package com.example.demo.Model;

import com.example.demo.Regestration.Model.MyAppUser;
import com.example.demo.Regestration.Model.MyAppUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ScoreService {
    private final MovieRecomendationRepository movieRecomendationRepository;
    private final MyAppUserRepository myAppUserRepository;
    private final MovieRepository movieRepository;

    public ScoreService(MovieRecomendationRepository movieRecomendationRepository, MyAppUserRepository myAppUserRepository, MovieRepository movieRepository) {
        this.movieRecomendationRepository = movieRecomendationRepository;
        this.myAppUserRepository = myAppUserRepository;
        this.movieRepository = movieRepository;
    }

    public String submitScore(Movie movie,Long idUser, Integer score)
    {
        if (movie.getRating() == 0) {
            movie.setRating(score);
        }
        else {
            movie.setRating((movie.getRating() + score) / 2);
        }
        MyAppUser user = myAppUserRepository.findById(idUser)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + idUser));

        user.getFavoriteMovies().merge(movie.getId(), 1, Integer::sum);

        movieRepository.save(movie);
        myAppUserRepository.save(user);//ПО ИДЕИ С ПСЙЛ ВСЕ ОК

        Optional<MovieRecomendation> existingRecomendation = movieRecomendationRepository.findById(movie.getId());
        MovieRecomendation movieRecomendation;

        if (existingRecomendation.isPresent()) {
            movieRecomendation = existingRecomendation.get();
        }
        else {
            movieRecomendation = new MovieRecomendation();
            movieRecomendation.setId(movie.getId());
        }

        Set<Long> idMovies = user.getFavoriteMovies().keySet();
        for (Long movieId :idMovies) {
            movieRecomendation.getRelatedMovies().merge(movieId, 1, Integer::sum);
        }

        movieRecomendationRepository.save(movieRecomendation);

        return "redirect:/home";
    }
}

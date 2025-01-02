package com.example.demo.Controller;

import com.example.demo.Model.*;
import com.example.demo.Regestration.Model.MyAppUser;
import com.example.demo.Regestration.Model.MyAppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Optional;

@Controller
public class MovieController {

    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieService movieService;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private MyAppUserRepository userRepository;
    @Autowired
    private MovieRecomendationRepository movieRecomendationRepository;

    @GetMapping({"/home", "/"})
    public String home(Model model){
        Iterable<Movie> movies = movieRepository.findAll();
        model.addAttribute("movies", movies);
        model.addAttribute("line", "Popular films");
        return "index";
    }


    @GetMapping("/add-movie")
    public String addMovie()
    {
        return "addMovie";
    }


    @GetMapping("/search")
    public String searchMovies(@RequestParam("query") String query, Model model){
        if (query.equals("")) {
            Iterable<Movie> movies = movieRepository.findAll();
            model.addAttribute("movies", movies);
            model.addAttribute("line", "Popular films");
        }
        else {
            Iterable<Movie> movies = movieService.searchMovieElastic(query);
            model.addAttribute("movies", movies);
            model.addAttribute("line", query);
        }
        return "index";
    }


    @GetMapping("/details/{id}")
    public String movieDetails(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Movie> movieOptional = movieRepository.findById(id);
        if (movieOptional.isPresent()){
            Movie movie = movieOptional.get();
            model.addAttribute("movie", movie);
            Iterable<Movie> movies = movieRepository.findAllById(movieService.getTop5RelatedMovies(id));
            model.addAttribute("allMovies", movies);
            return "movieDetails";
        }
        else
        {
            return "notFound";
        }
    }


    /*POST----------------------------------------------------------POST*/

    @PostMapping("/add-movie")
    public String addPostMovie(@RequestParam String name, @RequestParam String description, @RequestParam("file") MultipartFile file,  Model model)
    {
        if(file.isEmpty()) {
            model.addAttribute("error", "The poster is not selected");
            return "redirect:/home";
        }
        try {
            String posterPath = fileStorageService.uploadFileToYandexCloud(file);

            Movie movie = new Movie(name, description, posterPath);
            movieService.AddMovie(movie);



        } catch (Exception e){
            e.printStackTrace();
            model.addAttribute("error", "File upload error");
            return "addMovie";

        }
        return "redirect:/home";
    }

    @PostMapping("/submit-score")
    public String submitScore(@RequestParam("score") Integer score, @RequestParam("movieId") Long movieId, @AuthenticationPrincipal MyAppUser currentUser, Model model) {
        Movie movie = movieRepository.findById(movieId).orElseThrow();

        String as = scoreService.submitScore(movie, currentUser.getId(), score);
        return as;
    }

}

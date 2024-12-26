package com.example.demo.Controller;

import com.example.demo.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MovieController {

    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieService movieService;


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
        Iterable<Movie> movies = movieService.searchMovieElastic(query);
        model.addAttribute("movies", movies);
        model.addAttribute("line", query);
        return "index";
    }


    /*POST----------------------------------------------------------POST*/

    @PostMapping("/add-movie")
    public String addPostMovie(@RequestParam String name, @RequestParam String description, @RequestParam("file") MultipartFile file, Model model)
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

}

package com.example.demo.Regestration.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentController {
    public ContentController() {
    }
    @GetMapping({"/req/login"})
    public String login(){
        return "login";
    }

    @GetMapping({"/req/signup"})
    public String signup(){
        return "signup";
    }
    @GetMapping({"/index"})
    public String home(){
        return "index";
    }
}


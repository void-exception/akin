package com.example.demo.Regestration.Controller;
import com.example.demo.Regestration.Model.AppUser;
import com.example.demo.Regestration.Model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public RegistrationController() {
    }

    @PostMapping(
            value = {"/req/signup"},
            consumes = {"application/json"}
    )
    public AppUser createUser(@RequestBody AppUser appUser) {
        appUser.setPassword(this.passwordEncoder.encode(appUser.getPassword()));
        return (AppUser)this.userRepository.save(appUser);
    }
}

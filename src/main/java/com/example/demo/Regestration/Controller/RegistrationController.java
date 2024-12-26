package com.example.demo.Regestration.Controller;
import com.example.demo.Regestration.Model.MyAppUser;
import com.example.demo.Regestration.Model.MyAppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {
    @Autowired
    private MyAppUserRepository myAppUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public RegistrationController() {
    }

    @PostMapping(
            value = {"/req/signup"},
            consumes = {"application/json"}
    )
    public MyAppUser createUser(@RequestBody MyAppUser user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return (MyAppUser)this.myAppUserRepository.save(user);
    }
}

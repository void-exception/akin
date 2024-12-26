package com.example.demo.Regestration.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class MyAppUserService implements UserDetailsService {
    @Autowired
    private MyAppUserRepository repository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyAppUser> user = this.repository.findByUsername(username);
        if (user.isPresent()) {
            MyAppUser userObj = (MyAppUser)user.get();
            return User.builder().username(userObj.getUsername()).password(userObj.getPassword()).build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    public MyAppUserService(final MyAppUserRepository repository) {
        this.repository = repository;
    }
}

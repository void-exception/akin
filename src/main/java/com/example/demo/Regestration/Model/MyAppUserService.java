package com.example.demo.Regestration.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

//@Service
//public class MyAppUserService implements UserDetailsService {
//    @Autowired
//    private UserRepository repository;
//
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<AppUser> user = this.repository.findByUsername(username);
//        if (user.isPresent()) {
//            AppUser userObj = (AppUser)user.get();
//            return AppUser.builder().username(userObj.getUsername()).password(userObj.getPassword()).build();
//        } else {
//            throw new UsernameNotFoundException(username);
//        }
//    }
//
//    public MyAppUserService(final UserRepository repository) {
//        this.repository = repository;
//    }
//}
@Service
public class MyAppUserService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<AppUser> user = this.repository.findByUsername(username);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    public MyAppUserService(final UserRepository repository) {
        this.repository = repository;
    }

}

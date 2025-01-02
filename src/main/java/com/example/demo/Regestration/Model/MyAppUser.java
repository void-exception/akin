package com.example.demo.Regestration.Model;
import jakarta.persistence.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class MyAppUser implements UserDetails {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private Long id;
    private String username;
    private String email;
    private String password;
    @ElementCollection
    @CollectionTable(name = "favorite_movies", joinColumns = @JoinColumn(name = "my_app_user_id"))
    @MapKeyColumn(name = "movie_id")
    @Column(name = "quantity")
    private Map<Long, Integer> favoriteMovies = new HashMap<>();

    public MyAppUser() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<Long, Integer> getFavoriteMovies() {
        return favoriteMovies;
    }

    public void setFavoriteMovies(Map<Long, Integer> favoriteMovies) {
        this.favoriteMovies = favoriteMovies != null ? favoriteMovies : new HashMap<>();
    }
}

//package com.example.demo.Regestration.Model;
//
//import jakarta.persistence.ElementCollection;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.List;
//
//@Entity
//public class MyAppUser implements UserDetails {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//    private String username;
//    private String email;
//    private String password;
//
//    @ElementCollection
//    private HashMap<Long, Integer> favoriteMovies = new HashMap<>();
//
//    public MyAppUser() {}
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public HashMap<Long, Integer> getFavoriteMovies() {
//        return favoriteMovies;
//    }
//
//    public void setFavoriteMovies(HashMap<Long, Integer> favoriteMovies) {
//        this.favoriteMovies = favoriteMovies != null ? favoriteMovies : new HashMap<>();
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}

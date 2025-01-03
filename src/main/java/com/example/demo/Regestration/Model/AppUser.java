package com.example.demo.Regestration.Model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id; // Исправленный импорт
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
public class AppUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String email;
    private String password;

    @ElementCollection
    private Set<Long> favoriteMovies = new HashSet<>();

    public AppUser() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
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
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Long> getFavoriteMovies() {
        return favoriteMovies;
    }

    public void setFavoriteMovies(Set<Long> favoriteMovies) {
        this.favoriteMovies = favoriteMovies;
    }

    // Methods required by UserDetails interface
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(); // Return empty list for simplicity
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Always return true for now
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Always return true for now
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Always return true for now
    }

    @Override
    public boolean isEnabled() {
        return true; // Always return true for now
    }
}

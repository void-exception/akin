package com.example.demo.Model;

import org.springframework.data.annotation.Id;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.HashMap;

@Data
@RedisHash("RecMovieApp")
public class MovieRecomendation implements Serializable{
    @Id
    private Long id;
    private HashMap<Long, Integer> relatedMovies = new HashMap<>();
}

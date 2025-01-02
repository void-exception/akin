package com.example.demo.Model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRecomendationRepository extends CrudRepository<MovieRecomendation, Long> {
}

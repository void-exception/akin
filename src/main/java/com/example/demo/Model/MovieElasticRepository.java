package com.example.demo.Model;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieElasticRepository extends ElasticsearchRepository <MovieElastic, String> {
@Query("""
    {
        "bool": {
            "should": [
                {
                    "multi_match": {
                        "query": "?0",
                        "fields": ["name^4", "description^3"],
                        "type": "best_fields",
                        "operator": "or",
                        "fuzziness": "AUTO",
                        "analyzer": "russian"
                    }
                },
                {
                    "match_phrase": {
                        "name": {
                            "query": "?0",
                            "boost": 3
                        }
                    }
                },
                {
                    "match_phrase": {
                        "description": {
                            "query": "?0",
                            "boost": 2
                        }
                    }
                }
            ],
            "minimum_should_match": 1
        }
    }
    """)

List<MovieElastic> searchByQuery(String query);
}

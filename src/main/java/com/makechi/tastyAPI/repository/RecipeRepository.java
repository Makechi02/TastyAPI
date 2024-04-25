package com.makechi.tastyAPI.repository;

import com.makechi.tastyAPI.entity.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecipeRepository extends MongoRepository<Recipe, String> {
}

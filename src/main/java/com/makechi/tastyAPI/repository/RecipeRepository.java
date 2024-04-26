package com.makechi.tastyAPI.repository;

import com.makechi.tastyAPI.entity.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RecipeRepository extends MongoRepository<Recipe, String> {
	List<Recipe> findByTitleContaining(String title);
}

package com.makechi.tastyAPI.service;

import com.makechi.tastyAPI.entity.Recipe;

import java.util.List;

public interface RecipeService {
	List<Recipe> getAllRecipes();

	Recipe getRecipeById(String recipeID);

	Recipe addRecipe(Recipe recipe);

	String deleteRecipeById(String recipeID);

	Recipe updateRecipe(String recipeID, Recipe recipe);
}

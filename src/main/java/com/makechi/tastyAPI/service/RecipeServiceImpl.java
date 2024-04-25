package com.makechi.tastyAPI.service;

import com.makechi.tastyAPI.entity.Recipe;
import com.makechi.tastyAPI.exception.ResourceNotFoundException;
import com.makechi.tastyAPI.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

	private final RecipeRepository recipeRepository;

	@Override
	public List<Recipe> getAllRecipes() {
		return recipeRepository.findAll();
	}

	@Override
	public Recipe getRecipeById(String recipeID) {
		return recipeRepository.findById(recipeID)
				.orElseThrow(() -> new ResourceNotFoundException("Recipe with ID: " + recipeID + " not found"));
	}

	@Override
	public Recipe addRecipe(Recipe recipe) {
		return recipeRepository.save(recipe);
	}

	@Override
	public Recipe updateRecipe(String recipeID, Recipe recipe) {
		Recipe fetchedRecipe = recipeRepository.findById(recipeID)
				.orElseThrow(() -> new ResourceNotFoundException("Recipe with ID: " + recipeID + " not found"));

		BeanUtils.copyProperties(recipe, fetchedRecipe, "recipeID");

		return recipeRepository.save(fetchedRecipe);
	}

	@Override
	public String deleteRecipeById(String recipeID) {
		if (!recipeRepository.existsById(recipeID)) {
			throw new ResourceNotFoundException("Recipe with ID: " + recipeID + " not found");
		}
		recipeRepository.deleteById(recipeID);
		return "Recipe with id: " + recipeID + " deleted successfully";
	}
}

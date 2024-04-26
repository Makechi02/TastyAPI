package com.makechi.tastyAPI.service;

import com.makechi.tastyAPI.constants.DifficultyLevel;
import com.makechi.tastyAPI.entity.Recipe;
import com.makechi.tastyAPI.exception.ResourceNotFoundException;
import com.makechi.tastyAPI.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

	private final RecipeRepository recipeRepository;

	@Override
	public List<Recipe> getAllRecipes() {
		List<Recipe> recipes = recipeRepository.findAll();
		log.info("Total recipes: {}", recipes.size());
		return recipes;
	}

	@Override
	public Recipe getRecipeById(String recipeID) {
		return recipeRepository.findById(recipeID)
				.orElseThrow(() -> new ResourceNotFoundException("Recipe with ID: " + recipeID + " not found"));
	}

	@Override
	public Recipe addRecipe(Recipe recipe) {
		Recipe savedRecipe = recipeRepository.save(recipe);
		log.info("Added recipe: {}", savedRecipe);
		return savedRecipe;
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

	@Override
	public List<Recipe> searchRecipesByTitle(String title) {
		List<Recipe> recipes = recipeRepository.findByTitleContaining(title);
		log.info("Total recipes with {} title: {}", title, recipes.size());
		return recipes;
	}

	@Override
	public List<Recipe> searchRecipesByKeywords(List<String> keywords) {
		List<Recipe> recipes = recipeRepository.findAll().stream()
				.filter(recipe -> containsAnyKeyword(recipe, keywords))
				.toList();
		log.info("Total recipes with keywords: {}", recipes.size());
		return recipes;
	}

	private boolean containsAnyKeyword(Recipe recipe, List<String> keywords) {
		List<String> keywordList = recipe.getKeywords().stream()
				.map(String::toLowerCase)
				.toList();
		for (String keyword : keywords) {
			if (keywordList.contains(keyword.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Recipe> filterRecipesByCuisine(String cuisine) {
		List<Recipe> recipes = recipeRepository.findAll().stream()
				.filter(recipe -> recipe.getCuisine().equalsIgnoreCase(cuisine))
				.toList();

		log.info("Total recipes with {} cuisine: {} ", cuisine, recipes.size());
		return recipes;
	}

	@Override
	public List<Recipe> filterRecipesByDiet(String diet) {
		List<Recipe> recipes = recipeRepository.findAll().stream()
				.filter(recipe -> {
					boolean result = false;
					if (recipe.getDietaryInformation() != null) {
						result = recipe.getDietaryInformation().equalsIgnoreCase(diet);
					}

					return result;
				})
				.toList();

		log.info("Total recipes with {} diet: {}", diet, recipes.size());
		return recipes;
	}

	@Override
	public List<Recipe> filterRecipesByDifficulty(DifficultyLevel difficultyLevel) {
		List<Recipe> recipes = recipeRepository.findAll().stream()
				.filter(recipe -> recipe.getDifficultyLevel().equals(difficultyLevel))
				.toList();

		log.info("Total recipes with {} difficulty: {}", difficultyLevel, recipes.size());
		return recipes;
	}
}

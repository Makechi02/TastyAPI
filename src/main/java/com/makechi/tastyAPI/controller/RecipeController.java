package com.makechi.tastyAPI.controller;

import com.makechi.tastyAPI.constants.DifficultyLevel;
import com.makechi.tastyAPI.entity.Recipe;
import com.makechi.tastyAPI.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/recipes")
@RequiredArgsConstructor
public class RecipeController {
	private final RecipeService recipeService;

	@GetMapping
	public List<Recipe> getAllRecipes() {
		return recipeService.getAllRecipes();
	}

	@GetMapping("/{recipeID}")
	public Recipe getRecipeById(@PathVariable String recipeID) {
		return recipeService.getRecipeById(recipeID);
	}

	@PostMapping
	public ResponseEntity<Recipe> addRecipe(@RequestBody Recipe recipe) {
		Recipe savedRecipe = recipeService.addRecipe(recipe);
		return new ResponseEntity<>(savedRecipe, HttpStatus.CREATED);
	}

	@PutMapping("/{recipeID}")
	public Recipe updateRecipe(@PathVariable String recipeID, @RequestBody Recipe recipe) {
		return recipeService.updateRecipe(recipeID, recipe);
	}

	@DeleteMapping("/{recipeID}")
	public ResponseEntity<String> deleteRecipeById(@PathVariable String recipeID) {
		String response = recipeService.deleteRecipeById(recipeID);
	 	return ResponseEntity.ok(response);
	}

	@GetMapping("/search/title")
	public List<Recipe> searchRecipesByTitle(@RequestParam String title) {
		return recipeService.searchRecipesByTitle(title);
	}

	@GetMapping("/search/keywords")
	public List<Recipe> searchRecipesByKeywords(@RequestParam List<String> keywords) {
		return recipeService.searchRecipesByKeywords(keywords);
	}

	@GetMapping("/filter/cuisine")
	public List<Recipe> filterRecipesByCuisine(@RequestParam String cuisine) {
		return recipeService.filterRecipesByCuisine(cuisine);
	}

	@GetMapping("/filter/diet")
	public List<Recipe> filterRecipesByDiet(@RequestParam String diet) {
		return recipeService.filterRecipesByDiet(diet);
	}

	@GetMapping("/filter/difficulty")
	public List<Recipe> filterRecipesByDifficulty(@RequestParam DifficultyLevel difficulty) {
		return recipeService.filterRecipesByDifficulty(difficulty);
	}
}

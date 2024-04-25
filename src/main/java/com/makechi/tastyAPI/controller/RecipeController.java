package com.makechi.tastyAPI.controller;

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
}

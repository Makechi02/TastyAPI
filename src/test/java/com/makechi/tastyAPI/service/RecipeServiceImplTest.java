package com.makechi.tastyAPI.service;

import com.makechi.tastyAPI.entity.Recipe;
import com.makechi.tastyAPI.exception.ResourceNotFoundException;
import com.makechi.tastyAPI.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class RecipeServiceImplTest {

	private final Recipe recipe = Recipe.builder()
			.recipeID("662a8e071263130bc462b459")
			.title("Test Recipe")
			.build();

	private final Recipe recipe2 = Recipe.builder()
			.recipeID("662aa6e4698dc630e2f030e4")
			.title("Test Recipe 2")
			.build();

	@Mock
	private RecipeRepository recipeRepository;

	@InjectMocks
	private  RecipeServiceImpl recipeService;

	@Test
	void TestGetAllRecipes() {
		List<Recipe> recipes = List.of(recipe, recipe2);
		when(recipeRepository.findAll()).thenReturn(recipes);

		List<Recipe> recipeList = recipeService.getAllRecipes();
		assertEquals(2, recipeList.size());
		assertEquals(recipe, recipeList.getFirst());
		assertEquals(recipe2, recipeList.get(1));
	}

	@Test
	void testGetRecipeById() {
		when(recipeRepository.findById("662a8e071263130bc462b459")).thenReturn(Optional.of(recipe));

		Recipe fetchedRecipe = recipeService.getRecipeById("662a8e071263130bc462b459");
		assertEquals("Test Recipe", fetchedRecipe.getTitle());
	}

	@Test
	void testAddRecipe() {
		when(recipeRepository.save(recipe)).thenReturn(recipe);
		Recipe savedRecipe = recipeService.addRecipe(recipe);
		assertNotNull(savedRecipe);
		assertEquals(recipe.getTitle(), savedRecipe.getTitle());
	}

	@Test
	void testUpdateRecipe() {
	}

	@Test
	void testDeleteRecipeById() {
		doNothing().when(recipeRepository).deleteById("662a8e071263130bc462b459");
		when(recipeRepository.existsById("662a8e071263130bc462b459")).thenReturn(true);
		String message = recipeService.deleteRecipeById("662a8e071263130bc462b459");
		assertEquals("Recipe with id: 662a8e071263130bc462b459 deleted successfully", message);
	}

	@Test
	void deleteRecipeByIdShouldThrowErrorWhenIDNotFound() {
		var exception = assertThrows(ResourceNotFoundException.class, () -> recipeService.deleteRecipeById("662a8e0712130bc4629"));
		assertEquals("Recipe with ID: 662a8e0712130bc4629 not found", exception.getMessage());
	}

	@Test
	void getRecipeByIdShouldThrowErrorWhenIDNotFound() {
		var exception = assertThrows(ResourceNotFoundException.class, () -> recipeService.getRecipeById("662a8e0712130bc4629"));
		assertEquals("Recipe with ID: 662a8e0712130bc4629 not found", exception.getMessage());
	}

	@Test
	void updateRecipeShouldThrowErrorWhenIDNotFound() {
		var exception = assertThrows(ResourceNotFoundException.class, () -> recipeService.updateRecipe("662a8e0712130bc4629", recipe));
		assertEquals("Recipe with ID: 662a8e0712130bc4629 not found", exception.getMessage());
	}
}
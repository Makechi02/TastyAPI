package com.makechi.tastyAPI.service;

import com.makechi.tastyAPI.constants.DifficultyLevel;
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
			.recipeID("662acdc633a8cc022a8a23ad")
			.title("Nyama Choma")
			.preparationTime(15)
			.cookingTime(20)
			.servings(4)
			.difficultyLevel(DifficultyLevel.EASY)
			.cuisine("Kenyan")
			.keywords(List.of("Nyama Choma", "barbecue", "Kenyan"))
			.build();

	private final Recipe recipe2 = Recipe.builder()
			.recipeID("662acddd33a8cc022a8a23ae")
			.title("Ugali and Sukuma Wiki")
			.preparationTime(10)
			.cookingTime(20)
			.servings(4)
			.difficultyLevel(DifficultyLevel.EASY)
			.cuisine("Kenyan")
			.dietaryInformation("Vegetarian")
			.keywords(List.of("Ugali", "Sukuma Wiki", "Kenyan", "vegetarian"))
			.build();

	private final Recipe recipe3 = Recipe.builder()
			.recipeID("662acde933a8cc022a8a23af")
			.title("Chapati")
			.preparationTime(15)
			.cookingTime(20)
			.servings(6)
			.difficultyLevel(DifficultyLevel.MEDIUM)
			.cuisine("Kenyan")
			.dietaryInformation("Vegetarian")
			.keywords(List.of("Chapati", "flatbread", "Kenyan", "vegetarian"))
			.build();

	private final Recipe recipe4 = Recipe.builder()
			.recipeID("662acdf633a8cc022a8a23b0")
			.title("Matoke Stew")
			.preparationTime(20)
			.cookingTime(40)
			.servings(6)
			.difficultyLevel(DifficultyLevel.MEDIUM)
			.cuisine("Kenyan")
			.keywords(List.of("Matoke", "stew", "Kenyan"))
			.build();

	private final Recipe recipe5 = Recipe.builder()
			.recipeID("662ace1833a8cc022a8a23b3")
			.title("Kenyan Pilau")
			.preparationTime(30)
			.cookingTime(60)
			.servings(6)
			.difficultyLevel(DifficultyLevel.DIFFICULT)
			.cuisine("Kenyan")
			.keywords(List.of("Pilau", "Kenyan", "rice", "spices", "meat"))
			.build();

	private final Recipe recipe6 = Recipe.builder()
			.recipeID("662ace2433a8cc022a8a23b4")
			.title("Kenyan Samosa")
			.preparationTime(30)
			.cookingTime(45)
			.servings(4)
			.difficultyLevel(DifficultyLevel.DIFFICULT)
			.cuisine("Kenyan")
			.dietaryInformation("Vegetarian, Vegan")
			.keywords(List.of("Samosa", "Kenyan", "snack", "vegetarian", "vegan"))
			.build();

	private final List<Recipe> recipes = List.of(recipe, recipe2, recipe3, recipe4, recipe5, recipe6);

	@Mock
	private RecipeRepository recipeRepository;

	@InjectMocks
	private  RecipeServiceImpl recipeService;

	@Test
	void TestGetAllRecipes() {
		when(recipeRepository.findAll()).thenReturn(recipes);

		List<Recipe> recipeList = recipeService.getAllRecipes();
		assertEquals(6, recipeList.size());
		assertEquals(recipe, recipeList.getFirst());
		assertEquals(recipe2, recipeList.get(1));
		assertEquals(recipe3, recipeList.get(2));
		assertEquals(recipe4, recipeList.get(3));
		assertEquals(recipe5, recipeList.get(4));
		assertEquals(recipe6, recipeList.get(5));
	}

	@Test
	void testGetRecipeById() {
		when(recipeRepository.findById("662acdc633a8cc022a8a23ad")).thenReturn(Optional.of(recipe));

		Recipe fetchedRecipe = recipeService.getRecipeById("662acdc633a8cc022a8a23ad");
		assertEquals("Nyama Choma", fetchedRecipe.getTitle());
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


	@Test
	void testSearchRecipesByTitle() {
		List<Recipe> recipes = List.of(recipe5, recipe6);
		when(recipeRepository.findByTitleContaining("Kenyan")).thenReturn(recipes);

		List<Recipe> recipeList = recipeService.searchRecipesByTitle("Kenyan");
		assertEquals(2, recipeList.size());
		assertEquals(recipe5, recipeList.getFirst());
		assertEquals(recipe6, recipeList.get(1));
	}

	@Test
	void testSearchRecipesByKeywords() {
		when(recipeRepository.findAll()).thenReturn(recipes);

		List<Recipe> recipeList = recipeService.searchRecipesByKeywords(List.of("vegetarian"));
		assertEquals(3, recipeList.size());
		assertEquals(recipe2, recipeList.getFirst());
		assertEquals(recipe3, recipeList.get(1));
		assertEquals(recipe6, recipeList.get(2));
	}

	@Test
	void testFilterRecipesByCuisine() {
		when(recipeRepository.findAll()).thenReturn(recipes);

		List<Recipe> recipeList = recipeService.filterRecipesByCuisine("kenyan");
		assertEquals(6, recipeList.size());
		assertEquals(recipe, recipeList.getFirst());
		assertEquals(recipe2, recipeList.get(1));
		assertEquals(recipe3, recipeList.get(2));
		assertEquals(recipe4, recipeList.get(3));
		assertEquals(recipe5, recipeList.get(4));
		assertEquals(recipe6, recipeList.get(5));
	}

	@Test
	void testFilterRecipesByDiet() {
		when(recipeRepository.findAll()).thenReturn(recipes);

		List<Recipe> recipeList = recipeService.filterRecipesByDiet("vegetarian");
		assertEquals(2, recipeList.size());
		assertEquals(recipe2, recipeList.getFirst());
		assertEquals(recipe3, recipeList.get(1));
	}

	@Test
	void testFilterRecipesByDifficulty() {
		when(recipeRepository.findAll()).thenReturn(recipes);

		List<Recipe> recipeList = recipeService.filterRecipesByDifficulty(DifficultyLevel.EASY);
		assertEquals(2, recipeList.size());
		assertEquals(recipe, recipeList.getFirst());
		assertEquals(recipe2, recipeList.get(1));

		List<Recipe> recipeLis2 = recipeService.filterRecipesByDifficulty(DifficultyLevel.MEDIUM);
		assertEquals(2, recipeLis2.size());
		assertEquals(recipe3, recipeLis2.getFirst());
		assertEquals(recipe4, recipeLis2.get(1));

		List<Recipe> recipeList3 = recipeService.filterRecipesByDifficulty(DifficultyLevel.DIFFICULT);
		assertEquals(2, recipeList3.size());
		assertEquals(recipe5, recipeList3.getFirst());
		assertEquals(recipe6, recipeList3.get(1));
	}
}
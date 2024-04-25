package com.makechi.tastyAPI.controller;

import com.makechi.tastyAPI.entity.Recipe;
import com.makechi.tastyAPI.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RecipeControllerTest {

	private final Recipe recipe = Recipe.builder()
			.recipeID("662a8e071263130bc462b459")
			.title("Test Recipe")
			.build();

	private final Recipe recipe2 = Recipe.builder()
			.recipeID("662aa6e4698dc630e2f030e4")
			.title("Test Recipe 2")
			.build();

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RecipeService recipeService;

	@Test
	void testGetAllRecipes() throws Exception {

		List<Recipe> recipes = List.of(recipe, recipe2);

		when(recipeService.getAllRecipes()).thenReturn(recipes);
		mockMvc.perform(get("/api/v1/recipes"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].title").value(recipe.getTitle()))
				.andExpect(jsonPath("$[1].title").value(recipe2.getTitle()));
	}

	@Test
	void testGetRecipeById() throws Exception {
		when(recipeService.getRecipeById("662a8e071263130bc462b459")).thenReturn(recipe);
		mockMvc.perform(get("/api/v1/recipes/662a8e071263130bc462b459"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.title").value(recipe.getTitle()));
	}

	@Test
	void testAddRecipe() throws Exception {
		when(recipeService.addRecipe(any(Recipe.class))).thenReturn(recipe);
		mockMvc.perform(post("/api/v1/recipes")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"title\": \"Test Recipe\"}"))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.title").value(recipe.getTitle()));
	}

	@Test
	void updateRecipe() {

	}

	@Test
	void testDeleteRecipeById() {

	}
}
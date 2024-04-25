package com.makechi.tastyAPI.entity;

import com.makechi.tastyAPI.constants.DifficultyLevel;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document(collection = "recipes")
public class Recipe {
	@Id
	private String recipeID;
	private String title;
	private List<String> ingredients;
	private List<String> instructions;
	private int preparationTime;
	private int cookingTime;
	private int servings;
	private DifficultyLevel difficultyLevel;
	private String cuisine;
	private String dietaryInformation;
	private List<String> nutritionalInformation;
	private List<String> keywords;
}

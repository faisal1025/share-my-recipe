package com.airtribe.ShareMyRecipe.dto.recipe;

import com.airtribe.ShareMyRecipe.dto.chef.response.ChefWithoutRecipeDto;
import com.airtribe.ShareMyRecipe.entity.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class RecipeDto {
    private Long recipeId;

    private String title;

    private String summary;

    private List<String> steps;

    private List<Ingredient> ingredients = new ArrayList<>();

    private String description;

    private String featuredImageUrl;

    private List<String> imageUrls;

    private ChefWithoutRecipeDto chef;
}

package com.airtribe.ShareMyRecipe.dto.recipe.response;

import com.airtribe.ShareMyRecipe.dto.chef.response.ChefWithoutRecipeDto;
import com.airtribe.ShareMyRecipe.entity.Ingredient;
import com.airtribe.ShareMyRecipe.entity.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeDto {
    private Long recipeId;

    private String title;

    private String summary;

    private List<String> steps;

    private List<Ingredient> ingredients = new ArrayList<>();

    private String featuredImageUrl;

    private List<String> imageUrls = new ArrayList<>();

    private ChefWithoutRecipeDto chef;

    public RecipeDto() {
    }

    public RecipeDto(Long recipeId, String title, String summary, List<String> steps, List<Ingredient> ingredients, String featuredImageUrl, List<String> imageUrls, ChefWithoutRecipeDto chef) {
        this.recipeId = recipeId;
        this.title = title;
        this.summary = summary;
        this.steps = steps;
        this.ingredients = ingredients;
        this.featuredImageUrl = featuredImageUrl;
        this.imageUrls = imageUrls;
        this.chef = chef;
    }

    public RecipeDto(Long recipeId, String title, String summary, List<String> steps, List<Ingredient> ingredients, String featuredImageUrl, List<String> imageUrls) {
        this.recipeId = recipeId;
        this.title = title;
        this.summary = summary;
        this.steps = steps;
        this.ingredients = ingredients;
        this.featuredImageUrl = featuredImageUrl;
        this.imageUrls = imageUrls;
    }

    public RecipeDto(Recipe recipe) {
        this(recipe.getRecipeId(),
                recipe.getTitle(),
                recipe.getSummary(),
                recipe.getSteps(),
                recipe.getIngredients(),
                recipe.getFeaturedImageUrl(),
                recipe.getImageUrls(),
                new ChefWithoutRecipeDto(recipe.getChef()));
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getFeaturedImageUrl() {
        return featuredImageUrl;
    }

    public void setFeaturedImageUrl(String featuredImageUrl) {
        this.featuredImageUrl = featuredImageUrl;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public ChefWithoutRecipeDto getChef() {
        return chef;
    }

    public void setChef(ChefWithoutRecipeDto chef) {
        this.chef = chef;
    }
}

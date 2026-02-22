package com.airtribe.ShareMyRecipe.dto.recipe.request;

import com.airtribe.ShareMyRecipe.dto.chef.response.ChefWithoutRecipeDto;
import com.airtribe.ShareMyRecipe.entity.Ingredient;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecipeAddDto {

    private String title;

    private String summary;

    private List<String> steps;

    private List<Ingredient> ingredients = new ArrayList<>();

    private MultipartFile featuredImageUrl;

    private List<MultipartFile> imageUrls;

    public RecipeAddDto() {
    }

    public RecipeAddDto(String title, String summary, List<String> steps, List<Ingredient> ingredients, MultipartFile featuredImageUrl, List<MultipartFile> imageUrls) {
        this.title = title;
        this.summary = summary;
        this.steps = steps;
        this.ingredients = ingredients;
        this.featuredImageUrl = featuredImageUrl;
        this.imageUrls = imageUrls;
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

    public MultipartFile getFeaturedImageUrl() {
        return featuredImageUrl;
    }

    public void setFeaturedImageUrl(MultipartFile featuredImageUrl) {
        this.featuredImageUrl = featuredImageUrl;
    }

    public List<MultipartFile> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<MultipartFile> imageUrls) {
        this.imageUrls = imageUrls;
    }
}

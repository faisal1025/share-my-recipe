package com.airtribe.ShareMyRecipe.entity;

import com.airtribe.ShareMyRecipe.dto.recipe.request.RecipeAddDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;

    @NotNull
    private String title;

    private String summary;

    private List<String> steps;

    @ManyToMany
    private List<Ingredient> ingredients = new ArrayList<>();

    private String description;

    private String featuredImageUrl;

    @ElementCollection
    private List<String> imageUrls;

    @ManyToOne
    @JoinColumn(name = "chef_id")
    private Chef chef;

    public Recipe() {}

    public Recipe(Long recipeId, String title, String description, String featuredImageUrl, List<String> imageUrls) {
        this.recipeId = recipeId;
        this.title = title;
        this.description = description;
        this.featuredImageUrl = featuredImageUrl;
        this.imageUrls = imageUrls;
    }

    public Recipe(RecipeAddDto recipeAddDto) {
        this.title = recipeAddDto.getTitle();
        this.summary = recipeAddDto.getSummary();
        this.steps = recipeAddDto.getSteps();
        this.ingredients = recipeAddDto.getIngredients();
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

    public void setChef(Chef chef) {
        this.chef = chef;
    }

    public Chef getChef() {
        return chef;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

}

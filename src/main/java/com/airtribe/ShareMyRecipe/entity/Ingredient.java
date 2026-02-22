package com.airtribe.ShareMyRecipe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ingredientId;

    @NotNull
    private IngredientCategory category;

    @NotNull
    private String ingredientName;

    @ManyToMany(mappedBy = "ingredients")
    @JsonIgnore
    private List<Recipe> recipes = new ArrayList<>();

    public Ingredient() {}

}

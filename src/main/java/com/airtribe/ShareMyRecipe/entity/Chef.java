package com.airtribe.ShareMyRecipe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Chef extends AbstractUserBase {

    @NotNull(message = "Name is required")
    private String chefName;

    @Column(unique = true, nullable = false)
    private String chefHandle;

    @OneToMany(mappedBy = "chef", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recipe> recipes = new ArrayList<>();

    public Chef() {
        super();
    }

    public Chef(String chefName, String chefHandle, String email, String password, boolean isEnabled,List<Recipe> recipes) {
        super(email, password, false, Role.CHEF);
        this.chefName = chefName;
        this.chefHandle = chefHandle;
        this.recipes = recipes;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public void addRecipe(Recipe recipe){
        this.recipes.add(recipe);
    }

    public void addRecipies(List<Recipe> recipes){
        this.recipes.addAll(recipes);
    }

    public String getChefName() {
        return chefName;
    }

    public void setChefName(String chefName) {
        this.chefName = chefName;
    }

    public String getChefHandle() {
        return chefHandle;
    }

    public void setChefHandle(String chefHandle) {
        this.chefHandle = chefHandle;
    }

}

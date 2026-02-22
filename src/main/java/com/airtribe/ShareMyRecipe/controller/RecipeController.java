package com.airtribe.ShareMyRecipe.controller;


import com.airtribe.ShareMyRecipe.entity.Chef;
import com.airtribe.ShareMyRecipe.entity.Recipe;
import com.airtribe.ShareMyRecipe.exception.chef.ChefNotFoundException;
import com.airtribe.ShareMyRecipe.exception.RecipeNotFoundException;
import com.airtribe.ShareMyRecipe.service.RecipeManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecipeController {

    @Autowired
    private RecipeManagementService recipeManagementService;

    @PostMapping("/chef/{chefId}/recipes")
    public Chef createAndAssignRecipes(@PathVariable Long chefId, @RequestBody List<Recipe> recipes) throws ChefNotFoundException{
        return recipeManagementService.createAndAssignRecipesToChef(chefId, recipes);
    }



    @PostMapping("/chef/assign")
    public Recipe assignRecipeToChef(@RequestParam("chefId") Long chefId,
                                        @RequestParam("recipeId") Long recipeId) throws ChefNotFoundException, RecipeNotFoundException {
        return recipeManagementService.assignRecipeToChef(chefId, recipeId);

    }

    @GetMapping("/chef/{chefId}/recipe")
    public List<Recipe> getRecipe(@PathVariable Long chefId) {
        return recipeManagementService.getRecipeByChefId(chefId);
    }

    @ExceptionHandler(ChefNotFoundException.class)
    public ResponseEntity<String> handleChefNotFoundException(ChefNotFoundException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<String> handleRecipeNotFoundException(RecipeNotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

}

package com.airtribe.ShareMyRecipe.repository;

import com.airtribe.ShareMyRecipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Recipe findByTitle(String title) ;
    boolean existsByRecipeIdAndChefEmail(Long id, String email);
}
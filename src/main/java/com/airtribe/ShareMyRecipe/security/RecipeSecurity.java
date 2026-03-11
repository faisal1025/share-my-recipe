package com.airtribe.ShareMyRecipe.security;

import com.airtribe.ShareMyRecipe.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class RecipeSecurity {

    @Autowired
    private RecipeRepository _recipeRepository;

    public boolean isOwner(Long recipeId) {
        Authentication auth = SecurityContextHolder.getContext()
                                    .getAuthentication();
        String username = auth.getName();
//        return _recipeRepository.existByIdAndChefEmail(recipeId, username);
        return _recipeRepository.findById(recipeId)
                .filter(recipe -> recipe.getChef().getEmail().equals(username))
                .isPresent();

    }
}

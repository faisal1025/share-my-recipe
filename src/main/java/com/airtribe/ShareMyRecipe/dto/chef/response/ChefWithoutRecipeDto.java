package com.airtribe.ShareMyRecipe.dto.chef.response;

import com.airtribe.ShareMyRecipe.dto.UserBaseResponseDto;
import com.airtribe.ShareMyRecipe.entity.Chef;
import com.airtribe.ShareMyRecipe.entity.Recipe;
import com.airtribe.ShareMyRecipe.entity.Role;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChefWithoutRecipeDto extends UserBaseResponseDto {

    private String chefName;

    private String chefHandle;

    public ChefWithoutRecipeDto() {}

    public ChefWithoutRecipeDto(Long chefId, String chefName, String chefHandle, String email, List<Recipe> recipes, boolean isEnabled, Role role) {
        super(chefId, email, isEnabled, role);
        this.chefName = chefName;
        this.chefHandle = chefHandle;
    }

    public ChefWithoutRecipeDto(Long chefId, String chefName, String chefHandle, String email, boolean isEnabled, Role role, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(chefId, email, isEnabled, role, createdAt, updatedAt);
        this.chefName = chefName;
        this.chefHandle = chefHandle;
    }

    public ChefWithoutRecipeDto(Chef chef) {
        this(chef.getUserId(), chef.getChefName(), chef.getChefHandle(), chef.getEmail(), chef.isEnabled(), chef.getRole(), chef.getCreatedAt(), chef.getUpdatedAt());
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

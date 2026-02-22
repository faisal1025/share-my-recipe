package com.airtribe.ShareMyRecipe.dto.chef.response;

import com.airtribe.ShareMyRecipe.dto.UserBaseResponseDto;
import com.airtribe.ShareMyRecipe.entity.Recipe;
import com.airtribe.ShareMyRecipe.entity.Role;

import java.util.ArrayList;
import java.util.List;

public class ChefDto extends UserBaseResponseDto {

    private String chefName;

    private String chefHandle;

    private List<Recipe> recipes;

    public ChefDto() {}

    public ChefDto(ChefBuilder builder) {
        super(builder.chefId, builder.email, builder.isEnabled, builder.role);
        this.chefName = builder.chefName;
        this.chefHandle = builder.chefHandle;
        this.recipes = builder.recipes;
    }

    public static class ChefBuilder{
        private Long chefId;
        private String chefName;
        private String chefHandle;
        private String email;
        private boolean isEnabled;
        private Role role = Role.CHEF;
        private List<Recipe> recipes = new ArrayList<>();

        public ChefBuilder setChefId(Long chefId) {
            this.chefId = chefId;
            return this;
        }

        public ChefBuilder setChefName(String chefName) {
            this.chefName = chefName;
            return this;
        }

        public ChefBuilder setChefHandle(String chefHandle) {
            this.chefHandle = chefHandle;
            return this;
        }

        public ChefBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public ChefBuilder setEnabled(boolean enabled) {
            isEnabled = enabled;
            return this;
        }

        public ChefBuilder setRole(Role role) {
            this.role = role;
            return this;
        }

        public ChefBuilder setRecipes(List<Recipe> recipes) {
            this.recipes = recipes;
            return this;
        }

        public ChefDto build() {
            return new ChefDto(this);
        }
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

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}

package com.airtribe.ShareMyRecipe.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum IngredientCategory {
    VEGETABLE("Vegetable"),
    SPICE("Spice"),
    DAIRY("Dairy"),
    MEAT("Meat"),
    GRAIN("Grain");

    private String displayName;

    private IngredientCategory(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return this.displayName;
    }

}

package com.airtribe.ShareMyRecipe.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    ADMIN("ADMIN"),
    CHEF("CHEF"),
    USER("USER");

    private String roleName;

    private Role(String roleName) {
        this.roleName = roleName;
    }

    @JsonValue
    public String getRoleName() {
        return this.roleName;
    }
}

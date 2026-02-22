package com.airtribe.ShareMyRecipe.dto.chef.request;

import jakarta.validation.constraints.Email;

public class ChefLoginDto {

    private String email;

    private String password;

    public ChefLoginDto() {
    }

    public ChefLoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

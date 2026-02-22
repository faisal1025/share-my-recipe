package com.airtribe.ShareMyRecipe.dto.chef.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class ChefRegistrationDto {
    @NotNull(message = "Chef Name is required")
    private String chefName;

    @NotNull(message = "Chef Handle is required")
    private String chefHandle;

    @Email(message = "Enter a valid email")
    private String email;

    @NotBlank(message = "Password is required")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters long and include upper, lower, digit, and special character"
    )
    private String password;

    public ChefRegistrationDto() {}

    public ChefRegistrationDto(String chefName, String chefHandle, String email, String password) {
        this.chefName = chefName;
        this.chefHandle = chefHandle;
        this.email = email;
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

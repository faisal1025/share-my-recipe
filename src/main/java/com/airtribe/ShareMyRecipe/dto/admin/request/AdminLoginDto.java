package com.airtribe.ShareMyRecipe.dto.admin.request;

public class AdminLoginDto {
    private String email;
    private String password;

    public AdminLoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AdminLoginDto() {
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

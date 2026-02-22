package com.airtribe.ShareMyRecipe.dto.user.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserRegistrationDto {
    @NotNull(message = "Full name is required")
    private String fullName;

    @Email(message = "Enter a valid result")
    @Column(unique = true, nullable = false)
    private String email;

    @NotNull
    @Size(min = 8, message = "Password should be minimum 8 length")
    private String password;

    public UserRegistrationDto() {}

    public UserRegistrationDto(UserBuilder builder) {
        this.fullName = builder.fullName;
        this.email = builder.email;
        this.password = builder.password;
    }

    public static class UserBuilder{
        private String fullName;
        private String email;
        private String password;

        public UserBuilder setFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }
        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }
        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }
        public UserRegistrationDto build(){
            return new UserRegistrationDto(this);
        }
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

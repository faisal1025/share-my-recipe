package com.airtribe.ShareMyRecipe.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "app_user")
@PrimaryKeyJoinColumn(name = "user_id")
public class User extends AbstractUserBase {
    @NotNull
    private String fullName;

    public User() {}

    public User(String fullName, String email, String password) {
        super(email, password, false, Role.USER);
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}

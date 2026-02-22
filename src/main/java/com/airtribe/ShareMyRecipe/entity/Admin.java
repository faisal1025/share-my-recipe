package com.airtribe.ShareMyRecipe.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Admin extends  AbstractUserBase{

    @NotNull
    private String fullName;

    public Admin() {}

    public Admin(String fullName, String email, String password) {
        super(email, password, false, Role.ADMIN);
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}

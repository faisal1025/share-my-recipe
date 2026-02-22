package com.airtribe.ShareMyRecipe.exception.chef;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ChefNotFoundException extends Exception{
    public ChefNotFoundException(String message) {
        super(message);
    }
}

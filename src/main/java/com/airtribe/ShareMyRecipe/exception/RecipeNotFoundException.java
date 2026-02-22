package com.airtribe.ShareMyRecipe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecipeNotFoundException extends Exception {
    public RecipeNotFoundException(String message) {
        super(message);
    }
}

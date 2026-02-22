package com.airtribe.ShareMyRecipe.exception.chef;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ChefAlreadyExistsException extends Exception {
    public ChefAlreadyExistsException(String message) {
        super(message);
    }
}

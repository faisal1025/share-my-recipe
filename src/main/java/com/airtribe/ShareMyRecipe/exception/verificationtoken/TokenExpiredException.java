package com.airtribe.ShareMyRecipe.exception.verificationtoken;

public class TokenExpiredException extends Exception {
    public TokenExpiredException(String message) {
        super(message);
    }
}

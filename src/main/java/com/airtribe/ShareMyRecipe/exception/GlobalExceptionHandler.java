package com.airtribe.ShareMyRecipe.exception;

import com.airtribe.ShareMyRecipe.dto.ValidationErrorResponse;
import com.airtribe.ShareMyRecipe.exception.admin.AdminAlreadyExistsException;
import com.airtribe.ShareMyRecipe.exception.chef.ChefAlreadyExistsException;
import com.airtribe.ShareMyRecipe.exception.user.UserAlreadyExistsException;
import com.airtribe.ShareMyRecipe.exception.verificationtoken.TokenExpiredException;
import com.airtribe.ShareMyRecipe.exception.verificationtoken.TokenNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ChefAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleChefAlreadyExists(ChefAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "status", HttpStatus.CONFLICT,
                "error", "Conflict",
                "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of(
                    "timestamp", LocalDateTime.now().toString(),
                    "status", HttpStatus.CONFLICT,
                    "error", "Conflict",
                    "message", ex.getMessage()
                ));
    }

    @ExceptionHandler(AdminAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleUserAlreadyExists(AdminAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of(
                    "timestamp", LocalDateTime.now().toString(),
                    "status", HttpStatus.CONFLICT,
                    "error", "Conflict",
                    "message", ex.getMessage()
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleMethodArgNotValidException(MethodArgumentNotValidException ex) {
        ValidationErrorResponse errorResponse = new ValidationErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setTimeStamp(LocalDateTime.now());

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errorResponse.addFieldErrors(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        ValidationErrorResponse errorResponse = new ValidationErrorResponse();
        errorResponse.setMessage(ex.getErrorMessage());
        errorResponse.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity(Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "status", HttpStatus.NOT_FOUND,
                "error", "Not Found",
                "message", ex.getMessage()
        ), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleTokenNotFoundException(TokenNotFoundException ex) {
        return new ResponseEntity<>(Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "status", HttpStatus.NOT_FOUND,
                "error", "Not Found",
                "message", ex.getMessage()
        ), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<Map<String, Object>> handleTokenNotFoundException(TokenExpiredException ex) {
        return new ResponseEntity<>(Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "status", HttpStatus.BAD_REQUEST.value(),
                "error", "Not Found",
                "message", ex.getMessage()
        ), HttpStatus.BAD_REQUEST);
    }
}

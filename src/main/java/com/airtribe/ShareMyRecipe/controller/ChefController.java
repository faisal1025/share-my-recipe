package com.airtribe.ShareMyRecipe.controller;


import com.airtribe.ShareMyRecipe.entity.Chef;
import com.airtribe.ShareMyRecipe.exception.chef.ChefNotFoundException;
import com.airtribe.ShareMyRecipe.service.ChefManagementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ChefController {

    @Autowired
    private ChefManagementService chefService;

    // Jackson -> used for serialization and deserialization of JSON
    @PostMapping("/chefs")
    public Chef createChef(@Valid @RequestBody Chef chef) {
        return chefService.createChef(chef);
    }

    @DeleteMapping("/chefs/{id}")
    public ResponseEntity<String> deleteChef(@PathVariable("id") Long chefId) throws ChefNotFoundException{

        Chef chef = chefService.getChefById(chefId);
        chefService.deleteChef(chef);
        return ResponseEntity.status(200).body("Chef with id " + chefId + " deleted successfully.");
    }

    @GetMapping("/chefs")
    public List<Chef> getAllChefs(@RequestParam(value = "id", required = false) Long id,
                                  @RequestParam(value = "name", required = false) String name) throws ChefNotFoundException {
        List<Chef> chefs = new ArrayList<>();
        if(id == null && name == null) {
            chefs.addAll(chefService.getAllChefs());
        }else if(id != null && name == null) {
            chefs.add(chefService.getChefById(id));
        }else {
            chefs.addAll(chefService.getChefByName(Character.toTitleCase(name.charAt(0))+name.substring(1)));
        }
        return chefs;
    }

    @ExceptionHandler(ChefNotFoundException.class)
    public ResponseEntity<String> handleChefNotFoundException(ChefNotFoundException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException e){
        return ResponseEntity.status(400).body("Validation Error: " + e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }
}

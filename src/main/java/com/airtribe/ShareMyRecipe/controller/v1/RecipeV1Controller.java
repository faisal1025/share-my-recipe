package com.airtribe.ShareMyRecipe.controller.v1;

import java.io.IOException;
import java.util.*;
import com.airtribe.ShareMyRecipe.dto.recipe.response.RecipeDto;
import com.airtribe.ShareMyRecipe.dto.recipe.request.RecipeAddDto;
import com.airtribe.ShareMyRecipe.entity.Recipe;
import com.airtribe.ShareMyRecipe.exception.RecipeNotFoundException;
import com.airtribe.ShareMyRecipe.exception.chef.ChefNotFoundException;
import com.airtribe.ShareMyRecipe.service.RecipeManagementService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.annotation.security.RolesAllowed;
import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
public class RecipeV1Controller {

    @Autowired
    private RecipeManagementService _recipeManagementService;

    @PreAuthorize("hasRole('CHEF') or hasRole('ADMIN')")
    @PostMapping(value = "/chef/{chefId}/recipes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<RecipeDto>> createAndAssignRecipes(
            @PathVariable("chefId") Long chefId,
            @RequestPart("recipes") List<RecipeAddDto> recipesAddDto,
            @RequestPart(value = "featuredImage", required = false)
            List<MultipartFile> featuredImages) throws ChefNotFoundException, IOException {

        if(featuredImages != null){
            if(recipesAddDto.size() == featuredImages.size()){
                for(int i = 0; i < recipesAddDto.size(); i++){
                    recipesAddDto.get(i).setFeaturedImageUrl(featuredImages.get(i));
                }
            }
            else{
                throw new IllegalArgumentException("Recipes and images count mismatch");
            }
        }
        List<RecipeDto> recipeResponse = _recipeManagementService.createAndAssignRecipes(chefId, recipesAddDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeResponse);
    }

    @PreAuthorize("hasRole('CHEF') or hasRole('ADMIN')")
    @PostMapping(value = "/chef/{chefId}/recipes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RecipeDto>> createAndAssignRecipes(
            @PathVariable("chefId") Long chefId,
            @RequestBody List<RecipeAddDto> recipesAddDto) throws ChefNotFoundException, IOException {

        List<RecipeDto> recipeResponse = _recipeManagementService.createAndAssignRecipes(chefId, recipesAddDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeResponse);
    }

    @PreAuthorize("hasRole('CHEF') and @recipeSecurity.isOwner(#recipeId)")
    @PostMapping(value = "/recipe/{recipeId}/featured-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RecipeDto> uploadFeaturedImage(
            @PathVariable("recipeId") Long recipeId,
            @RequestPart("featuredImage") MultipartFile featuredImage) throws IOException, RecipeNotFoundException {

        RecipeDto recipe =  _recipeManagementService.addFeaturedImageToRecipe(recipeId, featuredImage);
        return ResponseEntity.ok(recipe);
    }

    @PreAuthorize("hasRole('CHEF') and @recipeSecurity.isOwner(#recipeId)")
    @PostMapping(value = "/recipe/{recipeId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RecipeDto> uploadRecipeImages(@PathVariable("recipeId") Long recipeId,
                                        @RequestParam("images") List<MultipartFile> recipeImages)
            throws RecipeNotFoundException, IOException{

        RecipeDto recipeDto = _recipeManagementService.addRecipeImages(recipeId, recipeImages);
        return ResponseEntity.ok(recipeDto);
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('CHEF') and @recipeSecurity.isOwner(#recipeId))")
    @DeleteMapping("/recipe/{recipeId}")
    public ResponseEntity<?> deleteRecipe(@PathVariable Long recipeId) {
        return ResponseEntity.status(200).body("Deleted Successfully");
    }

}

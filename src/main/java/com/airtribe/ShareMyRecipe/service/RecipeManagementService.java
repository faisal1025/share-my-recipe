package com.airtribe.ShareMyRecipe.service;

import com.airtribe.ShareMyRecipe.dto.recipe.response.RecipeDto;
import com.airtribe.ShareMyRecipe.dto.recipe.request.RecipeAddDto;
import com.airtribe.ShareMyRecipe.entity.Chef;
import com.airtribe.ShareMyRecipe.entity.Recipe;
import com.airtribe.ShareMyRecipe.exception.chef.ChefNotFoundException;
import com.airtribe.ShareMyRecipe.exception.RecipeNotFoundException;
import com.airtribe.ShareMyRecipe.repository.ChefRepository;
import com.airtribe.ShareMyRecipe.repository.RecipeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeManagementService {

    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private ChefRepository chefRepository;
    @Autowired
    private FileStorageService fileStorageService;

    public Recipe createRecipe(Long chefId, Recipe recipe) throws  ChefNotFoundException {
        Optional<Chef> optionalChef = chefRepository.findById(chefId);
        if (optionalChef.isEmpty()) {
            throw new ChefNotFoundException("Chef not found with id: " + chefId);
        }
        Chef chef = optionalChef.get();
        recipe.setChef(chef);
        return recipeRepository.save(recipe);
    }

    public Recipe assignRecipeToChef(long chefId, long recipeId) throws ChefNotFoundException, RecipeNotFoundException {
        Optional<Chef> chef = chefRepository.findById(chefId);
        if (chef.isEmpty()) {
            throw new ChefNotFoundException("Chef not found with id: " + chefId);
        }
        Optional<Recipe> recipe = recipeRepository.findById(recipeId);
        if (recipe.isEmpty()) {
            throw new RecipeNotFoundException("Recipe not found with id: " + recipeId);
        }
        recipe.get().setChef(chef.get());
        recipeRepository.save(recipe.get());
        return recipe.get();
    }

    @Transactional
    public RecipeDto addRecipeImages(Long recipeId, List<MultipartFile> recipeImages)
        throws RecipeNotFoundException, IOException{

        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        if(optionalRecipe.isEmpty())
            throw new RecipeNotFoundException("Recipe not exists with id: "+recipeId);

        Recipe recipe = optionalRecipe.get();

        List<String> imageUrls = recipeImages.parallelStream().map(imageFile -> {
            try{
                String prefix = recipe.getRecipeId().toString()+"/images";
                String imageUrl = fileStorageService.uploadFile(imageFile, prefix);
                return imageUrl;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());

        recipe.setImageUrls(imageUrls);
        Recipe savedRecipe = recipeRepository.save(recipe);

        return new RecipeDto(savedRecipe.getRecipeId(), savedRecipe.getTitle(), savedRecipe.getSummary(),
                savedRecipe.getSteps(), savedRecipe.getIngredients(), savedRecipe.getFeaturedImageUrl(),
                savedRecipe.getImageUrls());
    }

    @Transactional
    public RecipeDto addFeaturedImageToRecipe(Long recipeId, MultipartFile featuredImage)
            throws RecipeNotFoundException, IOException{
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        if(optionalRecipe.isEmpty()) throw new RecipeNotFoundException("recipe with recipe id not exist: "+recipeId);

        Recipe recipe = optionalRecipe.get();
        String featuredImageUrl = fileStorageService.uploadFile(featuredImage, recipe.getRecipeId().toString());
        recipe.setFeaturedImageUrl(featuredImageUrl);
        Recipe savedRecipe = recipeRepository.save(recipe);
        return new RecipeDto(savedRecipe.getRecipeId(), savedRecipe.getTitle(), savedRecipe.getSummary(),
                savedRecipe.getSteps(), savedRecipe.getIngredients(), savedRecipe.getFeaturedImageUrl(),
                savedRecipe.getImageUrls());
    }

    @Transactional
    public List<RecipeDto> createAndAssignRecipes(Long chefId, List<RecipeAddDto> recipeAddDtos)
            throws IOException, ChefNotFoundException {

        Optional<Chef> optionalChef = chefRepository.findById(chefId);
        if(optionalChef.isEmpty()){
            throw new ChefNotFoundException("Chef not exist with chef id: "+ chefId);
        }

        Chef chef = optionalChef.get();
        List<Recipe> savedRecipes = recipeAddDtos.parallelStream().map(recipeInput -> {
            Recipe recipe = recipeRepository.save(new Recipe(recipeInput));
            try {
                if(recipeInput.getFeaturedImageUrl() != null){
                    String featuredImage = fileStorageService.uploadFile(recipeInput.getFeaturedImageUrl(), recipe.getRecipeId().toString());
                    recipe.setFeaturedImageUrl(featuredImage);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (NullPointerException e){
                throw new NullPointerException("featuredImage is Empty: "+e);
            }
            recipe.setChef(chef);
            return recipe;
        }).toList();
        List<RecipeDto> recipeResponse = savedRecipes.parallelStream().map(RecipeDto::new).toList();
        return recipeResponse;
    }

    @Transactional
    public Chef createAndAssignRecipesToChef(Long chefId, List<Recipe> recipes) throws ChefNotFoundException {
        Optional<Chef> chef = chefRepository.findById(chefId);
        if (chef.isEmpty()) {
            throw new ChefNotFoundException("Chef not found with id: " + chefId);
        }
        for(Recipe recipe: recipes){
            recipe.setChef(chef.get());
        }
        chef.get().getRecipes().addAll(recipes);
        return chefRepository.save(chef.get());
    }

    public List<Recipe> getRecipeByChefId(Long chefId) {
        Optional<Chef> optionalChef = chefRepository.findById(chefId);
        if (optionalChef.isEmpty()) {
            return List.of();
        }
        Chef chef = optionalChef.get();
        return chef.getRecipes();
    }
}

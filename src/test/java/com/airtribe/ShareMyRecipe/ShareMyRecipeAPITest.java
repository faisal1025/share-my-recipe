package com.airtribe.ShareMyRecipe;

import com.airtribe.ShareMyRecipe.dto.chef.request.ChefLoginDto;
import com.airtribe.ShareMyRecipe.entity.AbstractUserBase;
import com.airtribe.ShareMyRecipe.entity.Chef;
import com.airtribe.ShareMyRecipe.entity.Recipe;
import com.airtribe.ShareMyRecipe.entity.Role;
import com.airtribe.ShareMyRecipe.repository.ChefRepository;
import com.airtribe.ShareMyRecipe.repository.RecipeRepository;
import com.airtribe.ShareMyRecipe.service.ChefManagementService;
import com.airtribe.ShareMyRecipe.service.emailservice.EmailServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ShareMyRecipeAPITest {

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ChefRepository _chefRepository;

    @Autowired
    private RecipeRepository _recipeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ChefManagementService chefManagementService;

    @AfterEach
    public void cleanUp(){
        _chefRepository.deleteAll();
        _recipeRepository.deleteAll();
    }

    @Test
    public void createRecipe() throws Exception {
        // Arrange
        Chef chef = new Chef();
        chef.setChefName("Jhon Doe");
        chef.setChefHandle("jhon@12");
        chef.setEmail("jhon@domain.com");
        chef.setRecipes(null);
        chef.setPassword(passwordEncoder.encode("faiSAL@12"));
        chef.setRole(Role.CHEF);
        chef.setEnabled(true);
        Chef savedChef = _chefRepository.save(chef);
        String token = chefManagementService.login(new ChefLoginDto("jhon@domain.com", "faiSAL@12"));
        // Act & Assert
        String contentBody = "[{\"title\": \"Jalebi\", \"summary\": \"This is a delicious sweet\"}]";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/chef/{chefId}/recipes", savedChef.getUserId())
                        .header("Authorization", "bearer "+token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(contentBody)).andExpect(status().isCreated());

    }

    @Test
    public void testSendEmail() {
        emailService.sendEmail(new String[] {"faisalprofessional1@gmail.com"},
                "This is the integration email", "Testing the send email");
    }

//    @Test
//    public void fetchRecipe() throws Exception {
//        // Arrange
//        Chef chef = new Chef();
//        chef.setChefName("Test");
//        chef.setChefHandle("test@121");
//        chef.setEmail("test@domain.com");
//        Chef savedChef = _chefRepository.save(chef);
//        Recipe recipe = new Recipe();
//        recipe.setTitle("Gulab Jamun");
//        recipe.setChef(chef);
//        recipe.setDescription("gulab jamun is delicious");
//        recipe.setFeaturedImageUrl("https://featured-image/gulab-jamun");
//        Recipe savecRecipe = _recipeRepository.save(recipe);
//        // Act
//        mockMvc.perform(MockMvcRequestBuilders.get("/chef/{chefId}/recipe", savedChef.getChefId()))
//                .andExpect(status().is2xxSuccessful())
//                .andDo(print());
//    }
}

package com.airtribe.ShareMyRecipe.repository;

import com.airtribe.ShareMyRecipe.entity.Chef;
import com.airtribe.ShareMyRecipe.entity.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class ChefRepositoryTest {

    @Autowired
    private ChefRepository _chefRepository;

    private Chef chef;


    @BeforeEach
    public void setup() {
        chef = new Chef();
        chef.setChefName("Faisal Azmi");
        chef.setChefHandle("faisal@12");
        chef.setEmail("faisal@domain.com");
        chef.setPassword("faiSAL@12");
        chef.setRole(Role.CHEF);
        chef.setRecipes(null);
    }

    @Test
    public void saveChefTest() {
        // Act
        Chef savedChef = _chefRepository.saveAndFlush(chef); // it will trigger the bean validation check
        // Assert
        Assertions.assertNotNull(savedChef);
        Assertions.assertNotNull(savedChef.getUserId());
        Assertions.assertEquals("faisal@domain.com", savedChef.getEmail());
        Assertions.assertEquals("Faisal Azmi", savedChef.getChefName());
    }

    @Test
    public void fetchChefByIdTest() {
        // Arrange
        Chef savedChef = _chefRepository.save(chef);
        // Act
        Optional<Chef> fetchedChef = _chefRepository.findById(savedChef.getUserId());
        // Assert
        Assertions.assertTrue(fetchedChef.isPresent());
        Assertions.assertEquals(savedChef.getUserId(), fetchedChef.get().getUserId());
    }
}

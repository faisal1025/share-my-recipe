package com.airtribe.ShareMyRecipe.service;

import com.airtribe.ShareMyRecipe.entity.Chef;
import com.airtribe.ShareMyRecipe.entity.Role;
import com.airtribe.ShareMyRecipe.exception.chef.ChefNotFoundException;
import com.airtribe.ShareMyRecipe.repository.ChefRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChefManagementServiceTest {

    @InjectMocks
    private ChefManagementService _chefManagementService;

    @Mock
    private ChefRepository _chefRepository;

    private Chef chef;

    @BeforeEach
    public void setup() {
        chef = new Chef();
        chef.setChefName("Jhon Doe");
        chef.setUserId(1L);
        chef.setChefHandle("jhon@12");
        chef.setEmail("jhon@domain.com");
        chef.setRecipes(null);
        chef.setPassword("faiSAL@12");
        chef.setRole(Role.CHEF);
    }

    @Test
    public void testCreateChef() {
        when(_chefRepository
                .save(chef)).thenReturn(chef);
        // Act
        Chef resultChef = _chefManagementService.createChef(chef);
        // Assert
        assertEquals(chef.getChefName(), resultChef.getChefName());
        assertEquals(chef.getEmail(), resultChef.getEmail());
        verify(_chefRepository, times(1)).save(chef);
    }

    @Test
    public void testFetchChef_successful() {
        // Arrange
        when(_chefRepository.findById(1L)).thenReturn(Optional.of(chef));
        // Act
        Chef resultChef = null;
        try{
            resultChef = _chefManagementService.getChefById(1L);
        }catch (ChefNotFoundException e){
            System.out.println(e.getMessage());
        }
        // Assert
        Assertions.assertNotNull(resultChef);
        assertEquals(1L, resultChef.getUserId());
        assertEquals("Jhon Doe", resultChef.getChefName());
        assertEquals("jhon@domain.com", resultChef.getEmail());
        verify(_chefRepository, times(2)).findById(1L);
    }

    @Test
    public void testFetchChef_unsuccessful() {
        // Arrange
        when(_chefRepository.findById(1L)).thenReturn(Optional.empty());
        // Act
        ChefNotFoundException exception = Assertions.assertThrowsExactly(ChefNotFoundException.class, () -> {
            _chefManagementService.getChefById(1L);
        });
        assertEquals("Chef not found with id: 1", exception.getMessage());
        verify(_chefRepository, times(1)).findById(1L);
    }
}

package com.airtribe.ShareMyRecipe.controller;


import com.airtribe.ShareMyRecipe.entity.Chef;
import com.airtribe.ShareMyRecipe.service.ChefManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ChefControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChefManagementService _chefManagementService;

    List<Chef> chefs;

    @BeforeEach
    public void setup() {
        chefs = new ArrayList<>();
        Chef chef = new Chef("Faisal", "faisal@12", "faisal@domain.com", "password", false, null);
        chef.setUserId(1L);
        chefs.add(chef);
    }

//    @Test
//    public void getAllChefsTest() throws Exception {
//        // Arrange
//        when(_chefManagementService.getAllChefs()).thenReturn(chefs);
//        // Act
//        mockMvc.perform(MockMvcRequestBuilders.get("/chefs")).
//                andExpect(status().isOk()).andDo(print()).
//                andExpect(jsonPath("$[0].chefName").value("Faisal")).
//                andExpect(jsonPath("$[0].email").value("faisal@domain.com"));
//    }

}

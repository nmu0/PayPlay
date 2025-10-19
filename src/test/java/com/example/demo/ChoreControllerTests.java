package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ChoreController.class)
public class ChoreControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetChores() throws Exception {
        mockMvc.perform(get("/api/chores"))
               .andExpect(status().isOk());
    }

    @Test
    void testGetChoreById() throws Exception {
        mockMvc.perform(get("/api/chores/1"))
               .andExpect(status().isOk());
    }
}
package com.authorization.controller;

import com.authorization.model.Merchant;
import com.authorization.service.MerchantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MerchantControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MerchantService merchantService;

    @InjectMocks
    private MerchantController merchantController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(merchantController).build();
    }

    @Test
    public void testCreateMerchant() throws Exception {
        Merchant merchant = new Merchant();
        merchant.setId(1L);
        merchant.setName("Test Merchant");

        when(merchantService.createMerchant(any(Merchant.class))).thenReturn(merchant);

        mockMvc.perform(post("/merchants")
                        .contentType("application/json")
                        .content("{\"name\":\"Test Merchant\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Merchant"));

        verify(merchantService, times(1)).createMerchant(any(Merchant.class));
    }

    @Test
    public void testGetAllMerchants() throws Exception {
        Merchant merchant1 = new Merchant();
        Merchant merchant2 = new Merchant();
        when(merchantService.getAllMerchants()).thenReturn(Arrays.asList(merchant1, merchant2));

        mockMvc.perform(get("/merchants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));

        verify(merchantService, times(1)).getAllMerchants();
    }
}

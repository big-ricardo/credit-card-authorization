package com.authorization.controller;

import com.authorization.dto.CreditDTO;
import com.authorization.exceptions.GlobalExceptionHandler;
import com.authorization.model.Account;
import com.authorization.model.Category;
import com.authorization.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
        mockMvc = MockMvcBuilders.standaloneSetup(accountController)
                .setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    public void testCreateAccount() throws Exception {
        Account account = new Account();
        account.setId(1L);
        account.setFoodBalance(100.0);
        account.setMealBalance(50.0);
        account.setCashBalance(200.0);

        when(accountService.createAccount(any(Account.class))).thenReturn(account);

        mockMvc.perform(post("/accounts")
                        .contentType("application/json")
                        .content("{\"foodBalance\":100.0,\"mealBalance\":50.0,\"cashBalance\":200.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.foodBalance").value(100.0))
                .andExpect(jsonPath("$.mealBalance").value(50.0))
                .andExpect(jsonPath("$.cashBalance").value(200.0));

        verify(accountService, times(1)).createAccount(any(Account.class));
    }

    @Test
    public void testAddCreditByCategory() throws Exception {
        Long accountId = 1L;
        CreditDTO creditDTO = new CreditDTO(50.0, "food");

        Account account = new Account();
        account.setId(accountId);
        account.setFoodBalance(100.0);
        account.setMealBalance(50.0);
        account.setCashBalance(200.0);

        Account newAccount = new Account(account.getId(), account.getFoodBalance() + creditDTO.getAmount(), account.getMealBalance(), account.getCashBalance());


        when(accountService.getById(accountId)).thenReturn(Optional.of(account));
        when(accountService.addCreditByCategory(account, creditDTO.getCategory(), creditDTO.getAmount())).thenReturn(newAccount);

        mockMvc.perform(post("/accounts/" + accountId + "/credit")
                        .contentType("application/json")
                        .content("{\"category\":\"food\",\"amount\":50.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(accountId))
                .andExpect(jsonPath("$.foodBalance").value(150.0));

        verify(accountService, times(1)).getById(accountId);
        verify(accountService, times(1)).addCreditByCategory(account, creditDTO.getCategory(), creditDTO.getAmount());
    }

    @Test
    public void testAddCreditByCategoryAccountNotFound() throws Exception {
        Long accountId = 1L;

        when(accountService.getById(accountId)).thenReturn(Optional.empty());

        mockMvc.perform(post("/accounts/" + accountId + "/credit")
                        .contentType("application/json")
                        .content("{\"category\":\"FOOD\",\"amount\":50.0}"))
                .andExpect(status().isNotFound())
                .andExpect(result -> {
                    assertEquals("Account not found", Objects.requireNonNull(result.getResolvedException()).getMessage());
                });

        verify(accountService, times(1)).getById(accountId);
        verify(accountService, never()).addCreditByCategory(any(Account.class), any(Category.class), anyDouble());
    }

    @Test
    public void testGetAllAccounts() throws Exception {
        Account account1 = new Account();
        Account account2 = new Account();
        when(accountService.getAllAccounts()).thenReturn(Arrays.asList(account1, account2));

        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));

        verify(accountService, times(1)).getAllAccounts();
    }
}

package com.authorization.controller;

import com.authorization.exceptions.InsufficientBalanceException;
import com.authorization.model.Account;
import com.authorization.model.Transaction;
import com.authorization.service.AccountService;
import com.authorization.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class TransactionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TransactionService transactionService;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    public void testAuthorizeSuccess() throws Exception {
        String accountId = "1";
        Account account = new Account();
        Transaction transaction = new Transaction();
        transaction.setAccountId(accountId);
        transaction.setTotalAmount(50.0);

        when(accountService.getById(Long.parseLong(accountId))).thenReturn(Optional.of(account));
        doNothing().when(transactionService).authorize(any(Transaction.class), any(Account.class));
        when(transactionService.save(any(Transaction.class))).thenReturn(transaction);
        when(accountService.save(any(Account.class))).thenReturn(account);

        mockMvc.perform(post("/authorize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountId\":\"1\",\"totalAmount\":50.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorization").value("AUTHORIZED"));

        verify(accountService, times(1)).getById(Long.parseLong(accountId));
        verify(transactionService, times(1)).authorize(any(Transaction.class), any(Account.class));
        verify(transactionService, times(1)).save(any(Transaction.class));
        verify(accountService, times(1)).save(any(Account.class));
    }

    @Test
    public void testAuthorizeInsufficientFunds() throws Exception {
        String accountId = "1";
        Account account = new Account();
        Transaction transaction = new Transaction();
        transaction.setAccountId(accountId.toString());
        transaction.setTotalAmount(50.0);

        when(accountService.getById(Long.parseLong(accountId))).thenReturn(Optional.of(account));
        doThrow(new InsufficientBalanceException("Insufficient balance")).when(transactionService).authorize(any(Transaction.class), any(Account.class));

        mockMvc.perform(post("/authorize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountId\":\"1\",\"totalAmount\":50.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorization").value("INSUFFICIENT_FUNDS"));

        verify(accountService, times(1)).getById(Long.parseLong(accountId));
        verify(transactionService, times(1)).authorize(any(Transaction.class), any(Account.class));
    }

    @Test
    public void testAuthorizeAccountNotFound() throws Exception {
        String accountId = "1";
        Transaction transaction = new Transaction();
        transaction.setAccountId(accountId.toString());
        transaction.setTotalAmount(50.0);

        when(accountService.getById(Long.parseLong(accountId))).thenReturn(Optional.empty());

        mockMvc.perform(post("/authorize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountId\":\"1\",\"totalAmount\":50.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorization").value("NO_AUTHORIZED"));

        verify(accountService, times(1)).getById(Long.parseLong(accountId));
        verify(transactionService, never()).authorize(any(Transaction.class), any(Account.class));
    }

    @Test
    public void testAuthorizeException() throws Exception {
        String accountId = "1";
        Account account = new Account();
        Transaction transaction = new Transaction();
        transaction.setAccountId(accountId.toString());
        transaction.setTotalAmount(50.0);

        when(accountService.getById(Long.parseLong(accountId))).thenReturn(Optional.of(account));
        doThrow(new RuntimeException("General error")).when(transactionService).authorize(any(Transaction.class), any(Account.class));

        mockMvc.perform(post("/authorize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountId\":\"1\",\"totalAmount\":50.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorization").value("NO_AUTHORIZED"));

        verify(accountService, times(1)).getById(Long.parseLong(accountId));
        verify(transactionService, times(1)).authorize(any(Transaction.class), any(Account.class));
    }
}

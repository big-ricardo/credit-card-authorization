package com.authorization.service;

import com.authorization.exceptions.InsufficientBalanceException;
import com.authorization.model.Account;
import com.authorization.model.Category;
import com.authorization.model.Merchant;
import com.authorization.model.Transaction;
import com.authorization.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    @Mock
    private AccountService accountService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private MerchantService merchantService;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthorizeWithSufficientBalance() {
        // Arrange
        Transaction transaction = new Transaction();
        transaction.setMcc("1234");
        transaction.setMerchant("Test Merchant");
        transaction.setTotalAmount(100.0);

        Account account = new Account();
        account.setCashBalance(50.0);

        Merchant merchant = new Merchant();
        merchant.setName("Test Merchant");
        merchant.setMcc("1234");

        when(merchantService.getByName("Test Merchant")).thenReturn(merchant);
        when(categoryService.getCategoryByMcc("1234")).thenReturn(Category.FOOD);
        when(accountService.getBalanceByCategory(account, Category.FOOD)).thenReturn(150.0);

        // Act
        transactionService.authorize(transaction, account);

        // Assert
        verify(accountService).updateBalance(Category.FOOD, account, 100.0);
        verify(transactionRepository).save(transaction);
    }

    @Test
    public void testAuthorizeWithInsufficientBalanceAndSufficientCash() {
        // Arrange
        Transaction transaction = new Transaction();
        transaction.setMcc("1234");
        transaction.setMerchant("Test Merchant");
        transaction.setTotalAmount(100.0);

        Account account = new Account();
        account.setCashBalance(150.0);

        Merchant merchant = new Merchant();
        merchant.setName("Test Merchant");
        merchant.setMcc("1234");

        when(merchantService.getByName("Test Merchant")).thenReturn(merchant);
        when(categoryService.getCategoryByMcc("1234")).thenReturn(Category.FOOD);
        when(accountService.getBalanceByCategory(account, Category.FOOD)).thenReturn(50.0);

        // Act
        transactionService.authorize(transaction, account);

        // Assert
        verify(accountService).updateBalance(Category.CASH, account, 100.0);
        verify(transactionRepository).save(transaction);
    }

    @Test
    public void testAuthorizeWithInsufficientBalanceAndInsufficientCash() {
        // Arrange
        Transaction transaction = new Transaction();
        transaction.setMcc("1234");
        transaction.setMerchant("Test Merchant");
        transaction.setTotalAmount(100.0);

        Account account = new Account();
        account.setCashBalance(50.0);

        Merchant merchant = new Merchant();
        merchant.setName("Test Merchant");
        merchant.setMcc("1234");

        when(merchantService.getByName("Test Merchant")).thenReturn(merchant);
        when(categoryService.getCategoryByMcc("1234")).thenReturn(Category.FOOD);
        when(accountService.getBalanceByCategory(account, Category.FOOD)).thenReturn(50.0);

        // Act & Assert
        assertThrows(InsufficientBalanceException.class, () -> {
            transactionService.authorize(transaction, account);
        });
    }

    @Test
    public void testAuthorizeWhenGetBalanceThrowsException() {
        // Arrange
        Transaction transaction = new Transaction();
        transaction.setMcc("1234");
        transaction.setMerchant("Test Merchant");
        transaction.setTotalAmount(100.0);

        Account account = new Account();
        account.setCashBalance(150.0);

        Merchant merchant = new Merchant();
        merchant.setName("Test Merchant");
        merchant.setMcc("1234");

        when(merchantService.getByName("Test Merchant")).thenReturn(merchant);
        when(categoryService.getCategoryByMcc("1234")).thenReturn(Category.FOOD);
        when(accountService.getBalanceByCategory(account, Category.FOOD)).thenThrow(new RuntimeException("Balance error"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            transactionService.authorize(transaction, account);
        });

        assertEquals("Balance error", exception.getMessage());
    }

    @Test
    public void testAuthorizeWhenUpdateBalanceThrowsException() {
        // Arrange
        Transaction transaction = new Transaction();
        transaction.setMcc("1234");
        transaction.setMerchant("Test Merchant");
        transaction.setTotalAmount(100.0);

        Account account = new Account();
        account.setCashBalance(150.0);

        Merchant merchant = new Merchant();
        merchant.setName("Test Merchant");
        merchant.setMcc("1234");

        when(merchantService.getByName("Test Merchant")).thenReturn(merchant);
        when(categoryService.getCategoryByMcc("1234")).thenReturn(Category.FOOD);
        when(accountService.getBalanceByCategory(account, Category.FOOD)).thenReturn(150.0);
        doThrow(new RuntimeException("Update balance error")).when(accountService).updateBalance(any(Category.class), any(Account.class), anyDouble());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            transactionService.authorize(transaction, account);
        });

        assertEquals("Update balance error", exception.getMessage());
    }
}

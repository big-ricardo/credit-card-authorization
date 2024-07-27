package com.authorization.service;

import com.authorization.model.Account;
import com.authorization.model.Category;
import com.authorization.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAccount() {
        // Arrange
        Account account = new Account();
        when(accountRepository.save(account)).thenReturn(account);

        // Act
        Account createdAccount = accountService.createAccount(account);

        // Assert
        assertEquals(account, createdAccount);
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    public void testGetById() {
        // Arrange
        Long accountId = 1L;
        Account account = new Account();
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        // Act
        Optional<Account> foundAccount = accountService.getById(accountId);

        // Assert
        assertTrue(foundAccount.isPresent());
        assertEquals(account, foundAccount.get());
        verify(accountRepository, times(1)).findById(accountId);
    }

    @Test
    public void testAddCreditByCategory() {
        // Arrange
        Account account = new Account();
        account.setFoodBalance(100.0);
        account.setMealBalance(50.0);
        account.setCashBalance(200.0);
        when(accountRepository.save(account)).thenReturn(account);

        // Act
        Account updatedAccountFood = accountService.addCreditByCategory(account, Category.FOOD, 50.0);
        Account updatedAccountMeal = accountService.addCreditByCategory(account, Category.MEAL, 25.0);
        Account updatedAccountCash = accountService.addCreditByCategory(account, Category.CASH, 75.0);

        // Assert
        assertEquals(150.0, updatedAccountFood.getFoodBalance());
        assertEquals(75.0, updatedAccountMeal.getMealBalance());
        assertEquals(275.0, updatedAccountCash.getCashBalance());
        verify(accountRepository, times(3)).save(account);
    }

    @Test
    public void testGetAllAccounts() {
        // Arrange
        List<Account> accounts = List.of(new Account(), new Account());
        when(accountRepository.findAll()).thenReturn(accounts);

        // Act
        List<Account> allAccounts = accountService.getAllAccounts();

        // Assert
        assertEquals(accounts, allAccounts);
        verify(accountRepository, times(1)).findAll();
    }

    @Test
    public void testGetBalanceByCategory() {
        // Arrange
        Account account = new Account();
        account.setFoodBalance(100.0);
        account.setMealBalance(50.0);
        account.setCashBalance(200.0);

        // Act & Assert
        assertEquals(100.0, accountService.getBalanceByCategory(account, Category.FOOD));
        assertEquals(50.0, accountService.getBalanceByCategory(account, Category.MEAL));
        assertEquals(200.0, accountService.getBalanceByCategory(account, Category.CASH));
    }

    @Test
    public void testUpdateBalance() {
        // Arrange
        Account account = new Account();
        account.setFoodBalance(100.0);
        account.setMealBalance(50.0);
        account.setCashBalance(200.0);

        // Act
        accountService.updateBalance(Category.FOOD, account, 30.0);
        accountService.updateBalance(Category.MEAL, account, 20.0);
        accountService.updateBalance(Category.CASH, account, 50.0);

        // Assert
        assertEquals(70.0, account.getFoodBalance());
        assertEquals(30.0, account.getMealBalance());
        assertEquals(150.0, account.getCashBalance());
    }

    @Test
    public void testSaveAccount() {
        // Arrange
        Account account = new Account();
        when(accountRepository.save(account)).thenReturn(account);

        // Act
        Account savedAccount = accountService.save(account);

        // Assert
        assertEquals(account, savedAccount);
        verify(accountRepository, times(1)).save(account);
    }
}

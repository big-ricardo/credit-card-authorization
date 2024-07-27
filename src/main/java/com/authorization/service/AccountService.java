package com.authorization.service;

import com.authorization.model.Account;
import com.authorization.model.Category;
import com.authorization.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Optional<Account> getById(Long id) {
        return accountRepository.findById(id);
    }

    public Account addCreditByCategory(Account account, Category category, Double amount) {
        switch (category) {
            case FOOD:
                account.setFoodBalance(account.getFoodBalance() + amount);
                break;
            case MEAL:
                account.setMealBalance(account.getMealBalance() + amount);
                break;
            case CASH:
                account.setCashBalance(account.getCashBalance() + amount);
                break;
        }
        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Double getBalanceByCategory(Account account, Category category) {
        return switch (category) {
            case FOOD -> account.getFoodBalance();
            case MEAL -> account.getMealBalance();
            case CASH -> account.getCashBalance();
            default -> 0.0;
        };
    }

    public void updateBalance(Category category, Account account, Double amount) {
        switch (category) {
            case FOOD:
                account.setFoodBalance(account.getFoodBalance() - amount);
                break;
            case MEAL:
                account.setMealBalance(account.getMealBalance() - amount);
                break;
            case CASH:
                account.setCashBalance(account.getCashBalance() - amount);
                break;
        }
    }

    public Account save (Account account) {
        return accountRepository.save(account);
    }

}
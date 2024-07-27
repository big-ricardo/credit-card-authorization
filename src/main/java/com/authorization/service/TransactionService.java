package com.authorization.service;

import com.authorization.exceptions.InsufficientBalanceException;
import com.authorization.model.Account;
import com.authorization.model.Category;
import com.authorization.model.Merchant;
import com.authorization.model.Transaction;
import com.authorization.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final AccountService accountService;
    private final CategoryService categoryService;
    private final TransactionRepository transactionRepository;
    private final MerchantService merchantService;

    @Autowired
    public TransactionService(AccountService accountService, CategoryService categoryService, TransactionRepository transactionRepository, MerchantService merchantService) {
        this.accountService = accountService;
        this.categoryService = categoryService;
        this.transactionRepository = transactionRepository;
        this.merchantService = merchantService;
    }

    public void authorize(Transaction transaction, Account account) {

        Optional<Merchant> merchant = getMerchantByName(transaction.getMerchant());

        merchant.ifPresent(value -> transaction.setMcc(value.getMcc()));

        Category category = categoryService.getCategoryByMcc(transaction.getMcc());

        if (isBalanceSufficient(transaction, account)) {
            processTransaction(category, account, transaction);
        } else if (isCashBalanceSufficient(transaction, account)) {
            processTransaction(Category.CASH, account, transaction);
        } else {
            throw new InsufficientBalanceException("Insufficient balance");
        }
    }

    private boolean isBalanceSufficient(Transaction transaction, Account account) {
        Category category = categoryService.getCategoryByMcc(transaction.getMcc());

        if(category == Category.CASH) {
            return false;
        }

        Double balance = accountService.getBalanceByCategory(account, category);
        return balance >= transaction.getTotalAmount();
    }

    private boolean isCashBalanceSufficient(Transaction transaction, Account account) {
        double totalAmount = transaction.getTotalAmount();
        double cashBalance = account.getCashBalance();
        return cashBalance >= totalAmount;
    }

    private void processTransaction(Category category, Account account, Transaction transaction) {
        accountService.updateBalance(category, account, transaction.getTotalAmount());
        transactionRepository.save(transaction);
    }

    private Optional<Merchant> getMerchantByName(String name) {
        return Optional.ofNullable(merchantService.getByName(name));
    }

    public Transaction save(Transaction transaction) {
        return this.transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return this.transactionRepository.findAll();
    }
}

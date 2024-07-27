package com.authorization.controller;

import com.authorization.exceptions.InsufficientBalanceException;
import com.authorization.model.Account;
import com.authorization.model.Authorization;
import com.authorization.model.Transaction;
import com.authorization.service.AccountService;
import com.authorization.service.TransactionService;
import com.authorization.view.AuthorizationView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/authorize")
public class TransactionController {

    private final TransactionService transactionService;
    private final AccountService accountService;

    @Autowired
    public TransactionController(TransactionService transactionService, AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @PostMapping
    public ResponseEntity<AuthorizationView> authorize(@Valid @RequestBody Transaction transaction) {
        transactionService.save(transaction);

        Optional<Account> accountOpt = accountService.getById(Long.valueOf(transaction.getAccountId()));

        if (accountOpt.isEmpty()) {
            return ResponseEntity.ok(new AuthorizationView(Authorization.NoAuthorized));
        }

        Account account = accountOpt.get();

        try {
            transactionService.authorize(transaction, account);

            accountService.save(account);

            transaction.setAuthorized(true);
            transactionService.save(transaction);

            return ResponseEntity.ok(new AuthorizationView(Authorization.Authorized));
        } catch (InsufficientBalanceException e) {
            return ResponseEntity.ok(new AuthorizationView(Authorization.InsufficientFunds));
        } catch (Exception e) {
            return ResponseEntity.ok(new AuthorizationView(Authorization.NoAuthorized));
        }
    }
}

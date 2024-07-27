package com.authorization.controller;

import com.authorization.dto.CreditDTO;
import com.authorization.exceptions.AccountNotFoundException;
import com.authorization.model.Account;
import com.authorization.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	private final AccountService accountService;

	@Autowired
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@PostMapping
	public ResponseEntity<Account> createAccount(@Valid @RequestBody Account account) {
		Account createdAccount = accountService.createAccount(account);
		return ResponseEntity.ok(createdAccount);
	}

	@PostMapping("{id}/credit")
	public ResponseEntity<Account> addCreditByCategory(@Valid @PathVariable Long id, @Valid @RequestBody CreditDTO creditDTO) {
		Optional<Account> accountOpt = accountService.getById(id);

		if (accountOpt.isEmpty()) {
			throw new AccountNotFoundException("Account not found");
		}

		Account account = accountOpt.get();

		Account updatedAccount = accountService.addCreditByCategory(account, creditDTO.getCategory(), creditDTO.getAmount());
		return ResponseEntity.ok(updatedAccount);
	}

	@GetMapping
	public ResponseEntity<List<Account>> getAllAccounts() {
		List<Account> accounts = accountService.getAllAccounts();
		return ResponseEntity.ok(accounts);
	}
}
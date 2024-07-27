package com.authorization.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testAccountCreation() {
        // Testa a criação de uma instância com valores padrão
        Account account = new Account();
        assertNotNull(account, "Account instance should be created.");

        // Testa a criação de uma instância com valores especificados
        Account accountWithValues = new Account(1L, 100.0, 200.0, 300.0);
        assertEquals(100.0, accountWithValues.getFoodBalance(), "Food balance should be set correctly.");
        assertEquals(200.0, accountWithValues.getMealBalance(), "Meal balance should be set correctly.");
        assertEquals(300.0, accountWithValues.getCashBalance(), "Cash balance should be set correctly.");
    }

    @Test
    public void testAccountValidation() {
        // Testa uma instância válida
        Account validAccount = new Account(1L, 100.0, 200.0, 300.0);
        Set<ConstraintViolation<Account>> violations = validator.validate(validAccount);
        assertTrue(violations.isEmpty(), "Valid Account should have no constraint violations.");

        // Testa uma instância inválida com valores negativos
        Account invalidAccount = new Account(1L, -100.0, 200.0, 300.0);
        violations = validator.validate(invalidAccount);
        assertFalse(violations.isEmpty(), "Account with negative food balance should have constraint violations.");

        // Verifica se a violação é para o campo correto
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("foodBalance")),
                "Constraint violation should be for foodBalance field.");
    }

    @Test
    public void testAccountSetters() {
        Account account = new Account();
        account.setFoodBalance(150.0);
        account.setMealBalance(250.0);
        account.setCashBalance(350.0);

        assertEquals(150.0, account.getFoodBalance(), "Food balance should be updated correctly.");
        assertEquals(250.0, account.getMealBalance(), "Meal balance should be updated correctly.");
        assertEquals(350.0, account.getCashBalance(), "Cash balance should be updated correctly.");
    }
}

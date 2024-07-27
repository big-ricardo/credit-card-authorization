package com.authorization.dto;

import com.authorization.model.Category;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CreditDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testCreditDTOConstructor() {
        // Test with valid values
        CreditDTO dto = new CreditDTO(50.0, "FOOD");
        assertEquals(50.0, dto.getAmount(), "Amount should be set correctly.");
        assertEquals(Category.FOOD, dto.getCategory(), "Category should be set correctly.");
    }

    @Test
    public void testCreditDTOValidation() {
        // Test valid CreditDTO
        CreditDTO validDto = new CreditDTO(50.0, "FOOD");
        Set<ConstraintViolation<CreditDTO>> violations = validator.validate(validDto);
        assertTrue(violations.isEmpty(), "Valid CreditDTO should have no constraint violations.");

        // Test invalid CreditDTO with null amount
        CreditDTO invalidDto = new CreditDTO(null, "FOOD");
        violations = validator.validate(invalidDto);
        assertFalse(violations.isEmpty(), "CreditDTO with null amount should have constraint violations.");

        // Test invalid CreditDTO with negative amount
        invalidDto = new CreditDTO(-10.0, "FOOD");
        violations = validator.validate(invalidDto);
        assertFalse(violations.isEmpty(), "CreditDTO with negative amount should have constraint violations.");
    }

    @Test
    public void testCreditDTOSetters() {
        CreditDTO dto = new CreditDTO(50.0, "FOOD");
        dto.setAmount(100.0);
        dto.setCategory(Category.MEAL);

        assertEquals(100.0, dto.getAmount(), "Amount should be updated correctly.");
        assertEquals(Category.MEAL, dto.getCategory(), "Category should be updated correctly.");
    }
}

package com.authorization.service;

import com.authorization.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryServiceTest {

    private CategoryService categoryService;

    @BeforeEach
    public void setUp() {
        categoryService = new CategoryService();
    }

    @Test
    public void testGetCategoryByMccForFood() {
        // Act & Assert
        assertEquals(Category.FOOD, categoryService.getCategoryByMcc("5411"));
        assertEquals(Category.FOOD, categoryService.getCategoryByMcc("5412"));
    }

    @Test
    public void testGetCategoryByMccForMeal() {
        // Act & Assert
        assertEquals(Category.MEAL, categoryService.getCategoryByMcc("5811"));
        assertEquals(Category.MEAL, categoryService.getCategoryByMcc("5812"));
    }

    @Test
    public void testGetCategoryByMccForCash() {
        // Act & Assert
        assertEquals(Category.CASH, categoryService.getCategoryByMcc("0000"));
        assertEquals(Category.CASH, categoryService.getCategoryByMcc("9999"));
        assertEquals(Category.CASH, categoryService.getCategoryByMcc("1234"));
    }
}

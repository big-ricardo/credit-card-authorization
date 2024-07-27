package com.authorization.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CategoryTest {

    @Test
    public void testCategoryValues() {
        Category[] categories = Category.values();

        assertEquals(3, categories.length, "Category enum should have 3 values.");
        assertTrue(containsCategory(categories, Category.FOOD), "Category should contain FOOD.");
        assertTrue(containsCategory(categories, Category.MEAL), "Category should contain MEAL.");
        assertTrue(containsCategory(categories, Category.CASH), "Category should contain CASH.");
    }

    @Test
    public void testCategoryNames() {
        assertEquals("FOOD", Category.FOOD.name(), "The name of FOOD should be FOOD.");
        assertEquals("MEAL", Category.MEAL.name(), "The name of MEAL should be MEAL.");
        assertEquals("CASH", Category.CASH.name(), "The name of CASH should be CASH.");
    }

    private boolean containsCategory(Category[] categories, Category category) {
        for (Category c : categories) {
            if (c.equals(category)) {
                return true;
            }
        }
        return false;
    }
}

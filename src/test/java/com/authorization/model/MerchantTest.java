package com.authorization.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MerchantTest {

    @Test
    public void testMerchantConstructorAndGetters() {
        Merchant merchant = new Merchant(1L, "Test Merchant", "1234");

        assertEquals(1L, merchant.getId(), "The ID should be 1L.");
        assertEquals("Test Merchant", merchant.getName(), "The name should be 'Test Merchant'.");
        assertEquals("1234", merchant.getMcc(), "The MCC should be '1234'.");
    }

    @Test
    public void testMerchantSetters() {
        Merchant merchant = new Merchant();
        merchant.setId(1L);
        merchant.setName("Test Merchant");
        merchant.setMcc("1234");

        assertEquals(1L, merchant.getId(), "The ID should be 1L.");
        assertEquals("Test Merchant", merchant.getName(), "The name should be 'Test Merchant'.");
        assertEquals("1234", merchant.getMcc(), "The MCC should be '1234'.");
    }

    @Test
    public void testMerchantEquality() {
        Merchant merchant1 = new Merchant(1L, "Test Merchant", "1234");
        Merchant merchant2 = new Merchant(1L, "Test Merchant", "1234");

        assertEquals(merchant1, merchant2, "Merchants with the same ID, name, and MCC should be equal.");
    }

    @Test
    public void testMerchantHashCode() {
        Merchant merchant1 = new Merchant(1L, "Test Merchant", "1234");
        Merchant merchant2 = new Merchant(1L, "Test Merchant", "1234");

        assertEquals(merchant1.hashCode(), merchant2.hashCode(), "Merchants with the same ID, name, and MCC should have the same hash code.");
    }

    @Test
    public void testMerchantToString() {
        Merchant merchant = new Merchant(1L, "Test Merchant", "1234");

        String expectedString = "Merchant(id=1, name=Test Merchant, mcc=1234)";
        assertEquals(expectedString, merchant.toString(), "The toString method should return the expected string.");
    }
}

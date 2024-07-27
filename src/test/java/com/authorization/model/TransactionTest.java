package com.authorization.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    @Test
    public void testTransactionConstructorAndGetters() {
        Transaction transaction = new Transaction(
                "uuid-1234",
                "account-id-1",
                100.0,
                "Test Merchant",
                "5411",
                true
        );

        assertEquals("uuid-1234", transaction.getId(), "The ID should be 'uuid-1234'.");
        assertEquals("account-id-1", transaction.getAccountId(), "The account ID should be 'account-id-1'.");
        assertEquals(100.0, transaction.getTotalAmount(), "The total amount should be 100.0.");
        assertEquals("Test Merchant", transaction.getMerchant(), "The merchant should be 'Test Merchant'.");
        assertEquals("5411", transaction.getMcc(), "The MCC should be '5411'.");
        assertTrue(transaction.isAuthorized(), "The authorized flag should be true.");
    }

    @Test
    public void testTransactionSetters() {
        Transaction transaction = new Transaction();
        transaction.setId("uuid-1234");
        transaction.setAccountId("account-id-1");
        transaction.setTotalAmount(100.0);
        transaction.setMerchant("Test Merchant");
        transaction.setMcc("5411");
        transaction.setAuthorized(true);

        assertEquals("uuid-1234", transaction.getId(), "The ID should be 'uuid-1234'.");
        assertEquals("account-id-1", transaction.getAccountId(), "The account ID should be 'account-id-1'.");
        assertEquals(100.0, transaction.getTotalAmount(), "The total amount should be 100.0.");
        assertEquals("Test Merchant", transaction.getMerchant(), "The merchant should be 'Test Merchant'.");
        assertEquals("5411", transaction.getMcc(), "The MCC should be '5411'.");
        assertTrue(transaction.isAuthorized(), "The authorized flag should be true.");
    }

    @Test
    public void testTransactionDefaultValues() {
        Transaction transaction = new Transaction();

        assertNull(transaction.getId(), "The ID should be null by default.");
        assertNull(transaction.getAccountId(), "The account ID should be null by default.");
        assertNull(transaction.getTotalAmount(), "The total amount should be null by default.");
        assertNull(transaction.getMerchant(), "The merchant should be null by default.");
        assertNull(transaction.getMcc(), "The MCC should be null by default.");
        assertFalse(transaction.isAuthorized(), "The default authorized flag should be false.");
    }
}

package com.authorization.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthorizationTest {

    @Test
    public void testAuthorizedEnum() {
        Authorization auth = Authorization.Authorized;
        assertEquals("00", auth.getCode(), "The code for Authorized should be 00.");
    }

    @Test
    public void testInsufficientFundsEnum() {
        Authorization auth = Authorization.InsufficientFunds;
        assertEquals("51", auth.getCode(), "The code for InsufficientFunds should be 51.");
    }

    @Test
    public void testNoAuthorizedEnum() {
        Authorization auth = Authorization.NoAuthorized;
        assertEquals("07", auth.getCode(), "The code for NoAuthorized should be 07.");
    }
}

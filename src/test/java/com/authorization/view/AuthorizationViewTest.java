package com.authorization.view;

import com.authorization.model.Authorization;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorizationViewTest {

    @Test
    public void testConstructorAndGetCode() {
        Authorization authorization = Authorization.Authorized;
        AuthorizationView view = new AuthorizationView(authorization);

        assertEquals(authorization.getCode(), view.getCode(), "The code should match the authorization code.");
    }

    @Test
    public void testConstructorWithInsufficientFunds() {
        Authorization authorization = Authorization.InsufficientFunds;
        AuthorizationView view = new AuthorizationView(authorization);

        assertEquals(authorization.getCode(), view.getCode(), "The code should match the InsufficientFunds authorization code.");
    }

    @Test
    public void testConstructorWithNoAuthorized() {
        Authorization authorization = Authorization.NoAuthorized;
        AuthorizationView view = new AuthorizationView(authorization);

        assertEquals(authorization.getCode(), view.getCode(), "The code should match the NoAuthorized authorization code.");
    }

    @Test
    public void testDefaultValues() {
        Authorization authorization = Authorization.Authorized;
        AuthorizationView view = new AuthorizationView(authorization);

        assertNotNull(view.getCode(), "The code should not be null.");
    }
}

package com.authorization.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountNotFoundExceptionTest {

    @Test
    public void testAccountNotFoundException() {
        // Defina a mensagem de erro esperada
        String expectedMessage = "Account not found";

        // Lance a exceção e verifique se a mensagem está correta
        AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, () -> {
            throw new AccountNotFoundException(expectedMessage);
        });

        // Verifique se a mensagem da exceção é a esperada
        assertEquals(expectedMessage, exception.getMessage(), "The exception message should be set correctly.");
    }
}

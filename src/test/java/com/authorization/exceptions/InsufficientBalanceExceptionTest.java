package com.authorization.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InsufficientBalanceExceptionTest {

    @Test
    public void testInsufficientBalanceException() {
        // Defina a mensagem de erro esperada
        String expectedMessage = "Insufficient balance";

        // Lance a exceção e verifique se a mensagem está correta
        InsufficientBalanceException exception = assertThrows(InsufficientBalanceException.class, () -> {
            throw new InsufficientBalanceException(expectedMessage);
        });

        // Verifique se a mensagem da exceção é a esperada
        assertEquals(expectedMessage, exception.getMessage(), "The exception message should be set correctly.");
    }
}

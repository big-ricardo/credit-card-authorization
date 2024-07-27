package com.authorization.model;

import lombok.Getter;

@Getter
public enum Authorization {
    Authorized("00"),
    InsufficientFunds("51"),
    NoAuthorized("07");

    private final String code;

    Authorization(String code) {
        this.code = code;
    }
}

package com.authorization.view;

import com.authorization.model.Authorization;
import lombok.Data;

@Data
public class AuthorizationView {

    private String code;

    public AuthorizationView(Authorization code) {
        this.code = code.getCode();
    }
}

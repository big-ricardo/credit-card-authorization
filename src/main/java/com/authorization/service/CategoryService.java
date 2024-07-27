package com.authorization.service;

import org.springframework.stereotype.Service;
import com.authorization.model.Category;

@Service
public class CategoryService {

    public Category getCategoryByMcc(String mcc) {
        return switch (mcc) {
            case "5411", "5412" -> Category.FOOD;
            case "5811", "5812" -> Category.MEAL;
            default -> Category.CASH;
        };
    }
}
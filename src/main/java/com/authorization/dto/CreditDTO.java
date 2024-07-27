package com.authorization.dto;

import com.authorization.model.Category;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CreditDTO {

    @NotNull
    private Category category;

    @NotNull
    @PositiveOrZero
    private Double amount;

    public CreditDTO(Double amount, String category) {
        this.category = Category.valueOf(category.toUpperCase());
        this.amount = amount;
    }
}

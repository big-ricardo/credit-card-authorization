package com.authorization.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @NotNull
    @NotEmpty
    private String accountId;
    @PositiveOrZero
    private Double totalAmount;
    @NotNull
    @NotEmpty
    private String merchant;
    @NotNull
    @NotEmpty
    private String mcc;
    private boolean authorized = false;
}

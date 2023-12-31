package com.alibaba.entities;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Transaction {
    private int id_Transaction;
    private Account sourceAccount;
    private Account destinationAccount;
    private double amount;
    private LocalDateTime transactionTime;
    private Operation operation;

    public Transaction(Integer id_Transaction, Account sourceAccount, Account destinationAccount, double amount, LocalDateTime transactionTime, Operation operation){
        super();


    }

}

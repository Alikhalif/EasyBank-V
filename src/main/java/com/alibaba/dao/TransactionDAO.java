package com.alibaba.dao;

import com.alibaba.entities.Transaction;

import java.util.Optional;

public interface TransactionDAO {
    Optional<Transaction> create(Transaction transaction);
    boolean updateDestinationBalance(Transaction transaction);
    boolean updateFromAccountBalance(Transaction transaction);
    boolean delete(Integer id);
}

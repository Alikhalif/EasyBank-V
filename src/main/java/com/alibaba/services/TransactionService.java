package com.alibaba.services;

import com.alibaba.dao.daoImpl.OperationDAOImpl;
import com.alibaba.dao.daoImpl.TransactionDAOImpl;
import com.alibaba.entities.Operation;
import com.alibaba.entities.Transaction;

import java.util.Optional;

public class TransactionService {
    private TransactionDAOImpl transactionDAO;

    private OperationDAOImpl operationDao;
    public TransactionService(TransactionDAOImpl transactionDAO, OperationDAOImpl operationDao) {
        this.transactionDAO = transactionDAO;
        this.operationDao = operationDao;
    }

    public Transaction createPayment(Transaction transaction, Operation operation) throws Exception {
        if (transaction.getSourceAccount() == null || transaction.getDestinationAccount() == null || transaction.get_employee() == null) {
            return null;
        }else {
            Optional<Operation> optionalOperation = operationDao.create(operation);
            if (optionalOperation.isPresent()) {
                if (transaction.getAmount() > transaction.getSourceAccount().getBalance()) {
                    throw new Exception("Amount insufficient in your account");
                }
                Optional<Transaction> optionalPayment = transactionDAO.create(transaction);
                if (optionalPayment.isPresent()) {
                    transactionDAO.updateFromAccountBalance(transaction);
                    transactionDAO.updateDestinationBalance(transaction);
                }
                return optionalPayment.get();
            }else {
                return null;
            }
        }
    }

    public boolean deletePayment(Integer id) {
        if (id.toString().isEmpty()) {
            return false;
        }else {
            return transactionDAO.delete(id);
        }
    }
}

package com.alibaba.dao.daoImpl;

import com.alibaba.connexion.DB;
import com.alibaba.dao.TransactionDAO;
import com.alibaba.entities.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class TransactionDAOImpl implements TransactionDAO {
    private final Connection conn;

    public TransactionDAOImpl() {
        conn = DB.getConnection();
    }

    public TransactionDAOImpl(Connection connection) {
        conn = connection;
    }
    @Override
    public Optional<Transaction> create(Transaction transaction) {
        try {
            String insertSQL = "INSERT INTO payments (from_account, to_account, operationNumber) VALUES (?, ?, ?) RETURNING id";
            PreparedStatement preparedStatement = conn.prepareStatement(insertSQL);

            preparedStatement.setInt(1, transaction.getSourceAccount().getAccountNumber());
            preparedStatement.setInt(2, transaction.getDestinationAccount().getAccountNumber());
            preparedStatement.setInt(3, transaction.getOperation().getOperationNumber());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int generatedId = resultSet.getInt("id");

                transaction.setId_Transaction(generatedId);

                return Optional.of(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public boolean updateDestinationBalance(Transaction transaction) {
        String updateBalanceSQL = "UPDATE accounts SET balance = ? WHERE accountNumber = ?";

        double newBalance;
        newBalance = transaction.getSourceAccount().getBalance() + transaction.getAmount();
        try (PreparedStatement preparedStatement = conn.prepareStatement(updateBalanceSQL)) {
            preparedStatement.setDouble(1, newBalance);
            preparedStatement.setInt(2, transaction.getSourceAccount().getAccountNumber());

            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateFromAccountBalance(Transaction transaction) {
        String updateBalanceSQL = "UPDATE accounts SET balance = ? WHERE accountNumber = ?";

        double newBalance;
        newBalance = transaction.getSourceAccount().getBalance() - transaction.getAmount();
        try (PreparedStatement preparedStatement = conn.prepareStatement(updateBalanceSQL)) {
            preparedStatement.setDouble(1, newBalance);
            preparedStatement.setInt(2, transaction.getSourceAccount().getAccountNumber());

            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try {
            String deleteSQL = "DELETE FROM payments WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, id);

            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


}

package com.alibaba.dao.daoImpl;

import com.alibaba.connexion.DB;
import com.alibaba.dao.OperationDAO;
import com.alibaba.entities.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OperationDAOImpl implements OperationDAO {
    private Connection connection = DB.getConnection();
    Operation operation = new Operation();

    @Override
    public void createOperation(Operation operation){
        try{
            String SQL = "INSERT INTO operations (creationDate, amount, type, accountNumber, employeeMatricule) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement pstmt = connection.prepareStatement(SQL);
            pstmt.setDate(1, java.sql.Date.valueOf(operation.getCreationDate()));
            pstmt.setDouble(2, operation.getAmount());
            pstmt.setString(3, operation.getOperationStatus().toString());
            pstmt.setInt(4, operation.getEmployee().getMatricule());
            pstmt.setInt(5, operation.getAccount().getAccountNumber());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Operation getOperationByNumber(int operationNumber) {
        try{
            String sql = "SELECT * FROM operations WHERE operationNumber = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,operationNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
//creationDate, amount, type, accountNumber, employeeMatricule
            if (resultSet.next()) {
                operation.setOperationNumber(resultSet.getInt("operationNumber"));
                operation.setCreationDate(resultSet.getDate("creationDate").toLocalDate());
                operation.setAmount(resultSet.getDouble("amount"));
                operation.setOperationStatus(OperationStatus.valueOf(resultSet.getString("type")) );
                Account account = new Account();
                account.setAccountNumber(resultSet.getInt("accountNumber"));
                operation.setAccount(account);

                Employee employee = new Employee();
                employee.setMatricule(resultSet.getInt("employeeMatricule"));
                operation.setEmployee(employee);

                return operation;
            }
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
        return null;
    }


    public Boolean operationDelete(int numberOperation){
        try {
            String sql = "DELETE FROM operations WHERE operationNumber = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, numberOperation);
            int affectedRows = preparedStatement.executeUpdate();

            if(affectedRows > 0){
                return true;
            }else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public Operation getAccountByOperation(int numberOperation) {
        try{
            String sql = "SELECT * FROM operations OP join accounts AC on OP.accountNumber = AC.accountNumber WHERE OP.operationNumber = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,numberOperation);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Operation operation1 = new Operation();
                Account account = new Account();
                account.setAccountNumber(resultSet.getInt("accountNumber"));
                account.setBalance(resultSet.getInt("balance"));
                account.setCreationDate(resultSet.getDate("creationDate").toLocalDate());
                account.setStatus(AccountStatus.valueOf(resultSet.getString("status")) );
                Client client = new Client();
                client.setCode(resultSet.getInt("clientCode"));
                account.setClient(client);

                Employee employee = new Employee();
                employee.setMatricule(resultSet.getInt("employeeMatricule"));
                account.setEmployee(employee);
                operation1.setAccount(account);
                return operation1;
                // Create and return an Employee object

            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}

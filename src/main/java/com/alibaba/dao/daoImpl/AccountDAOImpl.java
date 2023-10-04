package com.alibaba.dao.daoImpl;

import com.alibaba.connexion.DB;
import com.alibaba.dao.AccountDAO;
import com.alibaba.entities.*;
import jdk.jshell.Snippet;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


public class AccountDAOImpl implements AccountDAO {

    private Connection connection = DB.getConnection();
    Account account = new Account();

    @Override
    public void createAccountChecking(Account account) {
        String addAccountQuery = "INSERT INTO accounts (balance, creationDate, status, clientCode, employeeMatricule) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(addAccountQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDouble(1, account.getBalance());
            preparedStatement.setDate(2, java.sql.Date.valueOf(account.getCreationDate()));
            preparedStatement.setString(3, account.getStatus().toString());
            preparedStatement.setInt(4, account.getClient().getCode());
            preparedStatement.setInt(5, account.getEmployee().getMatricule());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int accountNumber = -1;
            if (generatedKeys.next()) {
                accountNumber = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Failed to retrieve auto-generated account number.");
            }


            if (account instanceof CheckingAccount) {
                CheckingAccount checkingAccount = (CheckingAccount) account;
                String addCheckingAccountQuery = "INSERT INTO currentAccounts (accountNumber, overdraft) VALUES (?, ?)";
                try (PreparedStatement ps = connection.prepareStatement(addCheckingAccountQuery)) {
                    ps.setInt(1, accountNumber);
                    ps.setDouble(2, checkingAccount.getOverdraftLimit());
                    ps.executeUpdate();
                } catch (SQLException e) {
                    // Handle SQLException
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createAccountSavinge(Account account) {
        String addAccountQuery = "INSERT INTO accounts (balance, creationDate, status, clientCode, employeeMatricule) VALUES ( ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(addAccountQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDouble(1, account.getBalance());
            preparedStatement.setDate(2, java.sql.Date.valueOf(account.getCreationDate()));
            preparedStatement.setString(3, account.getStatus().toString());
            preparedStatement.setInt(4, account.getClient().getCode());
            preparedStatement.setInt(5, account.getEmployee().getMatricule());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int accountNumber = -1;
            if (generatedKeys.next()) {
                accountNumber = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Failed to retrieve auto-generated account number.");
            }

            if (account instanceof SavingsAccount) {
                SavingsAccount savingsAccount = (SavingsAccount) account;
                String SQL = "INSERT INTO savingsAccounts (accountNumber, interestRate) VALUES (?, ?)";
                try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                    ps.setInt(1, accountNumber);
                    ps.setDouble(2, savingsAccount.getInterestRate());
                    ps.executeUpdate();
                } catch (SQLException e) {
                    // Handle SQLException
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Account> getAllAccount() {
        List<Account> accounts = new ArrayList<>();
        try{
            String sql = "SELECT * FROM accounts";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
//            balance, creationDate, status, clientCode, employeeMatricule
            while (resultSet.next()){
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
                accounts.add(account);
            }
            return accounts;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateAccount(Account account) {

    }

    @Override
    public Boolean deleteAccount(int accountNumber) {
        try {
            String sql = "DELETE FROM accounts WHERE accountNumber = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountNumber);
            int affectedRows = preparedStatement.executeUpdate();

            System.out.println("Number of affected rows: " + affectedRows);

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
    public Account getAccountByNumber(int accountNumber) {
        try{
            String sql = "SELECT * FROM accounts WHERE accountNumber = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,accountNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
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


                // Create and return an Employee object
                return account;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public Boolean updateAccountByStatus(Account account){
        try {
            String sql = "UPDATE accounts SET status=? WHERE accountNumber = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, account.getStatus().toString());
            preparedStatement.setInt(2, account.getAccountNumber());

            preparedStatement.executeUpdate();

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public Account getAccountByClient(int code) {
        try{
            String sql = "SELECT * FROM accounts WHERE clientCode = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,code);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                account.setBalance(resultSet.getInt("balance"));
                account.setCreationDate(resultSet.getDate("creationDate").toLocalDate());
                account.setStatus(AccountStatus.valueOf(resultSet.getString("status")) );
                Client client = new Client();
                client.setCode(resultSet.getInt("clientCode"));
                account.setClient(client);

                Employee employee = new Employee();
                employee.setMatricule(resultSet.getInt("employeeMatricule"));
                account.setEmployee(employee);


                // Create and return an Employee object
                return account;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<Account> getAccountsByStatus(Account account1) {
        ArrayList<Account> accounts = new ArrayList<>();
        try{
            String sql = "SELECT * FROM accounts WHERE status = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account1.getStatus().toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Account account = new Account();
                account.setBalance(resultSet.getInt("balance"));
                account.setCreationDate(resultSet.getDate("creationDate").toLocalDate());
                account.setStatus(AccountStatus.valueOf(resultSet.getString("status")) );
                Client client = new Client();
                client.setCode(resultSet.getInt("clientCode"));
                account.setClient(client);

                Employee employee = new Employee();
                employee.setMatricule(resultSet.getInt("employeeMatricule"));
                account.setEmployee(employee);

                accounts.add(account);
            }
            return accounts;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public List<Account> getAccountsByDateCreation(Account account1) {
        ArrayList<Account> accounts = new ArrayList<>();
        try{
            String sql = "SELECT * FROM accounts WHERE creationDate = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, java.sql.Date.valueOf(account1.getCreationDate()));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                account.setBalance(resultSet.getInt("balance"));
                account.setCreationDate(resultSet.getDate("creationDate").toLocalDate());
                account.setStatus(AccountStatus.valueOf(resultSet.getString("status")) );
                Client client = new Client();
                client.setCode(resultSet.getInt("clientCode"));
                account.setClient(client);

                Employee employee = new Employee();
                employee.setMatricule(resultSet.getInt("employeeMatricule"));
                account.setEmployee(employee);

                accounts.add(account);
            }
            return accounts;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    public Boolean updateAccountByBalance(Account account){
        try {
            String sql = "UPDATE accounts SET balance=? WHERE accountNumber = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setDouble(1, account.getBalance());
            preparedStatement.setInt(2, account.getAccountNumber());

            preparedStatement.executeUpdate();

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public SavingsAccount getSavingsAccount(int accountNumber){
        try{
            SavingsAccount savingsAccount = new SavingsAccount();
            String sql = "SELECT * FROM savingsAccounts WHERE accountNumber = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,accountNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                savingsAccount.setAccountNumber(resultSet.getInt("accountNumber"));
                savingsAccount.setInterestRate(resultSet.getDouble("interestRate"));

                return savingsAccount;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public CheckingAccount getCheckingAccount(int accountNumber){
        try{
            CheckingAccount checkingAccount = new CheckingAccount();
            String sql = "SELECT * FROM currentAccounts WHERE accountNumber = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,accountNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                checkingAccount.setAccountNumber(resultSet.getInt("accountNumber"));
                checkingAccount.setOverdraftLimit(resultSet.getDouble("overdraft"));

                return checkingAccount;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<SavingsAccount> updateSavingsAccount(SavingsAccount savingsAccount){
        try {
            String sql = "UPDATE savingsAccounts SET interestRate=? WHERE accountNumber = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setDouble(1, savingsAccount.getInterestRate());
            preparedStatement.setInt(2, savingsAccount.getAccountNumber());

            int affectationRow = preparedStatement.executeUpdate();
            if (affectationRow > 0){
                return Optional.of(savingsAccount);
            }else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }


    @Override
    public Optional<CheckingAccount> updateCheckingAccount(CheckingAccount checkingAccount){
        try {
            String sql = "UPDATE currentAccounts SET overdraft=? WHERE accountNumber = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setDouble(1, checkingAccount.getOverdraftLimit());
            preparedStatement.setInt(2, checkingAccount.getAccountNumber());

            int affectationRow = preparedStatement.executeUpdate();
            if (affectationRow > 0){
                return Optional.of(checkingAccount);
            }else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }



    //@Override




}

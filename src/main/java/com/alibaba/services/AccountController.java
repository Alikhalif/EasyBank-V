package com.alibaba.services;

import com.alibaba.entities.*;
import com.alibaba.dao.daoImpl.AccountDAOImpl;
import com.alibaba.dao.daoImpl.ClientDAOImpl;
import com.alibaba.dao.daoImpl.EmployeeDAOImpl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Optional;

public class AccountController {
    Account account = new Account();
    SavingsAccount savingsAccount = new SavingsAccount();
    CheckingAccount checkingAccount = new CheckingAccount();

    AccountDAOImpl seracc = new AccountDAOImpl();

    EmployeeController employeeController = new EmployeeController();
    ClientController clientController = new ClientController();

    public void addAccount(){
        clientController.getAllClient();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Code Client");
        int codeC = sc.nextInt();



        employeeController.AllEmployee();
        System.out.println("Enter Matricule Employee");
        int matriculeEmp = sc.nextInt();

        //account_number, balance, creation_date, status
        System.out.println("Enter balance");
        double balance = sc.nextDouble();
        System.out.println("Enter creation date");
        String creationDate = sc.next();

        System.out.println("Enter status : \n" +
                "1 - active\n" +
                "2 - inactive \n" +
                "3 - blocked\n");

        int ch = sc.nextInt();
        String sts = "ACTIVE";
        if (ch == 1){
            System.out.println("actv");
            sts = "ACTIVE";
        } else if (ch == 2) {
            System.out.println("inctv");
            sts = "INACTIVE";
        }else if (ch == 3) {
            System.out.println("blk");
            sts = "BLOCKED";
        }


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate creationDateprs = LocalDate.parse(creationDate, formatter);

        account.setBalance(balance);
        account.setCreationDate(creationDateprs);
        account.setStatus(AccountStatus.valueOf(sts));

        Client client = new Client();
        client.setCode(codeC);
        account.setClient(client);

        Employee employee = new Employee();
        employee.setMatricule(matriculeEmp);
        account.setEmployee(employee);


        System.out.println("Enter type account : \n" +
                "1 - Savings Account\n" +
                "2 - Current Account\n");

        int typeAccount = sc.nextInt();
        if (typeAccount == 1){
            System.out.println("Savings");
            System.out.println("Enter interest Rate");
            double interestRate = sc.nextDouble();
            savingsAccount.setBalance(account.getBalance());
            savingsAccount.setCreationDate(account.getCreationDate());
            savingsAccount.setStatus(account.getStatus());
            savingsAccount.setClient(account.getClient());
            savingsAccount.setEmployee(account.getEmployee());

            savingsAccount.setInterestRate(interestRate);
            seracc.createAccountSavinge(savingsAccount);


        } else if (typeAccount == 2) {
            System.out.println("Current");
            System.out.println("Enter overdraft");
            double overdraft = sc.nextDouble();
            checkingAccount.setBalance(account.getBalance());
            checkingAccount.setCreationDate(account.getCreationDate());
            checkingAccount.setStatus(account.getStatus());
            checkingAccount.setClient(account.getClient());
            checkingAccount.setEmployee(account.getEmployee());

            checkingAccount.setOverdraftLimit(overdraft);
            seracc.createAccountChecking(checkingAccount);
        }
    }

    public void deleteAccount(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of the account to delete: ");
        int number = sc.nextInt();

        if (seracc.deleteAccount(number)) {
            System.out.println("Employee deleted successfully");
        } else {
            System.out.println("Employee not deleted");
        }
    }

    public void AllAccount(){
        List<Account> accounts = seracc.getAllAccount();
        for(Account account1 : accounts){
            System.out.println(account1.getAccountNumber()+" | "+account1.getBalance()+" | "+account1.getCreationDate()+" | "+account1.getStatus()+" | "+account1.getClient().getCode()+" | "+account1.getEmployee().getMatricule());
        }
    }

    public void getAccountByNum(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Number Account");
        int num = sc.nextInt();
        Account account = seracc.getAccountByNumber(num);

        if (account != null) {
            System.out.println("Client found:");
            System.out.println("Number account" + account.getAccountNumber());
            System.out.println("Balance: " + account.getBalance());
            System.out.println("Date creation: " + account.getCreationDate());
            System.out.println("Status: " + account.getStatus());
            System.out.println("Code client: " + account.getClient().getCode());
            System.out.println("Matricule : " + account.getEmployee().getMatricule());
        } else {
            System.out.println("Account not found for numero: " + num);
        }
    }


    public void updateStatusAccount(){
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        if (seracc.getAccountByNumber(num) != null){
            System.out.println("Enter your choice \n" +
                    "1 - active\n" +
                    "2 - inactive \n" +
                    "3 - blocked\n");
            int choice = sc.nextInt();
            if(choice == 1){
                account.setStatus(AccountStatus.ACTIVE);
            }else if(choice == 2){
                account.setStatus(AccountStatus.INACTIVE);
            }else if(choice == 3){
                account.setStatus(AccountStatus.BLOCKED);
            }else {
                System.out.println("Enter number correct !");
            }

            account.setAccountNumber(num);
            seracc.updateAccountByStatus(account);


        }
    }


    public void searchByClientCode(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Code Client : ");
        int code = sc.nextInt();
        Account account = seracc.getAccountByClient(code);

        if (account != null) {
            System.out.println("Client found:");
            System.out.println("Number account" + account.getAccountNumber());
            System.out.println("Balance: " + account.getBalance());
            System.out.println("Date creation: " + account.getCreationDate());
            System.out.println("Status: " + account.getStatus());
            System.out.println("Code client: " + account.getClient().getCode());
            System.out.println("Matricule : " + account.getEmployee().getMatricule());
        } else {
            System.out.println("Account not found for numero: " + code);
        }
    }


    public void AllAccountByStatus(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your choice \n" +
                "1 - active\n" +
                "2 - inactive \n" +
                "3 - blocked\n");
        int choice = sc.nextInt();
        if(choice == 1){
            account.setStatus(AccountStatus.ACTIVE);
        }else if(choice == 2){
            account.setStatus(AccountStatus.INACTIVE);
        }else if(choice == 3){
            account.setStatus(AccountStatus.BLOCKED);
        }else {
            System.out.println("Enter number correct !");
        }
        List<Account> accounts = seracc.getAccountsByStatus(account);
        if(accounts != null){
            for(Account account1 : accounts){
                System.out.println(account1.getAccountNumber()+" | "+account1.getBalance()+" | "+account1.getCreationDate()+" | "+account1.getStatus()+" | "+account1.getClient().getCode()+" | "+account1.getEmployee().getMatricule());
            }
        }
        else{
            System.out.println("Not found");
        }
    }


    public void getAccountByDatecreation(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Date form yyyy-MM-dd");
        String dateC = sc.next();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate creationDateprs = LocalDate.parse(dateC, formatter);

        account.setCreationDate(creationDateprs);

        List<Account> accounts = seracc.getAccountsByDateCreation(account);
        if(accounts != null){
            for(Account account1 : accounts){
                System.out.println(account1.getAccountNumber()+" | "+account1.getBalance()+" | "+account1.getCreationDate()+" | "+account1.getStatus()+" | "+account1.getClient().getCode()+" | "+account1.getEmployee().getMatricule());
            }
        }
        else{
            System.out.println("Not found");
        }
    }


    public Boolean checkOperation(Operation operation){
        Account account = seracc.getAccountByNumber(operation.getAccount().getAccountNumber());
        if (account != null){
            System.out.println(account.getBalance());
            System.out.println(operation.getAmount());
            if (account.getBalance() >= operation.getAmount()){
                Double newBalance = account.getBalance() - operation.getAmount();

                Account account1 = new Account();
                account1.setAccountNumber(account.getAccountNumber());
                account1.setBalance(newBalance);
                System.out.println(account1.getAccountNumber());
                System.out.println(account1.getBalance());
                if (seracc.updateAccountByBalance(account1)){
                    System.out.println("Operation success");
                    return true;
                }

            }
            else {
                System.out.println("ma3ndkch mataklch");
            }
        }
        else {
            System.out.println("account not found !");
        }

        return false;
    }

    public Boolean retraitOperation(Operation operation){
        Account account = seracc.getAccountByNumber(operation.getAccount().getAccountNumber());
        if (account != null){
            System.out.println(account.getBalance());
            System.out.println(operation.getAmount());

            Double newBalance = account.getBalance() + operation.getAmount();

            Account account1 = new Account();
            account1.setAccountNumber(account.getAccountNumber());
            account1.setBalance(newBalance);
            if (seracc.updateAccountByBalance(account1)){
                System.out.println("Operation success");
                return true;
            }
        }
        else {
            System.out.println("account not found !");
        }

        return false;
    }


    public void updateAcccount(){
        Scanner sc = new Scanner(System.in);

        boolean validAccountNumber = false;
        int accountNumber = 0;

        while (!validAccountNumber) {
            try {
                System.out.println("Enter the account number you want to update: ");
                accountNumber = sc.nextInt();
                validAccountNumber = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid account number format. Please enter a valid integer.");
                sc.nextLine();
            }
        }
        try{
            Optional<Account> optionalAccount = Optional.ofNullable(seracc.getAccountByNumber(accountNumber));
            if(optionalAccount.isPresent()){
                System.out.println("Enter updated balance: ");
                double updatedBalance = sc.nextDouble();

                Account Updateaccount = optionalAccount.get();

                Updateaccount.setBalance(updatedBalance);
                seracc.updateAccount(Updateaccount);

                if (seracc.getSavingsAccount(accountNumber) != null){
                    Optional<SavingsAccount> savingsAccountOptional = seracc.updateSavingsAccount(seracc.getSavingsAccount(accountNumber));
                    if (savingsAccountOptional.isPresent()){
                        System.out.println("Saving account update successfuly");
                    }else {
                        System.out.println("Failed to update Saving account !");
                    }

                }else {
                    Optional<CheckingAccount> optionalCheckingAccount = seracc.updateCheckingAccount(seracc.getCheckingAccount(accountNumber));
                    if (optionalCheckingAccount.isPresent()){
                        System.out.println("Saving account update successfuly");
                    }else {
                        System.out.println("Failed to update Saving account !");
                    }
                }
            }

        }catch (Exception e){

        }

    }



}

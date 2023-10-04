package com.alibaba.services;

import com.alibaba.entities.Account;
import com.alibaba.entities.Employee;
import com.alibaba.entities.Operation;
import com.alibaba.entities.OperationStatus;
import com.alibaba.dao.daoImpl.OperationDAOImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class OperationController {
    Operation operation = new Operation();
    Account account = new Account();
    Employee employee = new Employee();
    OperationDAOImpl operationDAO = new OperationDAOImpl();
    AccountController accC = new AccountController();
    public void addOperation(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter amount");
        Double amount = sc.nextDouble();

        System.out.println("Enter type operation");
        System.out.println("Enter your choice :\n" +
                "1 - VERSEMENT\n" +
                "2 - RETRAIT \n");
        int choice = sc.nextInt();
        if (choice == 1){
            operation.setOperationStatus(OperationStatus.VERSEMENT);
        } else if (choice == 2) {
            operation.setOperationStatus(OperationStatus.RETRAIT);
        }else {
            System.out.println("Enter number correct");
        }

        System.out.println("Enter number account");
        int numberAcc = sc.nextInt();

        System.out.println("Enter matricule employee");
        int matriculeEmp = sc.nextInt();



        LocalDate DateNow = LocalDate.now();

        operation.setCreationDate(DateNow);
        operation.setAmount(amount);

        account.setAccountNumber(numberAcc);
        operation.setAccount(account);

        employee.setMatricule(matriculeEmp);
        operation.setEmployee(employee);

        if (operation.getOperationStatus().toString() == "VERSEMENT"){
            if (accC.checkOperation(operation)){
                operationDAO.createOperation(operation);
            }
        }else {
            if (accC.retraitOperation(operation)){
                operationDAO.createOperation(operation);
            }
        }


    }


    public void SearchOperation(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Number Operation : ");
        int num = sc.nextInt();
        Operation operation1 = operationDAO.getOperationByNumber(num);

        if (operation1 != null) {
            System.out.println("Client found:");
            System.out.println("Number operation   :" + operation1.getOperationNumber());
            System.out.println("Date creation      : " + operation1.getCreationDate());
            System.out.println("Amount             : " + operation1.getAmount());
            System.out.println("Type operation     : " + operation1.getOperationStatus());
            System.out.println("Account number     : " + operation1.getAccount().getAccountNumber());
            System.out.println("Omployee matricule : " + operation1.getEmployee().getMatricule());
        } else {
            System.out.println("Operation not found for numero: " + num);
        }
    }


    public void deleteOperation(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of the operation to delete: ");
        int number = sc.nextInt();

        if (operationDAO.operationDelete(number)) {
            System.out.println("Operation deleted successfully");
        } else {
            System.out.println("Operation not found !");
        }
    }

    public void SearchAccountByOperation(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of the operation : ");
        int number = sc.nextInt();
        if (number > 0){
            Operation operation1 = operationDAO.getAccountByOperation(number);
            if (operation1 != null){
                System.out.println("Number account " + operation1.getAccount().getAccountNumber());
                System.out.println("Balance: " + operation1.getAccount().getBalance());
                System.out.println("Date creation: " +operation1.getAccount().getCreationDate());
                System.out.println("Status: " + operation1.getAccount().getStatus());
                System.out.println("Code client: " + operation1.getAccount().getClient().getCode());
                System.out.println("Matricule : " + operation1.getAccount().getEmployee().getMatricule());
            }else {
                System.out.println("Not found !");
            }
        }
    }



}

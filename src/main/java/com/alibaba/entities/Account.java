package com.alibaba.entities;

import java.time.LocalDate;
import java.util.Date;

public class Account {
    protected int accountNumber;
    protected double balance;
    protected LocalDate creationDate;
    protected AccountStatus status;
    protected Client client;
    protected Employee employee;

    //////

    public Account(int accountNumber, double balance, LocalDate creationDate, AccountStatus status, Client client, Employee employee) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.creationDate = creationDate;
        this.status = status;
        this.client = client;
        this.employee = employee;
    }

    public Account(){

    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", balance=" + balance +
                ", creationDate=" + creationDate +
                ", status=" + status +
                '}';
    }


}

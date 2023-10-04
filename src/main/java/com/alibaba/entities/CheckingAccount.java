package com.alibaba.entities;

import java.time.LocalDate;

public class CheckingAccount extends Account{
    private double overdraftLimit;

    public  CheckingAccount(){

    }

    public CheckingAccount(int accountNumber, double balance, LocalDate creationDate, AccountStatus status, Client client, Employee employee, double overdraftLimit) {
        super(accountNumber, balance, creationDate, status, client, employee);
        this.overdraftLimit = overdraftLimit;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }
}

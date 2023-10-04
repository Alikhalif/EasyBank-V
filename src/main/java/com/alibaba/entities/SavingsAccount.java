package com.alibaba.entities;

import java.time.LocalDate;

public class SavingsAccount extends Account{
    private double interestRate;

    public SavingsAccount(){

    }
    public SavingsAccount(int accountNumber, double balance, LocalDate creationDate, AccountStatus status, Client client, Employee employee, double interestRate) {
        super(accountNumber, balance, creationDate, status, client, employee);
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

}

package com.alibaba.entities;

import java.time.LocalDate;
import java.util.Date;

public class Operation {
    private int operationNumber;
    private LocalDate creationDate;
    private Double amount;
    private OperationStatus operationStatus;
    private Employee employee;
    private Account account;


    public Operation() {

    }

    public Operation(int operationNumber, LocalDate creationDate, Double amount, OperationStatus operationStatus, Employee employee, Account account) {
        this.operationNumber = operationNumber;
        this.creationDate = creationDate;
        this.amount = amount;
        this.operationStatus = operationStatus;
        this.employee = employee;
        this.account = account;
    }

    public int getOperationNumber() {
        return operationNumber;
    }

    public void setOperationNumber(int operationNumber) {
        this.operationNumber = operationNumber;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public OperationStatus getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(OperationStatus operationStatus) {
        this.operationStatus = operationStatus;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "operationNumber=" + operationNumber +
                ", creationDate=" + creationDate +
                ", amount=" + amount +
                ", operationStatus=" + operationStatus +
                ", employee=" + employee +
                ", account=" + account +
                '}';
    }

}

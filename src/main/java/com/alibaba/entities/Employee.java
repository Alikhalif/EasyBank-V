package com.alibaba.entities;

import java.time.LocalDate;
import java.util.List;

public class Employee extends Person {
    private int matricule;
    private LocalDate dateOfRecruitment;
    private List<Mission> missions;
    private List<Operation> operations;
    private List<Account> accounts;

    public Employee(String firstName, String lastName, LocalDate dateOfBirth, String email, String phoneNumber, String address, int matricule, LocalDate dateOfRecruitment, List<Mission> missions, List<Operation> operations, List<Account> accounts) {
        super(firstName, lastName, dateOfBirth, email, phoneNumber, address);
        this.matricule = matricule;
        this.dateOfRecruitment = dateOfRecruitment;
        this.missions = missions;
        this.operations = operations;
        this.accounts = accounts;
    }

    public Employee() {

    }

    public int getMatricule() {
        return matricule;
    }

    public void setMatricule(int matricule) {
        this.matricule = matricule;
    }

    public LocalDate getDateOfRecruitment() {
        return dateOfRecruitment;
    }

    public void setDateOfRecruitment(LocalDate dateOfRecruitment) {
        this.dateOfRecruitment = dateOfRecruitment;
    }

    public List<Mission> getMissions() {
        return missions;
    }

    public void setMissions(List<Mission> missions) {
        this.missions = missions;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}

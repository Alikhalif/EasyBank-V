package com.alibaba.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee extends Person{
    private int matricule;

    private LocalDate recruitmentDate;

    private String email;

    private List<Operation> operations;

    private List<Mission> missions;

    private List<Account> accounts;

    private List<Client> clients;

    private Agency agency;

    public Employee(String lastName, String firstName, LocalDate birthDate, String phone, String address,
                    int matricule, LocalDate recruitmentDate, String email,
                    List<Operation> operations, List<Mission> missions, List<Account> accounts, List<Client> clients, Agency agency) {
        super(lastName, firstName, birthDate, email, phone, address);
        this.matricule = matricule;
        this.recruitmentDate = recruitmentDate;
        this.email = email;
        this.operations = operations;
        this.missions = missions;
        this.accounts = accounts;
        this.clients = clients;
        this.agency = agency;
    }

    public Employee(String lastName, String firstName, LocalDate birthDate, String phone, String address,
                    LocalDate recruitmentDate, String email, Agency agency) {
        super(lastName, firstName, birthDate, email, phone, address);
        this.recruitmentDate = recruitmentDate;
        this.email = email;
        this.agency = agency;
    }
}
package com.alibaba.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client extends Person{
    private int code;

    private List<Account> account;

    private Employee employee;

    public Client(String lastName, String firstName, LocalDate birthDate, String email, String phone, String address, int code, List<Account> accounts, Employee employee) {
        super(lastName, firstName, birthDate, email, phone, address);
        this.code = code;
        this.account = accounts;
        this.employee = employee;
    }

    public Client(String lastName, String firstName, LocalDate birthDate, String email, String phone, String address) {
        super(lastName, firstName, birthDate, email, phone, address);
    }

    public Client(String lastName, String firstName, LocalDate birthDate,String email, String phone, String address, Employee employee) {
        super(lastName, firstName, birthDate, email, phone, address);
        this.employee = employee;
    }

}
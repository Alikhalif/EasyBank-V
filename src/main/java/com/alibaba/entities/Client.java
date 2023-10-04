package com.alibaba.entities;

import java.time.LocalDate;
import java.util.List;

public class Client extends Person{
    private int code;
    private List<Account> accounts;

    public Client(String firstName, String lastName, LocalDate dateOfBirth, String email, String phoneNumber, String address, int code, List<Account> accounts) {
        super(firstName, lastName, dateOfBirth, email, phoneNumber, address);
        this.code = code;
        this.accounts = accounts;
    }

    public Client(){

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }


    @Override
    public String toString() {
        return "Client{" +
                "code=" + code +
                ", accounts=" + accounts +
                '}';
    }
}

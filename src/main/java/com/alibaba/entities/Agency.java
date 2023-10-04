package com.alibaba.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Agency {

    private Integer code;

    private String name;

    private String address;

    private String phone;

    private List<Employee> employees;

    private List<Account> accounts;

    public Agency(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }
}

package com.alibaba.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Simulation {

    private Integer id;

    private Double monthly_payment;

    private Double borrowed_capital;

    private Integer monthly_payment_num;

    private Double result;

    private Client client;

    private Employee employee;


    public Simulation(Double borrowed_capital, Integer monthly_payment_num, Client client) {
        this.borrowed_capital = borrowed_capital;
        this.monthly_payment_num = monthly_payment_num;
        this.client = client;
    }

}


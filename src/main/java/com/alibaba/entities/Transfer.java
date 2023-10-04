package com.alibaba.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transfer {
    private Integer id;
    private LocalDate transfer_date;
    private Employee employee;
    private Agency agency;

}

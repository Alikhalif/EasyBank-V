package com.alibaba.dao;

import com.alibaba.entities.Employee;
import com.alibaba.exception.EmployeeException;

import java.util.List;
import java.util.Optional;

public interface EmployeeDAO {
    Optional<Employee> create(Employee employee);
    Optional<Employee> update(Integer matricule, Employee employee);
    boolean delete(Integer matricule);
    Optional<Employee> findByID(Integer matricule);
    List<Employee> getAll() ;
    boolean deleteAll();
    List<Employee> findByAttribute(String searchValue) throws EmployeeException;
    boolean validateMatricule(int matricule) throws EmployeeException;
    Optional<Employee> changeAgency(Employee employee, Integer agency_code);
}

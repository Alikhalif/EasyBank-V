package com.alibaba.dao;

import com.alibaba.entities.Employee;

import java.util.List;

public interface EmployeeDAO {
    void addEmployee(Employee employee);
    Employee getEmployeeByMatricule(int matricule);
    List<Employee> getAllEmployees();
    void updateEmployee(Employee employee);
    Boolean deleteEmployee(int matricule);
}

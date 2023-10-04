package com.alibaba.services;

import com.alibaba.entities.Account;
import com.alibaba.entities.Employee;
import com.alibaba.entities.Mission;
import com.alibaba.entities.Operation;
import com.alibaba.dao.daoImpl.EmployeeDAOImpl;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class EmployeeController {
    Employee emp = new Employee();
    EmployeeDAOImpl seremp = new EmployeeDAOImpl();

    public void addEmployee(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter firstname");
        String firstName = sc.next();
        System.out.println("Enter lastname");
        String lastName = sc.next();
        System.out.println("Enter date of birth");
        String dateOfBirth = sc.next();
        System.out.println("Enter email");
        String email = sc.next();
        System.out.println("Enter phone number");
        String phoneNumber = sc.next();
        System.out.println("Enter address");
        String address = sc.next();
        System.out.println("Enter date of recruitment");
        String dateOfRecruitment = sc.next();


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateOfBirthpars = LocalDate.parse(dateOfBirth, formatter);
        LocalDate dateOfRecruitmentpars = LocalDate.parse(dateOfRecruitment, formatter);

        emp.setFirstName(firstName);
        emp.setLastName(lastName);
        emp.setDateOfBirth(dateOfBirthpars);
        emp.setEmail(email);
        emp.setPhoneNumber(phoneNumber);
        emp.setAddress(address);
        emp.setDateOfRecruitment(dateOfRecruitmentpars);

        seremp.addEmployee(emp);



    }

    public void AllEmployee(){
        List<Employee> employeeList = seremp.getAllEmployees();
        for(Employee employee : employeeList){
            System.out.println(employee.getMatricule()+" | "+employee.getFirstName()+" | "+employee.getLastName()+" | "+employee.getDateOfBirth()+" | "+employee.getEmail()+" | "+employee.getPhoneNumber()+" | "+employee.getAddress()+" | "+employee.getDateOfRecruitment());
        }
    }



    public void deleteEmployee(){
        AllEmployee();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the matricule of the employee to delete: ");
        int matricule = sc.nextInt();

        //seremp.deleteEmployee(matricule);
        if (seremp.deleteEmployee(matricule)) {
            System.out.println("Employee deleted successfully");
        } else {
            System.out.println("Employee not deleted");
        }

    }

    public void updateEmployee(){
        System.out.println("Entre matricule :");
        Scanner sc = new Scanner(System.in);
        int matricule = sc.nextInt();
        Employee employee = seremp.getEmployeeByMatricule(matricule);
        System.out.println(employee);
        if(employee != null){
            System.out.println("Enter firstname");
            String firstName = sc.next();
            System.out.println("Enter lastname");
            String lastName = sc.next();
            System.out.println("Enter date of birth");
            String dateOfBirth = sc.next();
            System.out.println("Enter email");
            String email = sc.next();
            System.out.println("Enter phone number");
            String phoneNumber = sc.next();
            System.out.println("Enter address");
            String address = sc.next();
            System.out.println("Enter date of recruitment");
            String dateOfRecruitment = sc.next();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dateOfBirthpars = LocalDate.parse(dateOfBirth, formatter);
            LocalDate dateOfRecruitmentpars = LocalDate.parse(dateOfRecruitment, formatter);

            emp.setFirstName(firstName);
            emp.setLastName(lastName);
            emp.setDateOfBirth(dateOfBirthpars);
            emp.setEmail(email);
            emp.setPhoneNumber(phoneNumber);
            emp.setAddress(address);
            emp.setMatricule(employee.getMatricule());
            emp.setDateOfRecruitment(dateOfRecruitmentpars);


            seremp.updateEmployee(emp);


        }

    }

}

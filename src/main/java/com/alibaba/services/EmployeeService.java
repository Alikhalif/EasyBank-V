package com.alibaba.services;

import com.alibaba.dao.AgencyDAO;
import com.alibaba.dao.EmployeeDAO;
import com.alibaba.dao.daoImpl.AgencyDaoImpl;
import com.alibaba.dao.daoImpl.EmployeeDAOImpl;
import com.alibaba.entities.Agency;
import com.alibaba.entities.Employee;
import com.alibaba.exception.EmployeeException;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class EmployeeService {
    public static void createEmployee() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter first name: ");
            String firstName = sc.nextLine();

            System.out.println("Enter last name: ");
            String lastName = sc.nextLine();

            System.out.println("Enter birthday (yyyy-MM-dd): ");
            String birthdateStr = sc.nextLine();

            System.out.println("Enter phone number: ");
            String phone = sc.nextLine();

            System.out.println("Enter address: ");
            String address = sc.nextLine();

            System.out.println("Enter recruitment date (yyyy-MM-dd): ");
            String recruitmentDateStr = sc.nextLine();

            System.out.println("Enter email: ");
            String email = sc.nextLine();

            System.out.println("Enter agency code: ");
            Integer agency_code = sc.nextInt();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            try {
                LocalDate birthdate = LocalDate.parse(birthdateStr, formatter);
                LocalDate recruitmentDate = LocalDate.parse(recruitmentDateStr, formatter);

                int age = Period.between(birthdate, LocalDate.now()).getYears();

                if (age < 18) {
                    System.out.println("Employee must be at least 18 years old. Please try again.");
                    continue;
                }

                AgencyDAO agencyDao = new AgencyDaoImpl();
                Optional<Agency> retrievedAgency = agencyDao.findByID(agency_code);

                Employee newEmployee = new Employee(firstName, lastName, birthdate, phone, address, recruitmentDate, email, retrievedAgency.get());
                EmployeeDAO dao = new EmployeeDAOImpl();

                dao.create(newEmployee);
                System.out.println("Employee created successfully.");
                break;

            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format. Please try again.");
            }
        }
    }

    public static void updateEmployee() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter the matricule of the employee you want to update (or enter 'q' to quit): ");
            String matriculeInput = sc.nextLine();

            if (matriculeInput.equalsIgnoreCase("q")) {
                break;
            }

            try {
                int matricule = Integer.parseInt(matriculeInput);
                EmployeeDAO dao = new EmployeeDAOImpl();
                Optional<Employee> existingEmployee = dao.findByID(matricule);

                if (existingEmployee.isPresent()) {
                    Employee employeeToUpdate = existingEmployee.get();

                    System.out.println("Enter new first name (leave empty to keep the current value): ");
                    String newFirstName = sc.nextLine();
                    if (!newFirstName.isEmpty()) {
                        employeeToUpdate.setFirstName(newFirstName);
                    }

                    System.out.println("Enter new last name (leave empty to keep the current value): ");
                    String newLastName = sc.nextLine();
                    if (!newLastName.isEmpty()) {
                        employeeToUpdate.setLastName(newLastName);
                    }

                    System.out.println("Enter new birthday (yyyy-MM-dd) (leave empty to keep the current value): ");
                    String newBirthdateStr = sc.nextLine();
                    if (!newBirthdateStr.isEmpty()) {
                        LocalDate newBirthdate = LocalDate.parse(newBirthdateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        employeeToUpdate.setDateOfBirth(newBirthdate);
                    }

                    System.out.println("Enter new phone number (leave empty to keep the current value): ");
                    String newPhone = sc.nextLine();
                    if (!newPhone.isEmpty()) {
                        employeeToUpdate.setPhoneNumber(newPhone);
                    }

                    System.out.println("Enter new address (leave empty to keep the current value): ");
                    String newAddress = sc.nextLine();
                    if (!newAddress.isEmpty()) {
                        employeeToUpdate.setAddress(newAddress);
                    }

                    System.out.println("Enter new recruitment date (yyyy-MM-dd) (leave empty to keep the current value): ");
                    String newRecruitmentDateStr = sc.nextLine();
                    if (!newRecruitmentDateStr.isEmpty()) {
                        LocalDate newRecruitmentDate = LocalDate.parse(newRecruitmentDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        employeeToUpdate.setRecruitmentDate(newRecruitmentDate);
                    }

                    System.out.println("Enter new email (leave empty to keep the current value): ");
                    String newEmail = sc.nextLine();
                    if (!newEmail.isEmpty()) {
                        employeeToUpdate.setEmail(newEmail);
                    }

                    dao.update(matricule, employeeToUpdate);
                    System.out.println("Employee updated successfully.");
                    break;
                } else {
                    System.out.println("Employee not found with matricule: " + matricule);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid matricule or 'q' to quit.");
            }
        }
    }

    public static void deleteEmployee() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter the matricule of the employee you want to delete (or enter 'q' to quit): ");
            String matriculeInput = sc.nextLine();

            if (matriculeInput.equalsIgnoreCase("q")) {
                break;
            }

            try {
                int matricule = Integer.parseInt(matriculeInput);
                EmployeeDAO dao = new EmployeeDAOImpl();

                if (dao.delete(matricule)) {
                    System.out.println("Employee with matricule " + matricule + " deleted successfully.");
                    break;
                } else {
                    System.out.println("Employee not found with matricule: " + matricule);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid matricule or 'q' to quit.");
            }
        }
    }

    public static void getEmployeeByMatricule() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter the matricule of the employee you want to retrieve (or enter 'q' to quit): ");
            String matriculeInput = sc.nextLine();

            if (matriculeInput.equalsIgnoreCase("q")) {
                break;
            }

            try {
                int matricule = Integer.parseInt(matriculeInput);
                EmployeeDAO dao = new EmployeeDAOImpl();
                Optional<Employee> existingEmployee = dao.findByID(matricule);

                if (existingEmployee.isPresent()) {
                    Employee employee = existingEmployee.get();
                    System.out.println("Employee found:");
                    System.out.println("Matricule: " + employee.getMatricule());
                    System.out.println("First Name: " + employee.getFirstName());
                    System.out.println("Last Name: " + employee.getLastName());
                    System.out.println("Birthdate: " + employee.getDateOfBirth());
                    System.out.println("Phone: " + employee.getPhoneNumber());
                    System.out.println("Address: " + employee.getAddress());
                    System.out.println("Recruitment Date: " + employee.getRecruitmentDate());
                    System.out.println("Email: " + employee.getEmail());
                    System.out.println("Agency name: " + employee.getAgency().getName());
                    System.out.println("Agency address: " + employee.getAgency().getAddress());
                    System.out.println("Agency phone: " + employee.getAgency().getPhone());
                    break;
                } else {
                    System.out.println("Employee not found with matricule: " + matricule);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid matricule or 'q' to quit.");
            }
        }
    }

    public static void getAllEmployees() {
        EmployeeDAO dao = new EmployeeDAOImpl();

        List<Employee> employees = dao.getAll();

        if (!employees.isEmpty()) {
            System.out.println("List of all employees:");
            for (Employee employee : employees) {
                System.out.println("Matricule: " + employee.getMatricule());
                System.out.println("First Name: " + employee.getFirstName());
                System.out.println("Last Name: " + employee.getLastName());
                System.out.println("Birthdate: " + employee.getDateOfBirth());
                System.out.println("Phone: " + employee.getPhoneNumber());
                System.out.println("Address: " + employee.getAddress());
                System.out.println("Recruitment Date: " + employee.getRecruitmentDate());
                System.out.println("Email: " + employee.getEmail());
                System.out.println("---------------------------");
            }
        } else {
            System.out.println("No employees found.");
        }
    }

    public static void findEmployeeByAttribute() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the value you want to search for: ");
        String searchValue = sc.nextLine();

        EmployeeDAO dao = new EmployeeDAOImpl();

        try {
            List<Employee> foundEmployees = dao.findByAttribute(searchValue);

            if (!foundEmployees.isEmpty()) {
                System.out.println("Employees found:");
                for (Employee employee : foundEmployees) {
                    System.out.println("Matricule: " + employee.getMatricule());
                    System.out.println("First Name: " + employee.getFirstName());
                    System.out.println("Last Name: " + employee.getLastName());
                    System.out.println("Birthdate: " + employee.getDateOfBirth());
                    System.out.println("Phone: " + employee.getPhoneNumber());
                    System.out.println("Address: " + employee.getAddress());
                    System.out.println("Recruitment Date: " + employee.getRecruitmentDate());
                    System.out.println("Email: " + employee.getEmail());
                    System.out.println("---------------------------");
                }
            } else {
                System.out.println("No employees found with the specified value.");
            }
        } catch (EmployeeException e) {
            System.out.println("Error searching for employees: " + e.getMessage());
        }
    }

    public static int validateMatricule() {
        int matricule = 0;

        while (matricule == 0) {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.println("Enter matricule number:");
                matricule = Integer.parseInt(sc.nextLine());

                EmployeeDAO dao = new EmployeeDAOImpl();

                if (dao.validateMatricule(matricule)) {
                    System.out.println("Matricule number is valid!");
                } else {
                    System.out.println("Matricule number is not valid. Please try again.");
                    matricule = 0;
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid Input");
            } catch (EmployeeException e) {
                throw new RuntimeException(e);
            }
        }

        return matricule;
    }
}

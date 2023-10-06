package com.alibaba.services;

import com.alibaba.dao.ClientDAO;
import com.alibaba.dao.daoImpl.ClientDAOImpl;
import com.alibaba.entities.Client;
import com.alibaba.entities.Employee;
import com.alibaba.exception.ClientException;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ClientService {
    public static void createClient() {
        Scanner sc = new Scanner(System.in);

        int matricule = EmployeeService.validateMatricule();

        if (matricule > 0) {
            while (true) {
                System.out.println("Enter client's first name: ");
                String firstName = sc.nextLine();

                System.out.println("Enter client's last name: ");
                String lastName = sc.nextLine();

                System.out.println("Enter client's birthday (yyyy-MM-dd): ");
                String birthdateStr = sc.nextLine();

                System.out.println("Enter client's phone number: ");
                String phone = sc.nextLine();

                System.out.println("Enter client's address: ");
                String address = sc.nextLine();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                try {
                    LocalDate birthdate = LocalDate.parse(birthdateStr, formatter);

                    int age = Period.between(birthdate, LocalDate.now()).getYears();

                    String email = "exemple@gmail.com";

                    if (age < 18) {
                        System.out.println("Client must be at least 18 years old. Please try again.");
                        continue;
                    }

                    Employee employee = new Employee();
                    employee.setMatricule(matricule);

                    Client newClient = new Client(firstName, lastName, birthdate, email, phone, address, employee);
                    ClientDAO dao = new ClientDAOImpl();

                    dao.create(newClient);
                    System.out.println("Client created successfully.");
                    break;

                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format. Please try again.");
                }
            }
        }
    }

    public static void updateClient() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter the code of the client you want to update (or enter 'q' to quit): ");
            String codeInput = sc.nextLine();

            if (codeInput.equalsIgnoreCase("q")) {
                break;
            }

            try {
                int code = Integer.parseInt(codeInput);
                ClientDAO dao = new ClientDAOImpl();
                Optional<Client> existingClient = dao.findByID(code);

                if (existingClient.isPresent()) {
                    Client clientToUpdate = existingClient.get();

                    System.out.println("Enter new first name (leave empty to keep the current value): ");
                    String newFirstName = sc.nextLine();
                    if (!newFirstName.isEmpty()) {
                        clientToUpdate.setFirstName(newFirstName);
                    }

                    System.out.println("Enter new last name (leave empty to keep the current value): ");
                    String newLastName = sc.nextLine();
                    if (!newLastName.isEmpty()) {
                        clientToUpdate.setLastName(newLastName);
                    }

                    System.out.println("Enter new birthday (yyyy-MM-dd) (leave empty to keep the current value): ");
                    String newBirthdateStr = sc.nextLine();
                    if (!newBirthdateStr.isEmpty()) {
                        LocalDate newBirthdate = LocalDate.parse(newBirthdateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        clientToUpdate.setDateOfBirth(newBirthdate);
                    }

                    System.out.println("Enter new phone number (leave empty to keep the current value): ");
                    String newPhone = sc.nextLine();
                    if (!newPhone.isEmpty()) {
                        clientToUpdate.setPhoneNumber(newPhone);
                    }

                    System.out.println("Enter new address (leave empty to keep the current value): ");
                    String newAddress = sc.nextLine();
                    if (!newAddress.isEmpty()) {
                        clientToUpdate.setAddress(newAddress);
                    }

                    dao.update(code, clientToUpdate);
                    System.out.println("Client updated successfully.");
                    break;
                } else {
                    System.out.println("Client not found with code: " + code);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid code or 'q' to quit.");
            }
        }
    }

    public static void deleteClient() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter the code of the client you want to delete (or enter 'q' to quit): ");
            String codeInput = sc.nextLine();

            if (codeInput.equalsIgnoreCase("q")) {
                break;
            }

            try {
                int code = Integer.parseInt(codeInput);
                ClientDAO dao = new ClientDAOImpl();

                if (dao.delete(code)) {
                    System.out.println("Client with code " + code + " deleted successfully.");
                    break;
                } else {
                    System.out.println("Client not found with code: " + code);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid code or 'q' to quit.");
            }
        }
    }

    public static void getClientByCode() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter the code of the client you want to retrieve (or enter 'q' to quit): ");
            String codeInput = sc.nextLine();

            if (codeInput.equalsIgnoreCase("q")) {
                break;
            }

            try {
                int code = Integer.parseInt(codeInput);
                ClientDAO dao = new ClientDAOImpl();
                Optional<Client> existingClient = dao.findByID(code);

                if (existingClient.isPresent()) {
                    Client client = existingClient.get();
                    System.out.println("Client found:");
                    System.out.println("Code: " + client.getCode());
                    System.out.println("First Name: " + client.getFirstName());
                    System.out.println("Last Name: " + client.getLastName());
                    System.out.println("Birthdate: " + client.getDateOfBirth());
                    System.out.println("Phone: " + client.getPhoneNumber());
                    System.out.println("Address: " + client.getAddress());
                    System.out.println("Responsible Employee: " + client.getEmployee().getLastName());
                    break;
                } else {
                    System.out.println("Client not found with code: " + code);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid code or 'q' to quit.");
            }
        }
    }

    public static void getAllClients() {
        ClientDAO dao = new ClientDAOImpl();

        List<Client> clients = dao.getAll();

        if (!clients.isEmpty()) {
            System.out.println("List of all clients:");
            for (Client client : clients) {
                System.out.println("Code: " + client.getCode());
                System.out.println("First Name: " + client.getFirstName());
                System.out.println("Last Name: " + client.getLastName());
                System.out.println("Birthdate: " + client.getDateOfBirth());
                System.out.println("Phone: " + client.getPhoneNumber());
                System.out.println("Address: " + client.getAddress());
                System.out.println("---------------------------");
            }
        } else {
            System.out.println("No clients found.");
        }
    }

    public static void findClientByAttribute() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the value you want to search for: ");
        String searchValue = sc.nextLine();

        ClientDAO dao = new ClientDAOImpl();

        try {
            List<Client> foundClients = dao.findByAttribute(searchValue);

            if (!foundClients.isEmpty()) {
                System.out.println("Clients found:");
                for (Client client : foundClients) {
                    System.out.println("Code: " + client.getCode());
                    System.out.println("First Name: " + client.getFirstName());
                    System.out.println("Last Name: " + client.getLastName());
                    System.out.println("Birthdate: " + client.getDateOfBirth());
                    System.out.println("Phone: " + client.getPhoneNumber());
                    System.out.println("Address: " + client.getAddress());
                    System.out.println("---------------------------");
                }
            } else {
                System.out.println("No clients found with the specified value.");
            }
        } catch (ClientException e) {
            System.out.println("Error searching for clients: " + e.getMessage());
        }
    }
}

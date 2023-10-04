package com.alibaba.services;

import com.alibaba.entities.Client;
import com.alibaba.entities.Employee;
import com.alibaba.dao.daoImpl.ClientDAOImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class ClientController {
    Client client = new Client();
    Employee employee=new Employee();
    ClientDAOImpl serclt = new ClientDAOImpl();
    public void addClient(){
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateOfBirthpars = LocalDate.parse(dateOfBirth, formatter);

        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setDateOfBirth(dateOfBirthpars);
        client.setEmail(email);
        client.setPhoneNumber(phoneNumber);
        client.setAddress(address);

        serclt.addClient(client);
    }

    public void getClientByCode(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter address");
        int code = sc.nextInt();
        Client client = serclt.getClientByCode(code);

        if (client != null) {
            System.out.println("Client found:");
            System.out.println("Code: " + client.getCode());
            System.out.println("FirstName: " + client.getFirstName());
            System.out.println("LastName: " + client.getLastName());
            System.out.println("Date of birth: " + client.getDateOfBirth());
            System.out.println("Email: " + client.getEmail());
            System.out.println("Phone: " + client.getPhoneNumber());
            System.out.println("Address: " + client.getAddress());
        } else {
            System.out.println("Client not found for code: " + code);
        }
    }

    public void getAllClient(){
        List<Client> clientList = serclt.getAllClients();
        for(Client client1 : clientList){
            System.out.println(client1.getCode()+" | "+client1.getFirstName()+" | "+client1.getLastName()+" | "+client1.getDateOfBirth()+" | "+client1.getEmail()+" | "+client1.getPhoneNumber()+" | "+client1.getAddress());
        }
    }

    public void deleteClient(){
        getAllClient();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the code of the client to delete: ");
        int code = sc.nextInt();

        if (serclt.deleteClient(code)) {
            System.out.println("Employee deleted successfully");
        } else {
            System.out.println("Employee not deleted");
        }
    }

    public void updateEmployee(){
        System.out.println("Entre code client :");
        Scanner sc = new Scanner(System.in);
        int code = sc.nextInt();
        Client client1 = serclt.getClientByCode(code);
        System.out.println(client1);
        if(client1 != null){
            System.out.println("kayn   !!");
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

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dateOfBirthpars = LocalDate.parse(dateOfBirth, formatter);

            client.setFirstName(firstName);
            client.setLastName(lastName);
            client.setDateOfBirth(dateOfBirthpars);
            client.setEmail(email);
            client.setPhoneNumber(phoneNumber);
            client.setAddress(address);
            client.setCode(client1.getCode());

            serclt.updateClient(client);

        }

    }
}

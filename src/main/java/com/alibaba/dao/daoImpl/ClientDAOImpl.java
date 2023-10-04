package com.alibaba.dao.daoImpl;

import com.alibaba.connexion.DB;
import com.alibaba.dao.ClientDAO;
import com.alibaba.entities.Client;
import com.alibaba.entities.Employee;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClientDAOImpl implements ClientDAO {
    private Connection connection = DB.getConnection();
    Client client = new Client();

    @Override
    public void addClient(Client client) {
        try {
            String SQL = "INSERT INTO clients (firstname, lastname, birthDate, email, phone, address) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = connection.prepareStatement(SQL);
            pstmt.setString(1, client.getFirstName());
            pstmt.setString(2, client.getLastName());
            pstmt.setDate(3, java.sql.Date.valueOf(client.getDateOfBirth()));

            pstmt.setString(4, client.getEmail());
            pstmt.setString(5, client.getPhoneNumber());
            pstmt.setString(6, client.getAddress());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Client getClientByCode(int code) {
        try{
            String sql = "SELECT * FROM clients WHERE code = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,code);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int code1 = resultSet.getInt("code");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                LocalDate birthDate = resultSet.getDate("birthDate").toLocalDate();
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phone");
                String address = resultSet.getString("address");

                // Create and return an Employee object
                return new Client(firstName, lastName, birthDate, email, phoneNumber, address, code1, null);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        try{
            String sql = "SELECT * FROM clients";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
//            firstName, lastName, birthDate, email, phone, address
            while (resultSet.next()){
                Client client = new Client();
                client.setCode(resultSet.getInt("code"));
                client.setFirstName(resultSet.getString("firstName"));
                client.setLastName(resultSet.getString("lastName"));
                Date birthDate = resultSet.getDate("birthDate");
                if (birthDate != null) {
                    client.setDateOfBirth(birthDate.toLocalDate());
                }
                client.setEmail(resultSet.getString("email"));
                client.setPhoneNumber(resultSet.getString("phone"));
                client.setAddress(resultSet.getString("address"));
                clients.add(client);
            }
            return clients;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean updateClient(Client client) {
        try {
            String sql = "UPDATE clients SET firstName=?, lastName=?, birthDate=?, email=?, phone=?, address=? WHERE code = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, client.getFirstName());
            preparedStatement.setString(2, client.getLastName());
            preparedStatement.setDate(3, java.sql.Date.valueOf(client.getDateOfBirth()));
            preparedStatement.setString(4, client.getEmail());
            preparedStatement.setString(5, client.getPhoneNumber());
            preparedStatement.setString(6, client.getAddress());
            preparedStatement.setInt(7, client.getCode());
            preparedStatement.executeUpdate();

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean deleteClient(int code) {
        try {
            String sql = "DELETE FROM clients WHERE code = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, code);
            int affectedRows = preparedStatement.executeUpdate();

            System.out.println("Number of affected rows: " + affectedRows);

            if(affectedRows > 0){
                return true;
            }else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

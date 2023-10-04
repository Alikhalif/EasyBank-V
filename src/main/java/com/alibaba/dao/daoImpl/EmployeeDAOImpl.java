package com.alibaba.dao.daoImpl;
import com.alibaba.connexion.DB;
import com.alibaba.dao.EmployeeDAO;
import com.alibaba.entities.Employee;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    private Connection connection = DB.getConnection();
    Employee emp = new Employee();

    @Override
    public void addEmployee(Employee employee) {
        try {
            String SQL = "INSERT INTO employees (firstName, lastName, birthDate, email, phone, address, recruitmentDate) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = connection.prepareStatement(SQL);
            pstmt.setString(1, employee.getFirstName());
            pstmt.setString(2, employee.getLastName());
            pstmt.setDate(3, java.sql.Date.valueOf(employee.getDateOfBirth()));
            pstmt.setString(4, employee.getEmail());
            pstmt.setString(5, employee.getPhoneNumber());
            pstmt.setString(6, employee.getAddress());
            pstmt.setDate(7, java.sql.Date.valueOf(employee.getDateOfRecruitment()));

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Employee getEmployeeByMatricule(int matricule) {
        try{
            String sql = "SELECT * FROM employees WHERE matricule = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,matricule);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int matricul = resultSet.getInt("matricule");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                LocalDate birthDate = resultSet.getDate("birthDate").toLocalDate();
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phone");
                String address = resultSet.getString("address");
                LocalDate recruitmentDate = resultSet.getDate("recruitmentDate").toLocalDate();

                // Create and return an Employee object
                return new Employee(firstName, lastName, birthDate, email, phoneNumber, address, matricul, recruitmentDate, null, null, null);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        try{
            String sql = "SELECT * FROM employees";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
//            firstName, lastName, birthDate, email, phone, address, recruitmentDate
            while (resultSet.next()){
                Employee emp = new Employee();
                emp.setMatricule(resultSet.getInt("matricule"));
                emp.setFirstName(resultSet.getString("firstName"));
                emp.setLastName(resultSet.getString("lastName"));
                emp.setDateOfBirth(resultSet.getDate("birthDate").toLocalDate());
                emp.setEmail(resultSet.getString("email"));
                emp.setPhoneNumber(resultSet.getString("phone"));
                emp.setAddress(resultSet.getString("address"));
                emp.setDateOfRecruitment(resultSet.getDate("recruitmentDate").toLocalDate());
                employees.add(emp);
            }
            return employees;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
        // Implement the logic to retrieve all employees from the database
    }

    @Override
    public void updateEmployee(Employee employee) {
        try {
            String sql = "UPDATE employees SET firstName=?, lastName=?, birthDate=?, email=?, phone=?, address=?, recruitmentDate=? WHERE matricule = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setDate(3, java.sql.Date.valueOf(employee.getDateOfBirth()));
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setString(5, employee.getPhoneNumber());
            preparedStatement.setString(6, employee.getAddress());
            preparedStatement.setDate(7, java.sql.Date.valueOf(employee.getDateOfRecruitment()));
            preparedStatement.setInt(8, employee.getMatricule());  // Corrected position

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Boolean deleteEmployee(int matricule) {
        try {
            String sql = "DELETE FROM employees WHERE matricule = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, matricule);
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

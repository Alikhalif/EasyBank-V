package com.alibaba.dao.daoImpl;
import com.alibaba.connexion.DB;
import com.alibaba.dao.EmployeeDAO;
import com.alibaba.entities.Employee;
import com.alibaba.exception.EmployeeException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeDAOImpl implements EmployeeDAO {
    private Connection connection;

    public EmployeeDAOImpl() {
        connection = DB.getConnection();
    }

    public EmployeeDAOImpl(Connection connection) {
        connection = connection;
    }

    @Override
    public Optional<Employee> create(Employee employee) {
        String insertSQL = "INSERT INTO employees (firstName, lastName, birthDate, phone, address, recruitmentDate, email, agency_code) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING matricule";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setDate(3, java.sql.Date.valueOf(employee.getDateOfBirth()));
            preparedStatement.setString(4, employee.getPhoneNumber());
            preparedStatement.setString(5, employee.getAddress());
            preparedStatement.setDate(6, java.sql.Date.valueOf(employee.getRecruitmentDate()));
            preparedStatement.setString(7, employee.getEmail());
            preparedStatement.setInt(8, employee.getAgency().getCode());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new EmployeeException("Creating employee failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int matricule = generatedKeys.getInt(1);
                    employee.setMatricule(matricule);
                } else {
                    throw new EmployeeException("Creating employee failed, no ID obtained.");
                }
            }

            return Optional.of(employee);
        } catch (SQLException | EmployeeException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Employee> update(Integer matricule, Employee employee) {
        String updateSQL = "UPDATE employees " +
                "SET firstName = ?, lastName = ?, birthDate = ?, phone = ?, address = ?, " +
                "recruitmentDate = ?, email = ? " +
                "WHERE matricule = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setDate(3, java.sql.Date.valueOf(employee.getDateOfBirth()));
            preparedStatement.setString(4, employee.getPhoneNumber());
            preparedStatement.setString(5, employee.getAddress());
            preparedStatement.setDate(6, java.sql.Date.valueOf(employee.getRecruitmentDate()));
            preparedStatement.setString(7, employee.getEmail());
            preparedStatement.setInt(8, matricule);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new EmployeeException("Updating employee failed, no rows affected.");
            }
            return Optional.of(employee);
        } catch (SQLException | EmployeeException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(Integer matricule) {
        String deleteSQL = "DELETE FROM employees WHERE matricule = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, matricule);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<Employee> findByID(Integer matricule) {
        String selectSQL = "SELECT * FROM employees WHERE matricule = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, matricule);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Employee employee = new Employee();
                    employee.setMatricule(resultSet.getInt("matricule"));
                    employee.setFirstName(resultSet.getString("firstName"));
                    employee.setLastName(resultSet.getString("lastName"));
                    employee.setDateOfBirth(resultSet.getDate("birthDate").toLocalDate());
                    employee.setPhoneNumber(resultSet.getString("phone"));
                    employee.setAddress(resultSet.getString("address"));
                    employee.setRecruitmentDate(resultSet.getDate("recruitmentDate").toLocalDate());
                    employee.setEmail(resultSet.getString("email"));
                    employee.setAgency(new AgencyDaoImpl().findByID(resultSet.getInt("agency_code")).get());

                    return Optional.of(employee);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> employees = new ArrayList<>();
        String selectAllSQL = "SELECT * FROM employees";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectAllSQL)) {

            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setMatricule(resultSet.getInt("matricule"));
                employee.setFirstName(resultSet.getString("firstName"));
                employee.setLastName(resultSet.getString("lastName"));
                employee.setDateOfBirth(resultSet.getDate("birthDate").toLocalDate());
                employee.setPhoneNumber(resultSet.getString("phone"));
                employee.setAddress(resultSet.getString("address"));
                employee.setRecruitmentDate(resultSet.getDate("recruitmentDate").toLocalDate());
                employee.setEmail(resultSet.getString("email"));

                employees.add(employee);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    @Override
    public boolean deleteAll() {
        boolean deleted = false;
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM employees");
            int rows = ps.executeUpdate();

            if (rows > 0) {
                deleted = true;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return deleted;
    }

    @Override
    public List<Employee> findByAttribute(String searchValue) throws EmployeeException {
        List<Employee> employees = new ArrayList<>();

        String selectByAttributeSQL = "SELECT * FROM employees WHERE " +
                "matricule::TEXT LIKE ? OR " +
                "firstName LIKE ? OR " +
                "lastName LIKE ? OR " +
                "birthDate::TEXT LIKE ? OR " +
                "phone LIKE ? OR " +
                "address LIKE ? OR " +
                "recruitmentDate::TEXT LIKE ? OR " +
                "email LIKE ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectByAttributeSQL)) {
            String wildcardSearchValue = "%" + searchValue + "%";
            for (int i = 1; i <= 8; i++) {
                preparedStatement.setString(i, wildcardSearchValue);
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Employee employee = new Employee();
                    employee.setMatricule(resultSet.getInt("matricule"));
                    employee.setFirstName(resultSet.getString("firstName"));
                    employee.setLastName(resultSet.getString("lastName"));
                    employee.setDateOfBirth(resultSet.getDate("birthDate").toLocalDate());
                    employee.setPhoneNumber(resultSet.getString("phone"));
                    employee.setAddress(resultSet.getString("address"));
                    employee.setRecruitmentDate(resultSet.getDate("recruitmentDate").toLocalDate());
                    employee.setEmail(resultSet.getString("email"));

                    employees.add(employee);
                }
            }
        } catch (SQLException e) {
            throw new EmployeeException("Error searching for employees by attribute.");
        }

        return employees;
    }

    @Override
    public boolean validateMatricule(int matricule) throws EmployeeException{
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM employees WHERE matricule = ?");
            ps.setInt(1, matricule);
            ResultSet rs = ps.executeQuery();

            return rs.next();

        }catch (SQLException e) {
            throw new EmployeeException(e.getMessage());
        }
    }

    @Override
    public Optional<Employee> changeAgency(Employee employee, Integer agency_code) {
        String updateSQL = "UPDATE employees SET agency_code = ? WHERE matricule = ?";
        try (PreparedStatement ps = connection.prepareStatement(updateSQL)){
            ps.setInt(1, agency_code);
            ps.setInt(2, employee.getMatricule());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new Exception("Updating employee failed, no rows affected.");
            }

            return Optional.of(employee);
        }catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }

    }

}

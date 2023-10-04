package com.alibaba.dao.daoImpl;

import com.alibaba.connexion.DB;
import com.alibaba.dao.MissionDAO;
import com.alibaba.dao.OperationDAO;
import com.alibaba.entities.Employee;
import com.alibaba.entities.Mission;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MissionDAOImpl implements MissionDAO {
    Connection connection = DB.getConnection();
    Mission mission = new Mission();

    @Override
    public void createMission(Mission mission){
        try {
            String SQL = "INSERT INTO missions (nom, description) VALUES (?, ?)";

            PreparedStatement pstmt = connection.prepareStatement(SQL);
            pstmt.setString(1, mission.getName());
            pstmt.setString(2, mission.getDescription());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Mission> getAllMission() {
        List<Mission> missions = new ArrayList<>();
        try{
            String sql = "SELECT * FROM missions";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
//            firstName, lastName, birthDate, email, phone, address, recruitmentDate
            while (resultSet.next()){
                Mission mission = new Mission();
                mission.setCode(resultSet.getInt("code"));
                mission.setName(resultSet.getString("nom"));
                mission.setDescription(resultSet.getString("description"));
                missions.add(mission);
            }
            return missions;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
        // Implement the logic to retrieve all employees from the database
    }


    @Override
    public Boolean deleteMission(int code) {
        try {
            String sql = "DELETE FROM missions WHERE code = ?";
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

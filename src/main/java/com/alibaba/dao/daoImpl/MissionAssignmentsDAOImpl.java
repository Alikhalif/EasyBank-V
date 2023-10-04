package com.alibaba.dao.daoImpl;

import com.alibaba.connexion.DB;
import com.alibaba.dao.MissionEmployeeDAO;
import com.alibaba.entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MissionAssignmentsDAOImpl implements MissionEmployeeDAO {
    Connection connection = DB.getConnection();
    MissionAssignments missionAssignments = new MissionAssignments();
    @Override
    public void MissionEmployee(MissionAssignments missionAssignments){
        try{
            String SQL = "INSERT INTO missionAssignments (debut_date, end_date, employee_matricule, mission_code) VALUES (?, ?, ?, ?)";

            PreparedStatement pstmt = connection.prepareStatement(SQL);
            pstmt.setTimestamp(1, Timestamp.valueOf(missionAssignments.getStartDate()));
            pstmt.setTimestamp(2, Timestamp.valueOf(missionAssignments.getEndDate()));
            pstmt.setInt(3, missionAssignments.getEmployee().getMatricule());
            pstmt.setInt(4, missionAssignments.getMission().getCode());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public List<MissionAssignments> getMissionEmployee(int matricule) {
        List<MissionAssignments> missionAssignmentsList = new ArrayList<>();
        try{
            String sql = "SELECT * FROM missionAssignments MA join missions MS on MA.mission_code = MS.code WHERE MA.employee_matricule = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,matricule);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                MissionAssignments assignments = new MissionAssignments();

                assignments.setStartDate(resultSet.getTimestamp("debut_date").toLocalDateTime());
                assignments.setEndDate(resultSet.getTimestamp("end_date").toLocalDateTime());

                Employee employee = new Employee();
                employee.setMatricule(resultSet.getInt("employee_matricule"));
                assignments.setEmployee(employee);

                Mission mission = new Mission();
                mission.setCode(resultSet.getInt("mission_code"));
                mission.setName(resultSet.getString("nom"));
                assignments.setMission(mission);

                missionAssignmentsList.add(assignments);

            }
            return missionAssignmentsList;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public Map<Integer, MissionAssignments> getCountMission() {
        Map<Integer,MissionAssignments> missionAssignmentsList = new HashMap<>();
        try{
            String sql = "SELECT ma.mission_code, m.nom, COUNT(*) AS count_mission\n" +
                    "FROM missionAssignments ma\n" +
                    "JOIN missions m ON ma.mission_code = m.code\n" +
                    "GROUP BY ma.mission_code, m.nom \n";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                MissionAssignments assignments = new MissionAssignments();

                Mission mission = new Mission();

                int count_mission = resultSet.getInt("count_mission");
                mission.setCode(resultSet.getInt("mission_code"));
                mission.setName(resultSet.getString("nom"));
                assignments.setMission(mission);

                missionAssignmentsList.put(count_mission,assignments);

            }
            return missionAssignmentsList;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }





}

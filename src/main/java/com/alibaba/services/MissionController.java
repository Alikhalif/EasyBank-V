package com.alibaba.services;


import com.alibaba.entities.Employee;
import com.alibaba.entities.Mission;
import com.alibaba.dao.daoImpl.MissionDAOImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class MissionController {

    Mission mission = new Mission();
    MissionDAOImpl missionDAO = new MissionDAOImpl();

    public void addMission(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Name mission");
        String name = sc.nextLine();
        System.out.println("Enter Description");
        String desc = sc.nextLine();

        mission.setName(name);
        mission.setDescription(desc);

        missionDAO.createMission(mission);
    }



    public void AllMission(){
        List<Mission> missions = missionDAO.getAllMission();
        for(Mission mission1 : missions){
            System.out.println(mission1.getCode()+" | "+mission1.getName()+" | "+mission1.getDescription());
        }
    }

    public void deleteMission(){
        AllMission();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the code of the mission to delete: ");
        int code = sc.nextInt();

        //seremp.deleteEmployee(matricule);
        if (missionDAO.deleteMission(code)) {
            System.out.println("Mission deleted successfully");
        } else {
            System.out.println("Mission not deleted");
        }

    }
}

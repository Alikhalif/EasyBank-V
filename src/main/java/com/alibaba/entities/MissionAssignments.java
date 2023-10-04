package com.alibaba.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MissionAssignments {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Employee employee;
    private Mission mission;

    public MissionAssignments() {

    }

    public MissionAssignments(LocalDateTime startDate, LocalDateTime endDate, Employee employee, Mission mission) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.employee = employee;
        this.mission = mission;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }


    @Override
    public String toString() {
        return "MissionAssignments{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", employee=" + employee +
                ", mission=" + mission +
                '}';
    }
}

package com.alibaba.dao;

import com.alibaba.entities.Mission;

import java.util.List;

public interface MissionDAO {
    void createMission(Mission mission);
    List<Mission> getAllMission();
    Boolean deleteMission(int code);

}

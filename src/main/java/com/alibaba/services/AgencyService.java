package com.alibaba.services;

import com.alibaba.dao.daoImpl.AgencyDaoImpl;
import com.alibaba.entities.Agency;

import java.util.List;
import java.util.Optional;

public class AgencyService {
    private AgencyDaoImpl agencyDao;

    public AgencyService(AgencyDaoImpl agencyDao) {
        this.agencyDao = agencyDao;
    }

    public void createAgency(Agency agency) {
        Optional<Agency> optionalAgency = agencyDao.create(agency);
        if (optionalAgency.isPresent()) {
            System.out.println("Agency created successfully.");
        }
    }

    public Agency getAgencyByID(Integer code) {
        Optional<Agency> retrievedAgency = agencyDao.findByID(code);
        if (retrievedAgency.isPresent()) {
            Agency existingAgency = retrievedAgency.get();
            System.out.println("Mission found:");
            System.out.println("Code: " + existingAgency.getCode());
            System.out.println("Name: " + existingAgency.getName());
            System.out.println("Description: " + existingAgency.getAddress());
            System.out.println("Phone Number: " + existingAgency.getPhone());
            return existingAgency;
        }else {
            System.out.println("Mission not found with code: " + code);
            return null;
        }
    }

    public boolean deleteAgencyByCode(Integer code) {
        return agencyDao.delete(code);
    }

    public boolean updateAgency(Integer code, Agency agency) {
        Optional<Agency> retrievedAgency = agencyDao.findByID(code);
        if (retrievedAgency.isPresent()) {
            Optional<Agency> updatedAgency = agencyDao.update(code, agency);
            return updatedAgency.isPresent();
        }else {
            return false;
        }
    }

    public List<Agency> getAllAgencies() {
        return agencyDao.getAll();
    }

    public List<Agency> getAgenciesByAddress(String address) {
        return agencyDao.findByAddress(address);
    }

    //    public Agency getAgencyByEmployee(Employee employee) {
    //        return agencyDao.findAgencyByEmployee(employee);
    //    }
}

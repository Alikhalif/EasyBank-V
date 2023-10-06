package com.alibaba.services;

import com.alibaba.dao.daoImpl.EmployeeDAOImpl;
import com.alibaba.entities.Simulation;

public class SimulationService {

    private EmployeeDAOImpl employeeDao;
    public SimulationService(EmployeeDAOImpl employeeDao) {
        this.employeeDao = employeeDao;
    }
    public double createSimulation(Simulation simulation) {
        double result = 0;
        try {
            if (employeeDao.findByID(simulation.getEmployee().getMatricule()).isPresent()) {
                if (simulation.getMonthly_payment().toString().isEmpty() || simulation.getBorrowed_capital().toString().isEmpty()) {
                    System.out.println("All fields needs to be mentioned");
                }else {
                    result = (simulation.getMonthly_payment() * 1.2/12) / Math.pow((1 - (1 + 1.2/12)), - simulation.getMonthly_payment_num());
                }
            }


        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

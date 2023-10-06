package com.alibaba;
import com.alibaba.dao.daoImpl.AgencyDaoImpl;
import com.alibaba.services.*;
import com.alibaba.views.*;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        EmployeeService empc = new EmployeeService();
        //ClientController cltc = new ClientController();
        AccountController accc = new AccountController();
        OperationController opc = new OperationController();
        MissionController missc = new MissionController();
        MissionAssignmentsController assignmentsController = new MissionAssignmentsController();

        AgencyDaoImpl agencyDao = new AgencyDaoImpl();
        AgencyService agencyService = new AgencyService(agencyDao);
        AgencyView agencyView = new AgencyView(agencyService, agencyDao);



        int choice;
        LocalDateTime dateNow;

        do {
            Scanner scanner = new Scanner(System.in);

            System.out.println("******** welcome to EasyBank app ********\n" +

                    "1  - Add Employee : \n" +
                    "2  - Read List Employee : \n" +
                    "3  - Update Employee : \n" +
                    "4  - Delete Employee : \n" +

                    "5  - Add Client : \n" +
                    "6  - Read List Clients : \n" +
                    "7  - Delete Client : \n" +
                    "8  - Update  Client : \n" +
                    "9  - Search Client by Code : \n" +

                    "10 - Add Account : \n" +
                    "11 - Read List Accounts : \n" +
                    "12 - Delete Account : \n" +
                    "13 - Update Account : \n" +
                    "14 - Update Status Account : \n" +
                    "15 - Search for account by code client : \n" +
                    "16 - Select accounts by Status : \n" +
                    "17 - Select accounts by date creation \n" +

                    "18 - Add opperation : \n" +
                    "19 - Search for operation by number \n" +
                    "20 - Delete Operation \n" +

                    "21 - Add Mission : \n" +
                    "22 - Read All Mission \n" +
                    "23 - Delete Mission \n" +
                    "24 - Read All Mission Employee\n" +
                    "25 - Search account by number operation\n" +
                    "26 - Mission Employee\n" +
                    "27 - Add Agency\n" +

                    "0 - exit.\n\n" +
                    "==> ");

            choice = scanner.nextInt();

            switch (choice){
                case 1 :
                    System.out.println("Add Employee");
                    EmployeeService.createEmployee();
                    break;

                case 2 :
                    System.out.println("read Employees");
                    EmployeeService.getAllEmployees();
                    break;

                case 3 :
                    System.out.println("Update Employee");
                    EmployeeService.updateEmployee();
                    break;

                case 4 :
                    System.out.println("****************** delete Employee");
                    EmployeeService.deleteEmployee();
                    break;

                case 5 :
                    dateNow = LocalDateTime.now();
                    System.out.println("****************** Add Client");
                    ClientService.createClient();
                    assignmentsController.addMissionOpmloyee(dateNow);
                    break;

                case 6 :
                    dateNow = LocalDateTime.now();
                    System.out.println("****************** Read All Clients");
                    ClientService.getAllClients();
                    assignmentsController.addMissionOpmloyee(dateNow);
                    break;

                case 7 :
                    dateNow = LocalDateTime.now();
                    System.out.println("****************** delete Client");
                    ClientService.deleteClient();
                    break;

                case 8 :
                    dateNow = LocalDateTime.now();
                    System.out.println("****************** update Client");
                    ClientService.updateClient();
                    assignmentsController.addMissionOpmloyee(dateNow);
                    break;

                case 9 :
                    dateNow = LocalDateTime.now();
                    System.out.println("****************** Search client by code");
                    ClientService.getClientByCode();
                    assignmentsController.addMissionOpmloyee(dateNow);
                    break;

                case 10 :
                    System.out.println("****************** Add Account");
                    dateNow = LocalDateTime.now();
                    accc.addAccount();
                    assignmentsController.addMissionOpmloyee(dateNow);
                    break;

                case 11 :
                    System.out.println("****************** Read All Accounts");
                    dateNow = LocalDateTime.now();
                    accc.AllAccount();
                    assignmentsController.addMissionOpmloyee(dateNow);
                    break;

                case 12 :
                    System.out.println("****************** Delete Account");
                    dateNow = LocalDateTime.now();
                    accc.deleteAccount();
                    assignmentsController.addMissionOpmloyee(dateNow);
                    break;

                case 13 :
                    System.out.println("****************** Update Account");
                    dateNow = LocalDateTime.now();
                    assignmentsController.addMissionOpmloyee(dateNow);
                    break;

                case 14 :
                    System.out.println("****************** Update Status Account ");
                    dateNow = LocalDateTime.now();
                    accc.updateStatusAccount();
                    assignmentsController.addMissionOpmloyee(dateNow);
                    break;

                case 15 :
                    System.out.println("***************** Search for account by cient code");
                    dateNow = LocalDateTime.now();
                    accc.searchByClientCode();
                    assignmentsController.addMissionOpmloyee(dateNow);
                    break;

                case 16 :
                    System.out.println("***************** Select accounts by Status");
                    dateNow = LocalDateTime.now();
                    accc.AllAccountByStatus();
                    assignmentsController.addMissionOpmloyee(dateNow);
                    break;

                case 17 :
                    System.out.println("***************** Select accounts by date creation");
                    dateNow = LocalDateTime.now();
                    accc.getAccountByDatecreation();
                    assignmentsController.addMissionOpmloyee(dateNow);
                    break;

                case 18 :
                    System.out.println("***************** Add operation");
                    dateNow = LocalDateTime.now();
                    opc.addOperation();
                    assignmentsController.addMissionOpmloyee(dateNow);
                    break;

                case 19 :
                    System.out.println("***************** Search for operation by number");
                    dateNow = LocalDateTime.now();
                    opc.SearchOperation();
                    assignmentsController.addMissionOpmloyee(dateNow);
                    break;

                case 20 :
                    System.out.println("***************** Delete operation");
                    dateNow = LocalDateTime.now();
                    opc.deleteOperation();
                    assignmentsController.addMissionOpmloyee(dateNow);
                    break;

                case 21 :
                    System.out.println("***************** Add Mission");
                    missc.addMission();

                    break;

                case 22 :
                    System.out.println("***************** Read All Mission");
                    missc.AllMission();
                    break;

                case 23 :
                    System.out.println("***************** Delete Mission");
                    missc.deleteMission();
                    break;

                case 24 :
                    System.out.println("***************** Read All Mission Employee");
                    assignmentsController.getMissionByEmployee();
                    break;

                case 25 :
                    System.out.println("***************** Search account by number operation");
                    opc.SearchAccountByOperation();
                    break;
                case 26 :
                    System.out.println("***************** Mission Statistic ");
                    assignmentsController.StatisticMission();
                    break;
                case 27 :
                    System.out.println("***************** Add Agency ");
                    agencyView.createAgency();
                    break;
                case 29 :
                    System.out.println("***************** Read All Agencys ");
                    agencyView.getAllAgencies();
                    break;
                case 30 :
                    System.out.println("***************** Get Agency ");
                    agencyView.getAgencyByID();
                    break;
                case 31 :
                    System.out.println("***************** Delete Agency ");
                    agencyView.deleteAgencyByID();
                    break;
                case 32 :
                    System.out.println("***************** Update Agency ");
                    agencyView.updateAgency();
                    break;
                case 28 :
                    System.out.println("***************** Get Agency By Employee ");
                    //agencyView.createAgency();
                    break;


                case 0:
                    System.out.println("Good by");
                    break;

                default:
                    System.out.println("Enter valid choice !");

            }

//            scanner.close();
        }while (choice != 0);
    }
}

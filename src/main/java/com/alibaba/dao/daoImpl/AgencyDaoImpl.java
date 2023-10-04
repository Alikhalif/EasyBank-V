package com.alibaba.dao.daoImpl;

import com.alibaba.connexion.DB;
import com.alibaba.dao.AgencyDAO;
import com.alibaba.entities.Agency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class AgencyDaoImpl implements AgencyDAO {
    private Connection connection;

    public AgencyDaoImpl(){
        connection = DB.getConnection();
    }

    public AgencyDaoImpl(Connection connection){
        connection = connection;
    }

    @Override
    public Optional<Agency> creat(Agency agency){
        String SQL = "INSERT INTO agencies (name, address, phone) VALUES (?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(SQL)){
//            pst.setString(1,agency.get);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return null;
    }
}

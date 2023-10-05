package com.alibaba.dao;

import com.alibaba.entities.Agency;

import java.util.List;
import java.util.Optional;

public interface AgencyDAO {
    Optional<Agency> create(Agency agency);
    Optional<Agency> update(Integer id, Agency agency);
    Optional<Agency> findByID(Integer id);
    List<Agency> getAll();
    boolean delete(Integer id);
    List<Agency> findByAddress(String address);
}

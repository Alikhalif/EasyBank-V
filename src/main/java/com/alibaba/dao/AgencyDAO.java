package com.alibaba.dao;

import com.alibaba.entities.Agency;

import java.util.Optional;

public interface AgencyDAO {
    Optional<Agency> creat(Agency agency);
}

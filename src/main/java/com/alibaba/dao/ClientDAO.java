package com.alibaba.dao;

import com.alibaba.entities.Client;
import com.alibaba.exception.ClientException;

import java.util.List;
import java.util.Optional;

public interface ClientDAO {
    Optional<Client> create(Client client);
    Optional<Client> update(Integer code, Client client);
    boolean delete(Integer code);
    Optional<Client> findByID(Integer code);
    List<Client> getAll();
    List<Client> findByAttribute(String searchValue) throws ClientException;
}

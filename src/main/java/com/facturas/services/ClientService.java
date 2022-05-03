package com.facturas.services;

import com.facturas.models.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

     List<Client> findAll();

     void save(Client client);

     Optional<Client> findOne(Long id);

     void delete(Long id);
}

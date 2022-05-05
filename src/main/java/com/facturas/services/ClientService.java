package com.facturas.services;

import com.facturas.models.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ClientService {

     List<Client> findAll();

     Page<Client> findAll(Pageable pageable);

     void save(Client client);

     Optional<Client> findOne(Long id);

     void delete(Long id);
}

package com.facturas.services;

import com.facturas.models.entity.Client;
import com.facturas.models.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ClientServiceImpl implements ClientService{

    @Autowired
    private ClientRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return (List) repository.findAll();
    }
}

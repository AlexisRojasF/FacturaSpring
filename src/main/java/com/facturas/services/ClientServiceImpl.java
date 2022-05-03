package com.facturas.services;

import com.facturas.models.entity.Client;
import com.facturas.models.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class ClientServiceImpl implements ClientService{

    @Autowired
    private ClientRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return (List) repository.findAll();
    }

    @Override
    @Transactional
    public void save(Client client) {
            repository.save(client);

    }

    @Override
    public Optional<Client> findOne(Long id) {
        return repository.findById(id);
    }

    @Override
    public void delete(Long id) {
        Client c = repository.findById(id).orElse(null);
        if (c != null && c.getId() > 0){
            repository.delete(c);
        }

    }
}

package com.facturas.services;

import com.facturas.models.entity.Client;
import com.facturas.models.entity.Factura;
import com.facturas.models.entity.Producto;
import com.facturas.models.repository.ClientRepository;
import com.facturas.models.repository.IFacturaDao;
import com.facturas.models.repository.IProductoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class ClientServiceImpl implements ClientService{

    @Autowired
    private ClientRepository repository;

    @Autowired
    private IProductoDao productoDao;

    @Autowired
    private IFacturaDao facturaDao;

    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return (List) repository.findAll();
    }

    @Override
    public Page<Client> findAll(Pageable pageable) {
        return repository.findAll(pageable);
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

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findByNombre(String term) {
        return productoDao.findByNombreLikeIgnoreCase("%" + term + "%");
    }

    @Override
    @Transactional
    public void saveFactura(Factura factura) {
        facturaDao.save(factura);
    }

    @Override
    @Transactional(readOnly=true)
    public Producto findProductoById(Long id) {
        // TODO Auto-generated method stub
        return productoDao.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly=true)
    public Factura findFacturaById(Long id) {
        // TODO Auto-generated method stub
        return facturaDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteFactura(Long id) {
        facturaDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly=true)
    public Factura fetchFacturaByIdWithClienteWhithItemFacturaWithProducto(Long id) {
        return facturaDao.fetchByIdWithClienteWhithItemFacturaWithProducto(id);
    }
}

package com.facturas.services;

import com.facturas.models.entity.Client;
import com.facturas.models.entity.Factura;
import com.facturas.models.entity.Producto;
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

     public List<Producto> findByNombre(String term);

     public void saveFactura(Factura factura);

     public Producto findProductoById(Long id);

     public Factura findFacturaById(Long id);

     public void deleteFactura(Long id);

     public Factura fetchFacturaByIdWithClienteWhithItemFacturaWithProducto(Long id);
}

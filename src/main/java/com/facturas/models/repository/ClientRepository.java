package com.facturas.models.repository;

import com.facturas.models.entity.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {


}

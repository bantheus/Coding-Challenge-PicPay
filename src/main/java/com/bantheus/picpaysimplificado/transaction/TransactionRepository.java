package com.bantheus.picpaysimplificado.transaction;

import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

public interface TransactionRepository extends ListCrudRepository<Transaction, UUID>{
  
}

package com.bantheus.picpaysimplificado.wallet;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface WalletRepository extends CrudRepository<Wallet, UUID>{
  
}

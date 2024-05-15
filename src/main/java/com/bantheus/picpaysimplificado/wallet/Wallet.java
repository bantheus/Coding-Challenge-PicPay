package com.bantheus.picpaysimplificado.wallet;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.data.annotation.Id;

public record Wallet(
  @Id UUID id,
  String fullName,
  Long cpf,
  String email,
  String password,
  int type,
  BigDecimal balance
) {
  
}

package com.bantheus.picpaysimplificado.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("TRANSACTIONS")
public record Transaction(
  @Id UUID id,
  UUID payer,
  UUID payee,
  BigDecimal value,
  @CreatedDate LocalDateTime createdAt
) {
  public Transaction {
    value = value.setScale(2);
  }
}

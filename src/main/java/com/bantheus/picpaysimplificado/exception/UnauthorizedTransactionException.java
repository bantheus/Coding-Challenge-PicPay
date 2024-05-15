package com.bantheus.picpaysimplificado.exception;

public class UnauthorizedTransactionException extends RuntimeException {
  public UnauthorizedTransactionException(String message) {
    super(message);
  }
}

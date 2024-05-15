package com.bantheus.picpaysimplificado.transaction;

import org.springframework.stereotype.Service;

import com.bantheus.picpaysimplificado.wallet.WalletRepository;

@Service
public class TransactionService {
  private final TransactionRepository transactionRepository;
  private final WalletRepository walletRepository;

  public TransactionService(TransactionRepository transactionRepository, WalletRepository walletRepository) {
    this.transactionRepository = transactionRepository;
    this.walletRepository = walletRepository;
  }

  public Transaction create(Transaction transaction) {
    var newTransaction = transactionRepository.save(transaction);

    return newTransaction;
  }
}

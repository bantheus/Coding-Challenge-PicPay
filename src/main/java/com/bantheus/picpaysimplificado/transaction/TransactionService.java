package com.bantheus.picpaysimplificado.transaction;

import org.springframework.stereotype.Service;

import com.bantheus.picpaysimplificado.wallet.Wallet;
import com.bantheus.picpaysimplificado.wallet.WalletRepository;
import com.bantheus.picpaysimplificado.wallet.WalletType;

@Service
public class TransactionService {
  private final TransactionRepository transactionRepository;
  private final WalletRepository walletRepository;

  public TransactionService(TransactionRepository transactionRepository, WalletRepository walletRepository) {
    this.transactionRepository = transactionRepository;
    this.walletRepository = walletRepository;
  }

  public Transaction create(Transaction transaction) {
    validate(transaction);

    var newTransaction = transactionRepository.save(transaction);

    var wallet = walletRepository.findById(transaction.payer()).get();
    walletRepository.save(wallet.debit(transaction.value()));

    return newTransaction;
  }

  private void validate(Transaction transaction) {
    walletRepository.findById(transaction.payee())
      .map(payee -> walletRepository.findById(transaction.payer())
        .map(payer -> isTransactionValid(transaction, payer) ? transaction : null)
        .orElseThrow())
      .orElseThrow();
  }

  private boolean isTransactionValid(Transaction transaction, Wallet payer) {
    return payer.type() == WalletType.COMMON.getValue() && 
      payer.balance().compareTo(transaction.value()) >= 0 &&
      !payer.id().equals(transaction.payee());
  }
}

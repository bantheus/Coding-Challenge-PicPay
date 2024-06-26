package com.bantheus.picpaysimplificado.transaction;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bantheus.picpaysimplificado.authorization.AuthorizerService;
import com.bantheus.picpaysimplificado.notification.NotificationService;
import com.bantheus.picpaysimplificado.wallet.Wallet;
import com.bantheus.picpaysimplificado.wallet.WalletRepository;
import com.bantheus.picpaysimplificado.wallet.WalletType;

@Service
public class TransactionService {
  private final TransactionRepository transactionRepository;
  private final WalletRepository walletRepository;
  private final AuthorizerService authorizerService;
  private final NotificationService notificationService;

  public TransactionService(
    TransactionRepository transactionRepository, 
    WalletRepository walletRepository, 
    AuthorizerService authorizerService, 
    NotificationService notificationService
  ) {
    this.transactionRepository = transactionRepository;
    this.walletRepository = walletRepository;
    this.authorizerService = authorizerService;
    this.notificationService = notificationService;
  }

  @Transactional
  public Transaction create(Transaction transaction) {
    // Validate transaction
    validate(transaction);

    // Save transaction
    var newTransaction = transactionRepository.save(transaction);

    // Credit payee wallet
    var wallet = walletRepository.findById(transaction.payer()).get();
    walletRepository.save(wallet.debit(transaction.value()));

    // external services call
    // authorize transaction
    authorizerService.authorize(transaction);

    // notify transaction
    notificationService.notify(transaction); 

    return newTransaction;
  }

  private void validate(Transaction transaction) {
    walletRepository.findById(transaction.payee())
      .map(payee -> walletRepository.findById(transaction.payer())
        .map(payer -> isTransactionValid(transaction, payer) ? transaction : null)
        .orElseThrow(() -> new InvalidTransactionException("Invalid transaction: %s".formatted(transaction))))
      .orElseThrow(() -> new InvalidTransactionException("Invalid transaction: %s".formatted(transaction)));
  }

  private boolean isTransactionValid(Transaction transaction, Wallet payer) {
    return payer.type() == WalletType.COMMON.getValue() && 
      payer.balance().compareTo(transaction.value()) >= 0 &&
      !payer.id().equals(transaction.payee());
  }

  public List<Transaction> list() {
    return transactionRepository.findAll();
  }
}

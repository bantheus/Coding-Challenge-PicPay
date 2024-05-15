package com.bantheus.picpaysimplificado.notification;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.bantheus.picpaysimplificado.transaction.Transaction;

@Service
public class NotificationProducer {
  private final KafkaTemplate<String, Transaction> kafkaTemplate;

  public NotificationProducer(KafkaTemplate<String, Transaction> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendNotification(Transaction transaction) {
    kafkaTemplate.send("transaction-notification", transaction);
  }
  
}

package com.bantheus.picpaysimplificado.notification;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.bantheus.picpaysimplificado.transaction.Transaction;

@Service
public class NotificationConsumer {
  private RestClient restClient;

  public NotificationConsumer(RestClient.Builder builder) {
    this.restClient = builder
      .baseUrl("https://run.mocky.io/v3/ee5ce3ac-9026-4866-b8d7-59b4b33cad2c")
      .build();
  }

  @KafkaListener(topics = "transaction-notification", groupId = "picpay-simplificado")
  public void receiveNotification(Transaction transaction) {
    var response = restClient.get()
      .retrieve()
      .toEntity(Notification.class);

      if(response.getStatusCode().isError() || (response.getBody() != null && !response.getBody().message())) {
        throw new NotificationException("Error sending notification for transaction: %s".formatted(transaction));
      }
  }
}

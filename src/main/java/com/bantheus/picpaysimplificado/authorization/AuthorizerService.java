package com.bantheus.picpaysimplificado.authorization;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.bantheus.picpaysimplificado.transaction.Transaction;

@Service
public class AuthorizerService {
  private RestClient restClient;

  public AuthorizerService(RestClient.Builder builder) {
    this.restClient = builder
      .baseUrl("https://run.mocky.io/v3/9f43fb2d-2fc6-4788-89ac-6091d313ee3e")
      .build();
  }

  public void authorize(Transaction transaction) {
    var response = restClient.get()
      .retrieve()
      .toEntity(Authorization.class);

      if(response.getStatusCode().isError() || (response.getBody() != null && !response.getBody().isAuthorized())) {
        throw new UnauthorizedTransactionException("Transaction not authorized");
      }
  }
  
}

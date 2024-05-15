DELETE FROM TRANSACTIONS;

DELETE FROM WALLETS;

INTERT INTO 
  WALLETS (
    ID, FULL_NAME, CPF, EMAIL, "PASSWORD", "TYPE", BALANCE
  )
  VALUES (
    1, 'Fulano da Silva - User', 12345678900, 'fulano@email.com', '123456', 1, 1000.00
  );

INTERT INTO 
  WALLETS (
    ID, FULL_NAME, CPF, EMAIL, "PASSWORD", "TYPE", BALANCE
  )
  VALUES (
    2, 'Ciclano da Silva - Shopkeeper', 98765432100, 'ciclane@email.com', '123456', 2, 1000.00
  );
  
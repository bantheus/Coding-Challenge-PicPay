package com.bantheus.picpaysimplificado.wallet;

public enum WalletType {
  COMMON(1),
  RETAIL(2);

  private final int value;

  private WalletType(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}

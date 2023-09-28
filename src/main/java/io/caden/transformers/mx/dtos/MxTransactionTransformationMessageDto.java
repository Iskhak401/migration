package io.caden.transformers.mx.dtos;

import java.util.List;

public class MxTransactionTransformationMessageDto {
  private List<MxApiTransactionDto> transactions;

  public List<MxApiTransactionDto> getTransactions() {
    return this.transactions;
  }
  public void setTransactions(List<MxApiTransactionDto> transactions) {
    this.transactions = transactions;
  }
}

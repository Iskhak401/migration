package io.caden.transformers.airbnb.dtos;

import java.util.Map;

public class AirbnbTransformationDto {
  private String tripList;
  private String tripDetails;
  private String tripRoom;
  private Map<String, Object> receiptInfo;

  public AirbnbTransformationDto() {
  }

  public AirbnbTransformationDto(
    final String tripList,
    final String tripDetails,
    final String tripRoom,
    final Map<String, Object> receiptInfo
  ) {
    this.tripList = tripList;
    this.tripDetails = tripDetails;
    this.tripRoom = tripRoom;
    this.receiptInfo = receiptInfo;
  }

  public String getTripList() {
    return this.tripList;
  }

  public void setTripList(final String tripList) {
    this.tripList = tripList;
  }

  public String getTripDetails() {
    return this.tripDetails;
  }

  public void setTripDetails(final String tripDetails) {
    this.tripDetails = tripDetails;
  }

  public String getTripRoom() {
    return this.tripRoom;
  }

  public void setTripRoom(final String tripRoom) {
    this.tripRoom = tripRoom;
  }

  public Map<String, Object> getReceiptInfo() {
    return this.receiptInfo;
  }

  public void setReceiptInfo(final Map<String, Object> receiptInfo) {
    this.receiptInfo = receiptInfo;
  }
}

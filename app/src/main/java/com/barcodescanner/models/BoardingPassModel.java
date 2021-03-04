package com.barcodescanner.models;

/**
 * Project Name :dnataDVA
 * Created By :Himanshu sharma on 22-07-2020
 * Package : com.dnata.dnatadva.models
 */
public class BoardingPassModel {
    private String fullName;
    private String flightNumber;
    private String carrierCode = "";
    private String flightTime;
    private String gate;
    private String flightDate;
    private String flightStatus;
    private String seatNumber;
    private String sequenceNumber;
    private String fromCity;
    private String toCity;
    private String compartmentCode;
    private String seatingClass;
    private Boolean isValid = true;
    private Boolean isBuisnessClass = false;

    public BoardingPassModel() {
    }

    public BoardingPassModel(String name, String flightNumber, String flightDate, String seatNumber, String compartmentCode, String seatingClass, String passAdditionalInfo, String languageId) {
        this.fullName = name;
        this.flightNumber = flightNumber;
        this.flightDate = flightDate;
        this.seatNumber = seatNumber;
        this.compartmentCode = compartmentCode;
        this.seatingClass = seatingClass;
    }

    public String getFlightStatus() {
        return flightStatus;
    }

    public void setFlightStatus(String flightStatus) {
        this.flightStatus = flightStatus;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public Boolean getBuisnessClass() {
        return isBuisnessClass;
    }

    public void setBuisnessClass(Boolean buisnessClass) {
        isBuisnessClass = buisnessClass;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    public String getName() {
        return fullName;
    }

    public void setName(String name) {
        this.fullName = name;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public String getCompartmentCode() {
        return compartmentCode;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public void setCompartmentCode(String compartmentCode) {
        this.compartmentCode = compartmentCode;
    }

    public String getSeatingClass() {
        return seatingClass;
    }

    public void setSeatingClass(String seatingClass) {
        this.seatingClass = seatingClass;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(String flightTime) {
        this.flightTime = flightTime;
    }

    @Override
    public String toString() {
        return "BoardingPassModel{" +
                "fullName='" + fullName + '\'' +
                ", flightNumber='" + flightNumber + '\'' +
                ", carrierCode='" + carrierCode + '\'' +
                ", flightTime='" + flightTime + '\'' +
                ", gate='" + gate + '\'' +
                ", flightDate='" + flightDate + '\'' +
                ", flightStatus='" + flightStatus + '\'' +
                ", seatNumber='" + seatNumber + '\'' +
                ", sequenceNumber='" + sequenceNumber + '\'' +
                ", fromCity='" + fromCity + '\'' +
                ", toCity='" + toCity + '\'' +
                ", compartmentCode='" + compartmentCode + '\'' +
                ", seatingClass='" + seatingClass + '\'' +
                ", isValid=" + isValid +
                ", isBuisnessClass=" + isBuisnessClass +
                '}';
    }
}

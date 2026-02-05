package org.Ocean_View.Customer.Entity;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class Booking
{
    private String uniqueId;
    private String customerFirstName;
    private String customerLastName;
    private String customerEmail;
    private String phoneNumber;
    private String fine;
    private Date inDate;
    private Date outDate;
    private Double price;
    private String noOfDays;
    private String paymentUniqueId;
    private String paymentMethod;
    private LocalDateTime bookingDate;
    private String roomId;
    private String roomCategory;
    private String bookingStatus;
    private Double dailyFine;
    private Integer overdueDays;
    private Double calculatedFine;

    public Double getDailyFine() {
        return dailyFine;
    }

    public void setDailyFine(Double dailyFine) {
        this.dailyFine = dailyFine;
    }

    public Integer getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(Integer overdueDays) {
        this.overdueDays = overdueDays;
    }

    public Double getCalculatedFine() {
        return calculatedFine;
    }

    public void setCalculatedFine(Double calculatedFine) {
        this.calculatedFine = calculatedFine;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFine() {
        return fine;
    }

    public void setFine(String fine) {
        this.fine = fine;
    }

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public Date getOutDate() {
        return outDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(String noOfDays) {
        this.noOfDays = noOfDays;
    }

    public String getPaymentUniqueId() {
        return paymentUniqueId;
    }

    public void setPaymentUniqueId(String paymentUniqueId) {
        this.paymentUniqueId = paymentUniqueId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }


    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomCategory() {
        return roomCategory;
    }

    public void setRoomCategory(String roomCategory) {
        this.roomCategory = roomCategory;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}

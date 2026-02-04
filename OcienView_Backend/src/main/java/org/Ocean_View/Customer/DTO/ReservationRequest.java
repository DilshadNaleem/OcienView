package org.Ocean_View.Customer.DTO;

import java.util.Date;

public class ReservationRequest
{
    private String roomId;
    private String roomPrice;
    private Date checkIn;
    private Date checkOut;
    private String totalDays;
    private String totalPrice;
    private String paymentMethod;

    public ReservationRequest(String roomId, String roomPrice, Date checkIn, Date checkOut, String totalDays, String totalPrice, String paymentMethod) {
        this.roomId = roomId;
        this.roomPrice = roomPrice;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalDays = totalDays;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(String roomPrice) {
        this.roomPrice = roomPrice;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public String getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(String totalDays) {
        this.totalDays = totalDays;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}

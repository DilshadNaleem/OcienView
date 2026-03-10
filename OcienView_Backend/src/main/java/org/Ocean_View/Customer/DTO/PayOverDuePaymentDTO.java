package org.Ocean_View.Customer.DTO;

public class PayOverDuePaymentDTO
{
    private String uniqueId;
    private String bookingId;
    private String customerEmail;
    private String roomId;
    private Double calculatedFine;
    private Integer overdueDays;
    private String paymentMethod;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Double getCalculatedFine() {
        return calculatedFine;
    }

    public void setCalculatedFine(Double calculatedFine) {
        this.calculatedFine = calculatedFine;
    }

    public Integer getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(Integer overdueDays) {
        this.overdueDays = overdueDays;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return "PayOverDuePaymentDTO{" +
                "uniqueId='" + uniqueId + '\'' +
                ", bookingId='" + bookingId + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", roomId='" + roomId + '\'' +
                ", calculatedFine=" + calculatedFine +
                ", overdueDays=" + overdueDays +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
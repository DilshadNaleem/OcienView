package org.Ocean_View.Customer.Services.Interfaces;

import org.Ocean_View.Customer.Entity.Booking;
import org.Ocean_View.Customer.Entity.Payment;

public interface Reservations
{
    String saveReservations( Booking booking);
    String getLastUniqueId();
    String getPaymentLastUniqueId();
    String updateSuccessStatusForSuccess(String uniqueId);

}

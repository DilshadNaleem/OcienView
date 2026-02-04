package org.Ocean_View.Customer.Services.Interfaces;

import org.Ocean_View.Customer.Entity.Payment;

public interface PaymentService
{
    String savePayment(Payment payment);
    String getLastUniqueId();
}

package org.Ocean_View.Customer.Services;

public class PaymentUniqueID
{
    public static String generatePaymentUniqueID (String lastUniqueID)
    {
        if (lastUniqueID != null)
        {
            int lastNumber = Integer.parseInt(lastUniqueID.substring(9));
            return "PAYMENT_" + String.format("%02d", lastNumber + 1);
        }
        return "PAYMENT_01";
    }
}

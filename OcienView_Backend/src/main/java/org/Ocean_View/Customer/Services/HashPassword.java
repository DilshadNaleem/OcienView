package org.Ocean_View.Customer.Services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPassword
{
    public String hashPassword(String password)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.trim().getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes)
            {
                sb.append(String.format("%02x",b));
            }

            return sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException("Error hashing password: " + e.getMessage());
        }
    }
}

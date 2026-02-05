package org.Ocean_View.Customer.Services.Interfaces;

import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Customer.Entity.Booking;

import java.util.ArrayList;
import java.util.List;

public interface HistoryService
{
   public List<Booking> fetchHistoryByEmail(String email, HttpSession session);
}

package org.Ocean_View.Customer.Controller;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.Ocean_View.Customer.Services.Implementations.ReservationsImpl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/Customer/GetBookedDates")
public class GetBookedDatesServlet extends HttpServlet {
    private ReservationsImpl reservations = new ReservationsImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String roomId = req.getParameter("roomId");
        List<Map<String, String>> dates = reservations.getBookedDates(roomId);

        // Format dates to yyyy-MM-dd for easier comparison
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Map<String, String>> formattedDates = new ArrayList<>();

        for (Map<String, String> dateRange : dates) {
            Map<String, String> formattedRange = new HashMap<>();
            formattedRange.put("checkIn", dateRange.get("checkIn"));
            formattedRange.put("checkOut", dateRange.get("checkOut"));
            formattedDates.add(formattedRange);
        }

        String json = new Gson().toJson(formattedDates);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }
}
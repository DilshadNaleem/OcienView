package org.Ocean_View.Customer.Controller;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.Ocean_View.Customer.Services.Implementations.ReservationsImpl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/Customer/GetBookedDates")
public class GetBookedDatesServlet extends HttpServlet
{
    private ReservationsImpl reservations = new ReservationsImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, IOException {
        String roomId = req.getParameter("roomId");
        List<Map<String, String>> dates = reservations.getBookedDates(roomId);

        String json = new Gson().toJson(dates);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }


}

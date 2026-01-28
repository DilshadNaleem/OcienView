package org.Ocean_View.Customer.Controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// REMOVE @WebServlet annotation for manual registration
@WebServlet("/test1")  // Comment this out
public class sample extends HttpServlet {  // Must be public

    @Override
    public void init() {
        System.out.println("🔔 Servlet 'sample' INITIALIZED!");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        System.out.println("🔔 Servlet 'sample' HIT!");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h1 style='color: green;'>✅ SERVLET WORKS!</h1>");
        out.println("<p>Class: " + this.getClass().getName() + "</p>");
    }
}
package org.Ocean_View.Admin.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.Ocean_View.Admin.DTO.RoomType;
import org.Ocean_View.Admin.Services.FileUploader;
import org.Ocean_View.Admin.Services.Implementation.ManageRoomTypeImpl;
import org.Ocean_View.Admin.Services.Interfaces.ManageRoomType;

import java.io.IOException;
import java.util.List;

@WebServlet("/Admin/ManageRoomTypes")
public class ManageRoomTypeServlet extends HttpServlet
{
    private FileUploader fileUploader;
    private ManageRoomType manageRoomType;

    @Override
    public void init() throws ServletException {
        // Initialize services in init() method
        fileUploader = new FileUploader();
        manageRoomType = new ManageRoomTypeImpl(fileUploader);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<RoomType> roomTypes = manageRoomType.getAllRoomTypes();
        req.setAttribute("roomList", roomTypes);
        req.getRequestDispatcher("/Admin/ManageRoomTypes.jsp").forward(req, resp);


    }
}
package org.Ocean_View.Admin.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.Ocean_View.Admin.Services.FileUploader;
import org.Ocean_View.Admin.Services.Implementation.ManageRoomTypeImpl;
import org.Ocean_View.Admin.Services.Interfaces.ManageRoomType;

import java.io.File;
import java.io.IOException;

@WebServlet("/Admin/DeleteRoomType")
public class DeleteRoomTypeServlet  extends HttpServlet
{
    private ManageRoomType manageRoomType;

    @Override
    public void init() throws ServletException {
        FileUploader fileUploader = new FileUploader();
        manageRoomType = new ManageRoomTypeImpl(fileUploader);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String uniqueId = req.getParameter("uniqueId");

          boolean isDeleted = manageRoomType.getRoomTypeById(uniqueId);

        if(isDeleted)
        {
            req.getSession().setAttribute("successMessage", "Room type deleted Successfully!");
        } else
        {
            req.getSession().setAttribute("errorMessage", "Failed to delete room type.");
        }

        resp.sendRedirect(req.getContextPath() + "/Admin/ManageRoomTypes");
    }
}

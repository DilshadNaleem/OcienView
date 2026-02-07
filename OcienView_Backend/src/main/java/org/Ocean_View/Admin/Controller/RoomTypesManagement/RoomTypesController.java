package org.Ocean_View.Admin.Controller.RoomTypesManagement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.Ocean_View.Admin.Services.FileUploader;
import org.Ocean_View.Admin.Services.Implementation.ManageRoomTypeImpl;
import org.Ocean_View.Admin.Services.Interfaces.ManageRoomType;
import org.Ocean_View.Admin.Services.RoomTypeIdGenerator;

import java.io.IOException;

@WebServlet("/Admin/AddRoomTypes")
public class RoomTypesController extends HttpServlet
{
    private ManageRoomType manageRoom;


    @Override
    public void init() throws ServletException {
        manageRoom = new ManageRoomTypeImpl(new FileUploader());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String lastUniqueId = manageRoom.getLastUniqueId();
        String uniqueId = RoomTypeIdGenerator.generateUniqueId(lastUniqueId);
        String roomType = req.getParameter("category");
        String description = req.getParameter("description");
        String image = req.getParameter("image");

        if (roomType == null || roomType.trim().isEmpty())
        {
            resp.getWriter().write("<script type='text/javascript'>"
                    + "alert('Error: Room category cannot be empty!');"
                    + "window.location.href=document.referrer;"
                    + "</script>");

            return;
        }

        if (image == null || image.trim() .isEmpty())
        {
            resp.getWriter().write("<script type='text/javascript'>"
                    + "alert('Error: Room image is required!');"
                    + "window.location.href=document.referrer;"
                    + "</script>");
            return;
        }

        String imagePath = "Room_Categories/" + image;

        try
        {
            manageRoom.saveRoomType(uniqueId,roomType,description,imagePath);
            resp.getWriter().write("<script type='text/javascript'>"
                    + "alert('Room added successfully!');"
                    + "window.location.href=document.referrer;"
                    + "</script>");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            resp.getWriter().write("<script type='text/javascript'>"
                    + "alert('Error adding vehicle: " + e.getMessage() + "');"
                    + "window.location.href=document.referrer;"
                    + "</script>");
        }
    }
}

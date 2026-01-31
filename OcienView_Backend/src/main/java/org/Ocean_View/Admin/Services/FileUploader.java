package org.Ocean_View.Admin.Services;

import jakarta.servlet.http.Part;

import java.io.*;

public class FileUploader
{
    public String saveFile(Part filePart) throws IOException
    {
        String fileName = getFileName(filePart);

        if (fileName == null || fileName.isEmpty())
        {
            throw new IOException("No file Uploaded");
        }

        String uploadDir = "C:\\Users\\HP\\Desktop\\Shimer\\OcienView\\OcienView_Backend\\main\\webapp\\Room_Categories";

        File uploadFolder = new File(uploadDir);
        if (!uploadFolder.exists())
        {
            if (!uploadFolder.mkdirs())
            {
                throw new IOException("Failed to create upload Directory");
            }
        }

        String filePath = uploadDir + File.separator + fileName;
        System.out.println("Saving file to: " + filePath);

        try (InputStream inputStream = filePart.getInputStream();
             OutputStream outputStream = new FileOutputStream(new File(filePath))) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            System.out.println("File saved successfully!");
        } catch (IOException e) {
            // Log the error for better debugging
            System.err.println("Error while saving the file: " + e.getMessage());
            throw e;
        }

        // Return the relative path where the image has been saved
        return "/Room_Categories/" + fileName;
    }

    private String getFileName(Part part)
    {
        String contentDisposition = part.getHeader("content-disposition");
        for (String cd : contentDisposition.split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

}

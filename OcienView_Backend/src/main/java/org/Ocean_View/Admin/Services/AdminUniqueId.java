package org.Ocean_View.Admin.Services;

import org.Ocean_View.Connection.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminUniqueId
{
    public String generateCustomerUniqueId()
    {
        String uniqueId = "ADM_01";
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT unique_id FROM admin ORDER BY id DESC LIMIT 1";
            try (PreparedStatement ps = conn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                {
                    String lastId = rs.getString("unique_id");
                    if (lastId != null && lastId.startsWith("ADM_"))
                    {
                        int num = Integer.parseInt(lastId.substring(4)) + 1;
                        uniqueId = String.format("ADM_%02d", num);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uniqueId;
    }
}

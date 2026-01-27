package org.Ocean_View.Customer.Services;

import org.Ocean_View.Connection.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CusUniqueID
{
    public String generateCustomerUniqueId()
    {
        String uniqueId = "CUS_01";
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT unique_id FROM customers DESC LIMIT 1";
            try (PreparedStatement ps = conn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                {
                    String lastId = rs.getString("unique_id");
                    if (lastId != null && lastId.startsWith("CUS_"))
                    {
                        int num = Integer.parseInt(lastId.substring(4)) + 1;
                        uniqueId = String.format("CUS_%02d", num);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uniqueId;
    }
}

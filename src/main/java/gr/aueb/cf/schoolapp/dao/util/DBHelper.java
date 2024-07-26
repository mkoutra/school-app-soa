package gr.aueb.cf.schoolapp.dao.util;

import gr.aueb.cf.schoolapp.service.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class. Helps with cleaning the DB after Testing.
 */
public class DBHelper {
    private DBHelper() {}

    public static void eraseData() throws SQLException {
        String sqlFKOff = "SET @@foreign_key_checks = 0";   // To disable foreign key checks
        String sqlFKOn = "SET @@foreign_key_checks = 1";    // To enable foreign key checks

        // To get the names of the Database tables
        String sqlSelect = "SELECT TABLE_NAME FROM information_schema.tables WHERE TABLE_SCHEMA = 'school6db';";

        ResultSet rs;
        List<String> tables;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps1 = connection.prepareStatement(sqlFKOff);
             PreparedStatement ps2 = connection.prepareStatement(sqlSelect)){

            ps1.executeUpdate();        // Disable foreign key checks

            rs = ps2.executeQuery();    // Get the names of all database tables
            tables = mapRsToList(rs);

            for (String table : tables) {
                // Delete all records
                connection.prepareStatement("DELETE FROM " + table).executeUpdate();
                // Reset auto-increment to 1
                connection.prepareStatement("ALTER TABLE " + table + " AUTO_INCREMENT=1").executeUpdate();
            }

            connection.prepareStatement(sqlFKOn).executeUpdate();   // Re-activate foreign key checks
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private static List<String> mapRsToList(ResultSet rs) throws SQLException {
        List<String> tables = new ArrayList<>();    // Contains the names of the MySQL Database tables

        while(rs.next()) {
            tables.add(rs.getString("TABLE_NAME"));
        }

        return tables;
    }
}

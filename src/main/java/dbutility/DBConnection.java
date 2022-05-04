package dbutility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    String login = "s335150";
    String password = "dzo415";
    String host = "jdbc:postgresql://pg:5432/studs";

    public Connection connect(){

        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(host,login,password);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return conn;
    }
}

import dbutility.DBConnection;
import dbutility.DBInit;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = new DBConnection().connect();
        new DBInit(connection).init();

    }
}

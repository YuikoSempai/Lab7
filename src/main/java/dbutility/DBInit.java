package dbutility;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
public class DBInit {
    private final Connection dbConnection;

    public DBInit(Connection aConnection){
        dbConnection = aConnection;
    }

    public void init() throws SQLException {
        Statement st = dbConnection.createStatement();

        st.executeUpdate("CREATE TABLE IF NOT EXISTS tickets ("+
                "id int PRIMARY KEY,"+
                "name varchar(255) NOT NULL CHECK(name<>''),"+
                "xCoordinate int,"+
                "yCoordinate int,"+
                "creationDate date DEFAULT (current_date),"+
                "price float NOT NULL CHECK(price>0),"+
                "comment varchar(366),"+
                "tickettype varchar(15) NOT NULL CHECK(tickettype = 'VIP' OR tickettype = 'USUAL' OR tickettype = 'BUDGETARY' OR tickettype = 'CHEAP'),"+
                "personBirthday date DEFAULT(current_date),"+
                "personHeight float NOT NULL,"+
                "personWeight float NOT NULL,"+
                "personPassportID varchar(255) NOT NULL,"+
                "username varchar(255) NOT NULL" +
                ")");

        st.executeUpdate("CREATE TABLE IF NOT EXISTS users ("+
                "username varchar(255) PRIMARY KEY,"+
                "hashPassword varchar(255) DEFAULT (NULL)"+
                ")");
        st.executeUpdate("CREATE SEQUENCE IF NOT EXISTS sequence START 1");
    }

}

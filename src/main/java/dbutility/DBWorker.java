package dbutility;

import data.Ticket;
import data.TicketType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utility.Session;

import java.sql.*;

public class DBWorker {

    public static final Logger logger = LogManager.getLogger("Database");
    private final static Connection connection = new DBConnection().connect();

    public static void init(){
        try {
            new DBInit(connection).init();
        }catch (SQLException e){
            System.out.println(e.getMessage());
            logger.error("Can't create tables");
        }
    }

    public static boolean addTicket(String username, Ticket ticket) {
        String addStatement = Statements.addTicket.getStatement();
        try {
            PreparedStatement st = connection.prepareStatement(addStatement);
            setTicketStatement(st, username, ticket,"add");
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            logger.warn("Problems with SQL");
        }
        return false;
    }

    public static boolean clear(String username) {
        String clearStatement = Statements.clear.getStatement();
        try {
            PreparedStatement st = connection.prepareStatement(clearStatement);
            st.setString(1, username);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            logger.warn("Problems with SQL");
            return false;
        }
    }

    public static boolean removeAnyByType(String username, TicketType type) {
        String removeAnyByTypeStatement = Statements.removeAnyByType.getStatement();
        try {
            PreparedStatement st = connection.prepareStatement(removeAnyByTypeStatement);
            st.setString(1, username);
            st.setString(2, type.getType());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            logger.warn("Problems with SQL");
            return false;
        }
        return true;
    }

    public static boolean removeById(String username, String id) {
        String removeByIdStatement = Statements.removeById.getStatement();
        try {
            PreparedStatement st = connection.prepareStatement(removeByIdStatement);
            st.setString(1, username);
            st.setString(2, id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            logger.warn("Problems with SQL");
            return false;
        }
        return true;
    }
    public static boolean remove(String username, Ticket ticket) {
        try {
            Statement st = connection.createStatement();
            ResultSet resultSet;
            resultSet = st.executeQuery("SELECT count(*) from tickets");
            resultSet.next();
            int count = resultSet.getInt("count");
            PreparedStatement prst = connection.prepareStatement(Statements.remove.getStatement());
            setTicketStatement(prst, username, ticket,"remove");
            prst.execute();
            resultSet = st.executeQuery("SELECT count(*) from tickets");
            resultSet.next();
            int countAfterDelete = resultSet.getInt("count");
            return count != countAfterDelete;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            logger.warn("Problems with SQL");
            return false;
        }
    }

    public static void addUser(Session session) {
        String addUserStatement = Statements.addUser.getStatement();
        try {
            PreparedStatement st = connection.prepareStatement(addUserStatement);
            st.setString(1, session.getUsername());
            st.setBytes(2, session.getHashPassword());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            logger.warn("Problems with sql");
        }
    }

    public static boolean checkUser(Session session){
        ResultSet resultSet;
        try {
            PreparedStatement st2 = connection.prepareStatement(Statements.checkUsername.getStatement());
            st2.setString(1,session.getUsername());
            resultSet = st2.executeQuery();
            return resultSet.next();
        }catch(SQLException e) {
            System.out.println(e.getMessage());
            logger.warn("Problems with sql");
            return false;
        }
    }

    public static boolean checkPassword(Session session){
        try{
            ResultSet resultSet;
            PreparedStatement st;
            if(session.getHashPassword() == null){
                st = connection.prepareStatement(Statements.checkNullPassword.getStatement());
            }else{
                st = connection.prepareStatement(Statements.checkPassword.getStatement());
                st.setBytes(2, session.getHashPassword());
            }
            st.setString(1, session.getUsername());
            resultSet = st.executeQuery();
            return resultSet.next();
        }catch (SQLException e) {
            logger.warn("Problems with sql");
            return false;
        }
    }

    private static Integer generateId() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(Statements.generateID.getStatement());
            if (resultSet.next()) {
                return resultSet.getInt("nextval");
            }
        } catch (SQLException throwable) {
            logger.warn("SQL problem with generating id!");
        }
        return null;
    }

    private static void setTicketStatement(PreparedStatement st, String username, Ticket ticket, String commandName)
            throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(Statements.generateID.getStatement());
        int i = 1;
        if(commandName.equals("add")) {
            ticket.setId(generateId());
            st.setLong(i++, ticket.getId());
        }else{
            st.setLong(i++,ticket.getId());
        }
        st.setString(i++, ticket.getName());
        st.setInt(i++, ticket.getCoordinates().getX());
        st.setFloat(i++, ticket.getCoordinates().getY());
        st.setDate(i++, Date.valueOf(ticket.getCreationDate().toLocalDate()));
        st.setDouble(i++, ticket.getPrice());
        st.setString(i++, ticket.getComment());
        st.setString(i++, ticket.getType().getType());
        st.setDate(i++, Date.valueOf(ticket.getPerson().getBirthday().toLocalDate()));
        st.setFloat(i++, ticket.getPerson().getHeight());
        st.setFloat(i++, ticket.getPerson().getWeight());
        st.setString(i++, ticket.getPerson().getPassportID());
        st.setString(i, username);
    }
}

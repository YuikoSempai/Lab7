package dbutility;

public enum Statements {

    addTicket("INSERT INTO tickets " +
                      "(id,name,xcoordinate,ycoordinate,creationDate,price," +
                      "comment,tickettype,personBirthday,personHeight,personWeight,personPassportID,username) " +
                      "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)"),
    generateID("SELECT nextval('sequence')"),
    addUser("INSERT INTO users " +
            "(username,hashpassword) " +
            "VALUES(?,?)"),
    clear("DELETE FROM tickets " +
            "WHERE user = ?"),
    removeAnyByType("DELETE FROM tickets " +
            "WHERE user = ? AND tickettype = ?"),
    removeByUsername("DELETE FROM tickets " +
            "WHERE user = ?"),
    removeById("DELETE FROM tickets " +
            "WHERE user = ? AND id = ?"),
    remove("DELETE FROM tickets where " +
            "id = ? and name = ? AND xcoordinate = ? AND ycoordinate = ? AND creationDate = ? AND price = ? AND comment = ? AND " +
            "tickettype = ? AND personBirthday = ? AND personHeight = ? AND personWeight = ? AND personPassportID = ? " +
            "AND username = ?"),
    checkPassword("SELECT * FROM users WHERE username = ? AND hashpassword = ?"),
    checkUsername("SELECT * FROM users where username = ?"),
    checkTicket("SELECT FROM tickets where " +
            "name = ? AND xcoordinate = ? AND ycoordinate = ? AND creationDate = ? AND price = ? AND comment = ? AND " +
            "tickettype = ? AND personBirthday = ? AND personHeight = ? AND personWeight = ? AND personPassportID = ? AND username = ?"),
    checkNullPassword("SELECT * FROM users where username = ? AND hashpassword is NULL");
    private final String statement;

    Statements(String statement){
        this.statement = statement;
    }

    public String getStatement(){
        return statement;
    }
}

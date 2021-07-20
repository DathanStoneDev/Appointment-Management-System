package wgu.stone.DAO;

import wgu.stone.database.DatabaseConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Contains the method implementations for users.
 */
public class UserDAOImpl {
    public static String loggedInUser;

    /**
     * validates the username and password.
     * @param un username is passed.
     * @param up user password is passed.
     * @return true if username and password have a match in the database. Else, false.
     */
    public static boolean checkUserInfo(String un, String up) {

        String sql = "SELECT User_Name, Password FROM users";

        try(PreparedStatement p = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ResultSet rs = p.executeQuery();
            while(rs.next()) {
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                if(un.equals(userName) && up.equals(password)) {
                    loggedInUser = userName;
                    return true;
                }
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } return false;
    }
}

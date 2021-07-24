package wgu.stone.DAO;

import wgu.stone.database.DatabaseConnection;
import wgu.stone.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Place this in the database package


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

    //not a good method for this. Rewrite.
    public static int getUserInfo(String userName) {
        String sql = "SELECT User_ID FROM users WHERE User_Name = ?";

        try(PreparedStatement p = DatabaseConnection.getConnection().prepareStatement(sql)) {
            p.setString(1, userName);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("User_ID");
                return userId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

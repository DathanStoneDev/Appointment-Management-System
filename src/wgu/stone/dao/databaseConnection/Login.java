package wgu.stone.dao.databaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Login {

    /**
     * validates the username and password.
     * @param un username is passed.
     * @param up user password is passed.
     * @return Returns true if username and password have a match in the database. Else, false.
     */

    public boolean checkUserInfo(String un, String up) {

        String sql = "SELECT User_Name, Password FROM users WHERE User_Name = ? AND Password = ?";

        try(PreparedStatement p = DatabaseConnection.getConnection().prepareStatement(sql)) {
            p.setString(1, un);
            p.setString(2, up);
            ResultSet rs = p.executeQuery();
            if(rs.next()) {
                return true;
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } return false;
    }

    /**
     * Retrieves the User ID of the current user logged in.
     * @param userName Passed from the loginController to retrieve the User ID.
     * @return Returns the User ID.
     */
    public int getUserId (String userName) {

        int loggedInUserId = 0;
        String sql = "SELECT User_ID FROM users WHERE User_Name = ?";

        try(PreparedStatement p = DatabaseConnection.getConnection().prepareStatement(sql)) {
            p.setString(1, userName);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("User_ID");
                loggedInUserId = userId;
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loggedInUserId;
    }
}

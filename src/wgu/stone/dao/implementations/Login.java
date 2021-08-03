package wgu.stone.dao.implementations;

import wgu.stone.database.DatabaseConnection;
import wgu.stone.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Login {

    public static String loggedInUser;

    /**
     * validates the username and password.
     * @param un username is passed.
     * @param up user password is passed.
     * @return true if username and password have a match in the database. Else, false.
     */
    //May add this to Database Util, as Creating users is out of scope.
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


    /*public User getUserId(String userName) {

        String sql = "SELECT User_ID FROM users WHERE User_Name = ?";

        try(PreparedStatement p = DatabaseConnection.getConnection().prepareStatement(sql)) {
            p.setString(1, userName);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("User_ID");
                User user = new User(userId);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } */
}

package wgu.stone.DAO;

import wgu.stone.database.DatabaseConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl {

    public static void checkUserInfo(String un, String up) {
        Boolean foundUser = false;
        String sql = "SELECT User_Name, Password FROM users";

        try(PreparedStatement p = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ResultSet rs = p.executeQuery();
            while(rs.next()) {
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                if(un.equals(userName) && up.equals(password)) {
                    foundUser = true;
                    System.out.println("Found you!");
                }
            }
            rs.close();
            if(!foundUser) {
                System.out.println("Please check username and password");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

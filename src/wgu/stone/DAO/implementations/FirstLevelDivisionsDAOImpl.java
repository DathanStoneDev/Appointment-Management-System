package wgu.stone.DAO.implementations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wgu.stone.DAO.interfaces.FirstLevelDivisionsDAO;
import wgu.stone.database.DatabaseConnection;
import wgu.stone.model.FirstLevelDivisions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FirstLevelDivisionsDAOImpl implements FirstLevelDivisionsDAO {

    private ObservableList<FirstLevelDivisions> divisions = FXCollections.observableArrayList();
    @Override
    public ObservableList<FirstLevelDivisions> getDivisions() {

        String sql = "SELECT Division_ID, Division_Name FROM first_level_divisions";

        try (Statement statement = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while(rs.next()) {
                FirstLevelDivisions division = new FirstLevelDivisions();
                division.setDivisionId(rs.getInt("Division_ID"));
                division.setDivisionName(rs.getString("Division_Name"));
                divisions.add(division);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divisions;
    }
}

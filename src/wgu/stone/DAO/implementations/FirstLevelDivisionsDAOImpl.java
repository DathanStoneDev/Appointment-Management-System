package wgu.stone.DAO.implementations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wgu.stone.DAO.interfaces.FirstLevelDivisionsDAO;
import wgu.stone.database.DatabaseConnection;
import wgu.stone.model.Division;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class FirstLevelDivisionsDAOImpl implements FirstLevelDivisionsDAO {

    /**
     * Retrieves data from the divisions table in the database.
     * Creates division objects and puts them into an ObservableList.
     * returns the ObservableList to be used for ComboBoxes.
     * @return ObservableList of division objects.
     */
    @Override
    public ObservableList<Division> getDivisions() {

        ObservableList<Division> divisions = FXCollections.observableArrayList();

        String sql = "SELECT Division_ID, Division, COUNTRY_ID FROM first_level_divisions";

        try (Statement statement = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                Division div = new Division();
                div.setDivisionId(rs.getInt("Division_ID"));
                div.setCountryId(rs.getInt("COUNTRY_ID"));
                div.setDivisionName(rs.getString("Division"));
                divisions.add(div);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(divisions);
        return divisions;
    }
}

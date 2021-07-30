package wgu.stone.dao.implementations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wgu.stone.dao.interfaces.CountryDAO;
import wgu.stone.database.DatabaseConnection;
import wgu.stone.model.Country;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CountryDAOImpl implements CountryDAO {

    /**
     * Retrieves data from the countries table in the database.
     * Creates country objects and puts them into an ObservableList.
     * returns the ObservableList to be used for ComboBoxes.
     * @return ObservableList of country objects.
     */
    public ObservableList<Country> getCountries() {

        ObservableList<Country> countries = FXCollections.observableArrayList();

        String sql = "SELECT Country, Country_ID FROM countries";

        try(Statement statement = DatabaseConnection.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql)) {
            while(rs.next()) {
                Country country = new Country();
                country.setCountry(rs.getString("Country"));
                country.setCountryId(rs.getInt("Country_ID"));
                countries.add(country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countries;
    }
}

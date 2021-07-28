package wgu.stone.DAO.implementations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wgu.stone.DAO.interfaces.CountryDAO;
import wgu.stone.database.DatabaseConnection;
import wgu.stone.model.Country;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CountryDAOImpl implements CountryDAO {

    private ObservableList<Country> countries = FXCollections.observableArrayList();


    public ObservableList<Country> getCountries() {

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

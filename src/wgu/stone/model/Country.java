package wgu.stone.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Date;
import java.sql.Timestamp;

public class Country {

    private int countryId;
    private String country;
    private Date createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;
    private static ObservableList<Country> countries = FXCollections.observableArrayList();

    public Country(int countryId, String country, Date createDate, String createdBy, Timestamp lastUpdate, String lastUpdateBy) {
        this.countryId = countryId;
        this.country = country;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    public Country() {

    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public static ObservableList<Country> getCountries() {
        return countries;
    }

    public static void setCountries(ObservableList<Country> countries) {
        Country.countries = countries;
    }

    public static void addCountries(Country country) {
        countries.add(country);
    }

    @Override
    public String toString() {
        return country;
    }
}

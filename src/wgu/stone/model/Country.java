package wgu.stone.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Country {

    private int countryId;
    private String country;


    public Country(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;

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


    @Override
    public String toString() {
        return country;
    }
}

package wgu.stone.dao.interfaces;

import javafx.collections.ObservableList;
import wgu.stone.model.Country;

public interface CountryDAO {

    ObservableList<Country> getCountries();
}

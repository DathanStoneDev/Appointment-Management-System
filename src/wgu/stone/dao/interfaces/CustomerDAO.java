package wgu.stone.dao.interfaces;

import javafx.collections.ObservableList;
import wgu.stone.model.Country;
import wgu.stone.model.Customer;
import wgu.stone.model.Division;

/**
 * DAO Interface that the controllers will call to retrieve these methods from the CustomerDAOImpl.
 */
public interface CustomerDAO {

    ObservableList<Customer> getCustomerList();
    ObservableList<Division> getDivisionList();
    ObservableList<Country> getCountryList();
    ObservableList<Customer> getCustomerIdAndNamesList();
    void deleteCustomer(int id);
    void updateCustomer(Customer customer);
    void saveCustomer(Customer customer);

}

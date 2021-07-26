package wgu.stone.DAO;

import javafx.collections.ObservableList;
import wgu.stone.model.Customer;

public interface CustomerDAO {
    ObservableList<Customer> getAllCustomers();
    void deleteCustomer(int id);
    void insertNewCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void filterDivisionList(String countryName);
    void getAllCountries();
    ObservableList<Customer> getCustomerIdAndName();

}

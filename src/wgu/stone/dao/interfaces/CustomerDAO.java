package wgu.stone.dao.interfaces;

import javafx.collections.ObservableList;
import wgu.stone.model.Customer;

public interface CustomerDAO extends GenericDAO<Customer> {

    ObservableList<Customer> getCustomerIdAndName();

}

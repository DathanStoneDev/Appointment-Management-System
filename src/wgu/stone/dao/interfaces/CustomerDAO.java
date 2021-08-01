package wgu.stone.dao.interfaces;

import javafx.collections.ObservableList;
import wgu.stone.model.Customer;
import wgu.stone.model.Division;

public interface CustomerDAO extends GenericDAO<Customer> {

    ObservableList<Customer> getCustomerIdAndName();
    ObservableList<Division> getDivisionList();

}

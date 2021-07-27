package wgu.stone.DAO;

import javafx.collections.ObservableList;
import wgu.stone.model.Contact;

public interface ContactDAO {

    ObservableList<Contact> getAllContacts();
}

package wgu.stone.dao.implementations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wgu.stone.dao.interfaces.ContactDAO;
import wgu.stone.database.DatabaseConnection;
import wgu.stone.model.Contact;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ContactDAOImpl implements ContactDAO {

    @Override
    public ObservableList<Contact> getAllContacts() {
        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        String sql = "SELECT Contact_Name, Contact_ID FROM contacts";

        try(Statement statement = DatabaseConnection.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql)) {
            while(rs.next()) {
                Contact contact = new Contact();
                    contact.setContactId(rs.getInt("Contact_ID"));
                    contact.setContactName(rs.getString("Contact_Name"));
                    contacts.add(contact);
                }
            } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }
}

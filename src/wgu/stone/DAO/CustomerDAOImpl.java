package wgu.stone.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wgu.stone.database.DatabaseConnection;
import wgu.stone.model.Customer;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * Contains the method implementations for customers.
 */
public class CustomerDAOImpl {

    /**
     * Gets all the customers in the database and adds them to an observable list.
     * @return Observable list of customers that is then passed into the Customer controller and initialized there.
     */
    public static ObservableList<Customer> getAllCustomers() {

        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String sql = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, "
                + "customers.Phone, first_level_divisions.Division, countries.Country FROM customers " +
                "JOIN first_level_divisions" +
                " ON customers.Division_ID = first_level_divisions.Division_ID " +
                "JOIN countries ON countries.Country_ID = first_level_divisions.COUNTRY_ID";

        try(Statement statement = DatabaseConnection.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt("Customer_ID"));
                customer.setCustomerName(rs.getString("Customer_Name"));
                customer.setCustomerAddress(rs.getString("Address"));
                customer.setCustomerPostalCode(rs.getString("Postal_Code"));
                customer.setCustomerPhoneNumber(rs.getString("Phone"));
                customer.setDivisionName(rs.getString("Division"));
                customer.setCustomerCountry(rs.getString("Country"));
                allCustomers.add(customer);
            }
            return allCustomers;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Inserts a new customer into the database from a customer object passed by the AddCustomerController
     * @param customer object passed in.
     */
    public static void insertNewCustomer(Customer customer) {

        String sql = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Created_By, " +
                "Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, ?, ?, ?)";

        try(PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {

            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getCustomerAddress());
            ps.setString(3, customer.getCustomerPostalCode());
            ps.setString(4, customer.getCustomerPhoneNumber());
            ps.setString(5, customer.getCreatedBy());
            ps.setString(6, customer.getLastUpdatedBy());
            ps.setInt(7, customer.getDivisionId());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Deletes a customer from the database
     * @param id passed from CustomerMainController to determine customer to delete.
     */
    public static void deleteCustomer(int id) {

        String sql = "DELETE FROM customers WHERE Customer_Id = ? AND ";

        try(PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}

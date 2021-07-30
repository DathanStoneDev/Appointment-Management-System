package wgu.stone.dao.implementations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wgu.stone.dao.interfaces.CustomerDAO;
import wgu.stone.database.DatabaseConnection;
import wgu.stone.model.Customer;

import java.sql.*;



/**
 * Contains the method implementations for customers.
 */
public class CustomerDAOImpl implements CustomerDAO {

                                        //CUSTOMER MAIN FORM PAGE
    /**
     * Retrieves data from the customers table in the database.
     * Creates customer objects and puts them into an ObservableList.
     * returns the ObservableList to be used for ComboBoxes.
     * @return ObservableList of customer objects.
     */
    @Override
    public ObservableList<Customer> getAll() {

        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String sql = "SELECT c.Customer_ID, c.Customer_Name, c.Address, c.Postal_Code, c.Phone, d.Division, co.Country " +
                "FROM customers c " +
                "JOIN first_level_divisions d " +
                "ON c.Division_ID = d.Division_ID " +
                "JOIN countries co ON co.Country_ID = d.COUNTRY_ID";

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
                customer.setCountryName(rs.getString("Country"));
                allCustomers.add(customer);
            }
            return allCustomers;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    @Override
    public void delete(int id) {

        String sql = "DELETE FROM customers WHERE Customer_ID = ?";

        try (PreparedStatement p = DatabaseConnection.getConnection().prepareStatement(sql)) {
            p.setInt(1, id);
            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
                                        //ADD CUSTOMER PAGE

    @Override
    public void save(Customer customer) {

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
            e.printStackTrace();
        }
    }

                                        //UPDATE CUSTOMER PAGE
    @Override
    public void update(Customer customer) {

        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, " +
                "Last_Updated_By = ?, Division_ID = ? " +
                "WHERE Customer_ID = ?";

        try(PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement(sql)) {

            preparedStatement.setInt(7, customer.getCustomerId());
            preparedStatement.setString(1, customer.getCustomerName());
            preparedStatement.setString(2, customer.getCustomerAddress());
            preparedStatement.setString(3, customer.getCustomerPostalCode());
            preparedStatement.setString(4, customer.getCustomerPhoneNumber());
            preparedStatement.setString(5, customer.getLastUpdatedBy());
            preparedStatement.setInt(6, customer.getDivisionId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<Customer> getCustomerIdAndName() {
        ObservableList<Customer> customerIdName = FXCollections.observableArrayList();

        String sql = "SELECT Customer_ID, Customer_Name FROM customers";

        try(Statement statement = DatabaseConnection.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt("Customer_ID"));
                customer.setCustomerName(rs.getString("Customer_Name"));
                customerIdName.add(customer);
            }
            return customerIdName;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

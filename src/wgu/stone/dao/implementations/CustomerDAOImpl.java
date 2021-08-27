package wgu.stone.dao.implementations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wgu.stone.dao.interfaces.CustomerDAO;
import wgu.stone.dao.databaseConnection.DatabaseConnection;
import wgu.stone.model.Country;
import wgu.stone.model.Customer;
import wgu.stone.model.Division;
import java.sql.*;

/**
 * Contains the method implementations for customers.
 */
public class CustomerDAOImpl implements CustomerDAO {

    /**
     * Retrieves data from the customers table in the database and creates an ObservableList.
     * @return ObservableList of customer objects.
     */
    @Override
    public ObservableList<Customer> getCustomerList() {

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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return allCustomers;
    }

    /**
     * Deletes a customer based on the customer ID.
     * Deletes any appointments linked to the customer first due to foreign key restraints.
     * @param id Customer ID parameter that specify the customer to delete.
     */
    @Override
    public void deleteCustomer(int id) {

        String sql1 = "DELETE FROM appointments WHERE Customer_ID = ?";
        String sql2 = "DELETE FROM customers WHERE Customer_ID = ?";
        try (PreparedStatement p = DatabaseConnection.getConnection().prepareStatement(sql1)) {
            p.setInt(1, id);
            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage() + " Customer_ID is invalid");
        }

        try (PreparedStatement p = DatabaseConnection.getConnection().prepareStatement(sql2)) {
            p.setInt(1, id);
            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage() + " Customer_ID is invalid");
        }
    }

    /**
     * Saves a new customer to the database.
     * @param customer Customer Object passed in that will be added to the database.
     */
    @Override
    public void saveCustomer(Customer customer) {

        String sql = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, " +
                "Division_ID) VALUES(?, ?, ?, ?, ?)";

        try(PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {

            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getCustomerAddress());
            ps.setString(3, customer.getCustomerPostalCode());
            ps.setString(4, customer.getCustomerPhoneNumber());
            ps.setInt(5, customer.getDivisionId());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Updates a customer based on the customer ID.
     * @param customer Customer object that provides the necessary data to update in the database.
     */
    @Override
    public void updateCustomer(Customer customer) {

        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, " +
                "Division_ID = ? "+
                "WHERE Customer_ID = ?";

        try(PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement(sql)) {

            preparedStatement.setInt(6, customer.getCustomerId());
            preparedStatement.setString(1, customer.getCustomerName());
            preparedStatement.setString(2, customer.getCustomerAddress());
            preparedStatement.setString(3, customer.getCustomerPostalCode());
            preparedStatement.setString(4, customer.getCustomerPhoneNumber());
            preparedStatement.setInt(5, customer.getDivisionId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a list of Division Objects for the Division ComboBoxes.
     * @return Returns a list of Division Objects.
     */
    @Override
    public ObservableList<Division> getDivisionList() {

        ObservableList<Division> divisionList = FXCollections.observableArrayList();
        String sql = "SELECT Division, Division_ID, COUNTRY_ID FROM first_level_divisions";

        try(Statement statement = DatabaseConnection.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(sql)) {
            while(rs.next()) {
                Division division = new Division();
                division.setDivName(rs.getString("Division"));
                division.setDivId(rs.getInt("Division_ID"));
                division.setCountryID(rs.getInt("COUNTRY_ID"));
                divisionList.add(division);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divisionList;
    }

    /**
     * Retrieves a list of country objects for the country ComboBoxes.
     * @return Returns an observableList of country objects.
     */
    @Override
    public ObservableList<Country> getCountryList() {
        ObservableList<Country> countryList = FXCollections.observableArrayList();
        String sql = "SELECT Country, Country_ID FROM countries";

        try(Statement statement = DatabaseConnection.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql)) {
            while(rs.next()) {
                Country country = new Country();
                country.setCountryName(rs.getString("Country"));
                country.setCountryId(rs.getInt("Country_ID"));
                countryList.add(country);
                System.out.println(country.getCountryId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(countryList);
        return countryList;
    }

    /**
     * Retrieves the customers and their IDs' in a list for the AddAppointmentForm.
     * @return Returns an observable list of Customer names and IDs.
     */
    @Override
    public ObservableList<Customer> getCustomerIdAndNamesList() {

        ObservableList<Customer> customerIdAndNameList = FXCollections.observableArrayList();
        String sql = "SELECT Customer_ID, Customer_Name FROM customers";


        try(Statement statement = DatabaseConnection.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt("Customer_ID"));
                customer.setCustomerName(rs.getString("Customer_Name"));
                customerIdAndNameList.add(customer);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return customerIdAndNameList;
    }
}

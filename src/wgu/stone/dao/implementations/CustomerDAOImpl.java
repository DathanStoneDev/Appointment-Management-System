package wgu.stone.DAO.implementations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wgu.stone.dao.interfaces.CustomerDAO;
import wgu.stone.database.DatabaseConnection;
import wgu.stone.model.Country;
import wgu.stone.model.Customer;
import wgu.stone.model.Division;
import java.sql.*;



/**
 * Contains the method implementations for customers.
 */
public class CustomerDAOImpl implements CustomerDAO {

                                        //CUSTOMER MAIN FORM PAGE
    /**
     * Retrieves data from the customers table in the database.
     * Creates customer objects and puts them into an ObservableList.
     * returns the ObservableList to be used for the customer tableview
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


    @Override
    public void deleteCustomer(int id) {

        String sql1 = "DELETE FROM appointments WHERE Customer_ID = ?";
        String sql2 = "DELETE FROM customers WHERE Customer_ID = ?";

        try (PreparedStatement p = DatabaseConnection.getConnection().prepareStatement(sql2)) {
            p.setInt(1, id);
            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

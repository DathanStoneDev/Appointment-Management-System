package wgu.stone.DAO.implementations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wgu.stone.DAO.interfaces.CustomerDAO;
import wgu.stone.database.DatabaseConnection;
import wgu.stone.model.Customer;

import javax.xml.crypto.Data;
import java.sql.*;



/**
 * Contains the method implementations for customers.
 */
public class CustomerDAOImpl implements CustomerDAO {
                                        //CUSTOMER MAIN FORM PAGE
    /**
     * Gets all the customers in the database and adds them to an observable list.
     * @return Observable list of customers that is then passed into the Customer controller and initialized there.
     */
    @Override
    public ObservableList<Customer> getAll() {

        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customers";

        try(Statement statement = DatabaseConnection.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt("Customer_ID"));
                customer.setCustomerName(rs.getString("Customer_Name"));
                customer.setCustomerAddress(rs.getString("Address"));
                customer.setCustomerPostalCode(rs.getString("Postal_Code"));
                customer.setCustomerPhoneNumber(rs.getString("Phone"));
                customer.setDivisionId(rs.getInt("Division_ID"));
                allCustomers.add(customer);
            }
            return allCustomers;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    @Override
    public void delete(Customer customer) throws SQLException {

        String sql1 = "DELETE FROM appointments WHERE Customer_ID = ?";
        String sql2 = "DELETE FROM customers WHERE Customer_Id = ?";
        Connection conn = DatabaseConnection.getConnection();
        conn.setAutoCommit(false);
        try(PreparedStatement preparedStatement1 = conn.prepareStatement(sql1)){
            preparedStatement1.setInt(1, customer.getCustomerId());
            try (PreparedStatement preparedStatement2= conn.prepareStatement(sql2)) {
                preparedStatement2.setInt(1, customer.getCustomerId());

                conn.commit();
            }
        } catch (SQLException e) {
            conn.rollback();
            System.out.println(e.getMessage());
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
    //This needs to go to countries DAO
    /*@Override
    public void getAllCountries() {

        String sql = "SELECT Country FROM countries";

        try(Statement statement = DatabaseConnection.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql)) {
            while(rs.next()) {
                Country country = new Country();
                country.setCountry(rs.getString("Country"));
                Country.addCountries(country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } */
    //this should go into a map. This also is for the appointment selection table for updating and creating appointments
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

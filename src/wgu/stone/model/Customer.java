package wgu.stone.model;

import javafx.scene.control.Alert;

public class Customer {

    private int customerId;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhoneNumber;
    private String countryName;
    private String divisionName;
    private int divisionId;

    /**
     * Constructor for the tableview.
     */
    public Customer(int customerId, String customerName, String customerAddress, String customerPostalCode,
                    String customerPhoneNumber, String countryName, String divisionName) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhoneNumber = customerPhoneNumber;
        this.countryName = countryName;
        this.divisionName = divisionName;
    }

    /**
     * Constructor in AddCustomer form.
     */
    public Customer(String customerName, String customerAddress, String customerPostalCode, String customerPhoneNumber,
                    String countryName, String divisionName, int divisionId) {
        if(customerName.isBlank() || customerAddress.isBlank() || customerPostalCode.isBlank()
                || customerPhoneNumber.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Empty Fields");
            alert.setContentText("Please ensure fields are not blank");
            alert.show();
            throw new IllegalArgumentException("Empty Fields");
        }
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhoneNumber = customerPhoneNumber;
        this.countryName = countryName;
        this.divisionName = divisionName;
        this.divisionId = divisionId;
    }

    /**
     * Constructor in UpdateCustomer form.
     */
    public Customer(int customerId, String customerName, String customerAddress, String customerPostalCode,
                    String customerPhoneNumber, String countryName, String divisionName, int divisionId) {
        if(customerName.isBlank() || customerAddress.isBlank() || customerPostalCode.isBlank()
                || customerPhoneNumber.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Empty Fields");
            alert.setContentText("Please ensure fields are not blank");
            alert.show();
            throw new IllegalArgumentException("Empty Fields");
        }
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhoneNumber = customerPhoneNumber;
        this.countryName = countryName;
        this.divisionName = divisionName;
        this.divisionId = divisionId;
    }

    /**
     * Default constructor.
     */
    public Customer() {
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    public void setCustomerPostalCode(String customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
}

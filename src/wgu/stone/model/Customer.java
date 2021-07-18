package wgu.stone.model;

import java.sql.Timestamp;
import java.util.Date;

public class Customer {

    private int customerId;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhoneNumber;
    private Date createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;

    //foreign key reference.
    private int divisionId;

}

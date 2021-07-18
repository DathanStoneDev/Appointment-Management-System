package wgu.stone.model;

import java.sql.Timestamp;
import java.util.Date;

public class FirstLevelDivisions {

    private int divisionId;
    private String division;
    private Date createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;

    //foreign key reference.
    private Customer customer;

}

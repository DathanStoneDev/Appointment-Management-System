package wgu.stone.model;

import java.sql.Timestamp;
import java.util.Date;

public class Appointment {

    private int appId;
    private String appTitle;
    private String appDescription;
    private String appLocation;
    private String appType;
    private Date startDatetime;
    private Date endDatetime;
    private Date createdDatetime;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;

    //these are connected via foreign keys. need the ID's.
    private Customer customer;
    private User user;
    private Contact contact;



}

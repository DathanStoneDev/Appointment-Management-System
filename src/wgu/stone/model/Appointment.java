package wgu.stone.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {

    private int appId;
    private String appTitle;
    private String appDescription;
    private String appLocation;
    private String appType;
    private String startDatetime;
    private String endDatetime;
    private int customerId;
    private int contactId;
    private String appContact;
    private int userId;


    public Appointment() {

    }

    public Appointment(String appTitle, String appDescription, String appLocation, String appType,
                     String startDatetime,String endDatetime,
                       String appContact, int userId, int customerId, int contactId) {
        this.appTitle = appTitle;
        this.appDescription = appDescription;
        this.appLocation = appLocation;
        this.appType = appType;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
        this.appContact = appContact;
        this.userId = userId;
        this.customerId = customerId;
        this.contactId = contactId;
}
    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getAppTitle() {
        return appTitle;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    public String getAppDescription() {
        return appDescription;
    }

    public void setAppDescription(String appDescription) {
        this.appDescription = appDescription;
    }

    public String getAppLocation() {
        return appLocation;
    }

    public void setAppLocation(String appLocation) {
        this.appLocation = appLocation;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getStartDatetime() {
        DateTimeFormatter d1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime l = LocalDateTime.parse(startDatetime, d1);
        ZonedDateTime z = l.atZone(ZoneOffset.UTC);
        ZonedDateTime zz = z.withZoneSameInstant(ZoneId.systemDefault());
        String customerStartTime = zz.format(d1);
        return customerStartTime;
    }

    public void setStartDatetime(String startDatetime) { this.startDatetime = startDatetime;}
    //works. Put into Utility for DateTimeFormatting and the method.
    public String getEndDatetime() {
        DateTimeFormatter d1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime l = LocalDateTime.parse(endDatetime, d1);
        ZonedDateTime z = l.atZone(ZoneOffset.UTC);
        ZonedDateTime zz = z.withZoneSameInstant(ZoneId.systemDefault());
        String customerEndTime = zz.format(d1);
        return customerEndTime;
    }

    public void setEndDatetime(String endDatetime) {
        this.endDatetime = endDatetime;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getAppContact() {
        return appContact;
    }

    public void setAppContact(String appContact) {
        this.appContact = appContact;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}

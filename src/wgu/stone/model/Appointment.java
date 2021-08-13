package wgu.stone.model;

import wgu.stone.utility.DateTimeFormatterUtility;

import java.time.LocalDateTime;

public class Appointment {

    private int appId;
    private String appTitle;
    private String appDescription;
    private String appLocation;
    private String appType;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
    private int customerId;
    private int contactId;
    private String appContact;
    private int userId;


    public Appointment() {

    }

    public Appointment(String appTitle, String appDescription, String appLocation, String appType,
                     LocalDateTime startDateTime,LocalDateTime endDatetime,
                       String appContact, int userId, int customerId, int contactId) {
        this.appTitle = appTitle;
        this.appDescription = appDescription;
        this.appLocation = appLocation;
        this.appType = appType;
        this.startDatetime = startDateTime;
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

    public LocalDateTime getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(LocalDateTime startDatetime) { this.startDatetime = startDatetime;}

    public LocalDateTime getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(LocalDateTime endDatetime) {
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

    public String getStartTimeFormatted() {
        return DateTimeFormatterUtility.formatDateTimeForTableview(startDatetime);
    }

    public String getEndTimeFormatted() {
        return DateTimeFormatterUtility.formatDateTimeForTableview(endDatetime);
    }
}

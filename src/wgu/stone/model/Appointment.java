package wgu.stone.model;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Appointment {

    private int appId;
    private String appTitle;
    private String appDescription;
    private String appLocation;
    private String appType;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
    private LocalDateTime createdDatetime;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdateBy;
    private int customerId;
    private String appContact;
    private int contactId;
    private int userId;


    public Appointment() {

    }

    public Appointment(String appTitle, String appDescription, String appLocation, String appType, LocalDateTime startDatetime, LocalDateTime endDatetime, String createdBy, String lastUpdateBy, String appContact, int contactId, int userId, int customerId) {
        this.appTitle = appTitle;
        this.appDescription = appDescription;
        this.appLocation = appLocation;
        this.appType = appType;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
        this.createdBy = createdBy;
        this.lastUpdateBy = lastUpdateBy;
        this.appContact = appContact;
        this.contactId = contactId;
        this.userId = userId;
        this.customerId = customerId;
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatted = startDatetime.format(formatter);
        return formatted;
    }

    public void setStartDatetime(LocalDateTime startDatetime) {
        this.startDatetime = startDatetime;
    }

    public String getEndDatetime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatted = endDatetime.format(formatter);
        return formatted;
    }

    public void setEndDatetime(LocalDateTime endDatetime) {
        this.endDatetime = endDatetime;
    }

    public LocalDateTime getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(LocalDateTime createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
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

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

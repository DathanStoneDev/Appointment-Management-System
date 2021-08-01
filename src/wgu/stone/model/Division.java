package wgu.stone.model;

public class Division {

    private int divId;
    private String divName;
    private int countryID;

    public Division(int divId, String divName) {
        this.divId = divId;
        this.divName = divName;
    }

    public Division(int divId, String divName, int countryID) {
        this.divId = divId;
        this.divName = divName;
        this.countryID = countryID;
    }

    public Division() {

    }

    public Division(String divName) {
        this.divName = divName;
    }

    public int getDivId() {
        return divId;
    }

    public void setDivId(int divId) {
        this.divId = divId;
    }

    public String getDivName() {
        return divName;
    }

    public void setDivName(String divName) {
        this.divName = divName;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    @Override
    public String toString() {
        return divName;
    }
}

package wgu.stone.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FirstLevelDivisions {

    private int divisionId;
    private String divisionName;
    private static ObservableList<FirstLevelDivisions> divisions = FXCollections.observableArrayList();



    public FirstLevelDivisions(int divisionId, String divisionName) {
        this.divisionId = divisionId;
        this.divisionName = divisionName;
    }

    public FirstLevelDivisions() {

    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    @Override
    public String toString() {
        return divisionName;
    }

    public static ObservableList<FirstLevelDivisions> getDivisions() {
        return divisions;
    }

    public static void addDivisions(FirstLevelDivisions division){
        divisions.add(division);
    }

    public static ObservableList<FirstLevelDivisions> clearDivisions() {
        divisions.removeAll(divisions);
        return divisions;
    }
}

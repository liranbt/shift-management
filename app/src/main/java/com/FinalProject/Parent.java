package com.FinalProject;


import java.util.ArrayList;

public class Parent {
    private Shift shift = new Shift();
    private boolean isDeleteButtonClicked;
    private boolean isAddButtonClicked;
    private boolean isHeaderClicked;
    // ArrayList to store child objects
    private ArrayList<Child> children;

    public String getShiftName() {
        return shift.getName();
    }

    public void setShiftName(String shiftName) {
        this.shift.setName(shiftName);
    }

    public String getStartTime() {
        return shift.getStartTime();
    }

    public void setStartTime(String startTime) {
        this.shift.setStartTime(startTime);
    }

    public String getEndTime() {
        return shift.getEndTime();
    }

    public void setEndTime(String endTime) {
        this.shift.setEndTime(endTime);
    }

    public boolean isDeleteButtonClicked() {
        return isDeleteButtonClicked;
    }

    public void setDeleteButtonClicked(boolean deleteClicked) {
        isDeleteButtonClicked = deleteClicked;
    }

    public boolean isAddButtonClicked() {
        return isAddButtonClicked;
    }

    public void setAddButtonClicked(boolean addClicked) {
        isAddButtonClicked = addClicked;
    }

    public ArrayList<Child> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Child> children) {
        this.children = children;
    }

    public int getEmployeesRegistered() {
        return shift.getEmployeesSize();
    }

    public void clearEmployeesRegistered() {
        this.shift.clearEmployees();
    }

    public int getEmployeesRequired() {
        return this.shift.getNumOfEmps();
    }

    public void setEmployeesRequired(int employeesRequired) {
        this.shift.setNumOfEmps(employeesRequired);
    }

    public boolean isHeaderClicked() {
        return isHeaderClicked;
    }

    public void setHeaderClicked(boolean headerClicked) {
        isHeaderClicked = headerClicked;
    }
}

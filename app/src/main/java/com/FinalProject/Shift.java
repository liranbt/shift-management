package com.FinalProject;

import java.util.ArrayList;

public class Shift {
    private String startTime;
    private String endTime;
    private String name;
    private Integer wage;
    private Integer numOfEmps;
    private ArrayList<Long> employees;

    public Shift() {
    }

    public Shift( String name, String startTime, String endTime, Integer wage, Integer numOfEmps) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
        this.wage = wage;
        this.numOfEmps = numOfEmps;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWage() {
        return wage;
    }

    public void setWage(Integer wage) {
        this.wage = wage;
    }

    public Integer getNumOfEmps() {
        return numOfEmps;
    }

    public void setNumOfEmps(Integer numOfEmps) {
        this.numOfEmps = numOfEmps;
    }

    public int getEmployeesSize() {
        return (employees == null) ? 0 : employees.size();
    }

    public void clearEmployees(){this.employees.clear();}
}

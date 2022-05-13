package com.example.weatherupdate;

public class DayTempItem {
    String temp;
    String day;

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public DayTempItem(String temp, String day) {
        this.temp = temp;
        this.day = day;
    }
}

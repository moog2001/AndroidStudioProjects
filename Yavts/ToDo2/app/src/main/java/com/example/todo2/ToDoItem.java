package com.example.todo2;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDate;

public class ToDoItem {
    private long ID;
    private String ToDO;
    private int Status;
    private LocalDate EndDate;

    public ToDoItem(String toDO, int status, LocalDate endDate, DataBaseHandler save) {
        ToDO = toDO;
        Status = status;
        EndDate = endDate;
        long id = save.insertData(this);
        setID(id);

    }

    public ToDoItem() {
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getToDO() {
        return ToDO;
    }

    public void setToDO(String toDO) {
        ToDO = toDO;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public LocalDate getEndDate() {
        return EndDate;
    }

    public void setEndDate(LocalDate endDate) {
        EndDate = endDate;
    }
}

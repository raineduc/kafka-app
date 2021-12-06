package com.example.kafkainternapp.dto;

import java.sql.Timestamp;

public class Record {
    private long id;
    private String name;
    private Timestamp dateTime;

    public Record() {};

    public Record(long id, String name, Timestamp dateTime) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }
}

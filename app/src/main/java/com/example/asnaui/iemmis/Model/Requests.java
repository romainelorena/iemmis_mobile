package com.example.asnaui.iemmis.Model;

public class Requests {

    private String id, formNo, date, time, requestedBy, requestItems, others, receivedBy;

    public Requests(String id, String formNo, String date, String time, String requestedBy, String requestItems, String others, String receivedBy) {
        this.id = id;
        this.formNo = formNo;
        this.date = date;
        this.time = time;
        this.requestedBy = requestedBy;
        this.requestItems = requestItems;
        this.others = others;
        this.receivedBy = receivedBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormNo() {
        return formNo;
    }

    public void setFormNo(String formNo) {
        this.formNo = formNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getRequestItems() {
        return requestItems;
    }

    public void setRequestItems(String requestItems) {
        this.requestItems = requestItems;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }
}

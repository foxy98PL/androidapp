package com.example.projektinynierski.Models;

import com.example.projektinynierski.PatientActivity;

import java.time.OffsetDateTime;

public class PatientCallendar {

    private Long pesel;
    private Long docId;
    private OffsetDateTime dateFrom;
    private  OffsetDateTime dateTo;

    public Long getPesel() {
        return pesel;
    }

    public void setPesel(Long pesel) {
        this.pesel = pesel;
    }

    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }

    public OffsetDateTime getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(OffsetDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    public OffsetDateTime getDateTo() {
        return dateTo;
    }

    public void setDateTo(OffsetDateTime dateTo) {
        this.dateTo = dateTo;
    }

    public PatientCallendar(Long pesel, Long docId, OffsetDateTime dateFrom, OffsetDateTime dateTo) {
        this.pesel = pesel;
        this.docId = docId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }
    public PatientCallendar(){

    }

    @Override
    public String toString() {
        return "PatientCallendar{" +
                "pesel=" + pesel +
                ", docId=" + docId +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                '}';
    }


}

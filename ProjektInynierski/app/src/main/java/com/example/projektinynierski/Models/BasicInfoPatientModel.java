package com.example.projektinynierski.Models;

public class BasicInfoPatientModel {
    private String pesel;
    private String firstname;
    private String secondname;
    private String surrname;
    private String city;
    private String street;
    private String house_nbr;

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    public String getSurrname() {
        return surrname;
    }

    public void setSurrname(String surrname) {
        this.surrname = surrname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse_nbr() {
        return house_nbr;
    }

    public void setHouse_nbr(String house_nbr) {
        this.house_nbr = house_nbr;
    }

    public BasicInfoPatientModel(String pesel, String firstname, String secondname, String surrname, String city, String street, String house_nbr) {
        this.pesel = pesel;
        this.firstname = firstname;
        this.secondname = secondname;
        this.surrname = surrname;
        this.city = city;
        this.street = street;
        this.house_nbr = house_nbr;
    }

    @Override
    public String toString() {
        return "BasicInfoPatientModel{" +
                "pesel=" + pesel +
                ", firstname='" + firstname + '\'' +
                ", secondname='" + secondname + '\'' +
                ", surrname='" + surrname + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", house_nbr='" + house_nbr + '\'' +
                ", house_nbr='" + house_nbr + '\'' +
                ", house_nbr='" + house_nbr + '\'' +
                '}';
    }
}

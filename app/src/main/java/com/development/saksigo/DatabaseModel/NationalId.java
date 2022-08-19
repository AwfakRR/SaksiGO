package com.development.saksigo.DatabaseModel;

public class NationalId {

    public String id, firstname, lastname, gender, address, postal, matchesId, currentAddress, currentPostal;

    public NationalId(){

    }

    public NationalId(String id, String firstname, String lastname, String gender, String address, String postal, String matchesId, String currentAddress, String currentPostal) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.address = address;
        this.postal = postal;
        this.matchesId = matchesId;
        this.currentAddress = currentAddress;
        this.currentPostal = currentPostal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getMatchesId() {
        return matchesId;
    }

    public void setMatchesId(String matchesId) {
        this.matchesId = matchesId;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getCurrentPostal() {
        return currentPostal;
    }

    public void setCurrentPostal(String currentPostal) {
        this.currentPostal = currentPostal;
    }
}
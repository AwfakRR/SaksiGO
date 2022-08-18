package com.development.saksigo.DatabaseModel;

public class NationalId {

    public String stringID, stringFirstN, stringLastN, stringGender, stringAddress, stringPostal, stringSelfieWithId, stringPhotoId, stringMatchesId, stringCAddress, stringCPostal;

    public NationalId(){

    }

    public NationalId(String stringID, String stringFirstN, String stringLastN, String stringGender, String stringAddress, String stringPostal, String stringSelfieWithId, String stringPhotoId, String stringMatchesId, String stringCAddress, String stringCPostal) {
        this.stringID = stringID;
        this.stringFirstN = stringFirstN;
        this.stringLastN = stringLastN;
        this.stringGender = stringGender;
        this.stringAddress = stringAddress;
        this.stringPostal = stringPostal;
        this.stringSelfieWithId = stringSelfieWithId;
        this.stringPhotoId = stringPhotoId;
        this.stringMatchesId = stringMatchesId;
        this.stringCAddress = stringCAddress;
        this.stringCPostal = stringCPostal;
    }

    public String getStringID() {
        return stringID;
    }

    public void setStringID(String stringID) {
        this.stringID = stringID;
    }

    public String getStringFirstN() {
        return stringFirstN;
    }

    public void setStringFirstN(String stringFirstN) {
        this.stringFirstN = stringFirstN;
    }

    public String getStringLastN() {
        return stringLastN;
    }

    public void setStringLastN(String stringLastN) {
        this.stringLastN = stringLastN;
    }

    public String getStringGender() {
        return stringGender;
    }

    public void setStringGender(String stringGender) {
        this.stringGender = stringGender;
    }

    public String getStringAddress() {
        return stringAddress;
    }

    public void setStringAddress(String stringAddress) {
        this.stringAddress = stringAddress;
    }

    public String getStringPostal() {
        return stringPostal;
    }

    public void setStringPostal(String stringPostal) {
        this.stringPostal = stringPostal;
    }

    public String getStringSelfieWithId() {
        return stringSelfieWithId;
    }

    public void setStringSelfieWithId(String stringSelfieWithId) {
        this.stringSelfieWithId = stringSelfieWithId;
    }

    public String getStringPhotoId() {
        return stringPhotoId;
    }

    public void setStringPhotoId(String stringPhotoId) {
        this.stringPhotoId = stringPhotoId;
    }

    public String getStringMatchesId() {
        return stringMatchesId;
    }

    public void setStringMatchesId(String stringMatchesId) {
        this.stringMatchesId = stringMatchesId;
    }

    public String getStringCAddress() {
        return stringCAddress;
    }

    public void setStringCAddress(String stringCAddress) {
        this.stringCAddress = stringCAddress;
    }

    public String getStringCPostal() {
        return stringCPostal;
    }

    public void setStringCPostal(String stringCPostal) {
        this.stringCPostal = stringCPostal;
    }
}

package com.development.saksigo.DatabaseModel;

public class Users {

    public String email, fullname, phoneNumber, termsOpt, privacyOpt, newsOpt;

    public Users(String email, String fullname, String phoneNumber, String termsOpt, String privacyOpt, String newsOpt) {
        this.email = email;
        this.fullname = fullname;
        this.phoneNumber = phoneNumber;
        this.termsOpt = termsOpt;
        this.privacyOpt = privacyOpt;
        this.newsOpt = newsOpt;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTermsOpt() {
        return termsOpt;
    }

    public void setTermsOpt(String termsOpt) {
        this.termsOpt = termsOpt;
    }

    public String getPrivacyOpt() {
        return privacyOpt;
    }

    public void setPrivacyOpt(String privacyOpt) {
        this.privacyOpt = privacyOpt;
    }

    public String getNewsOpt() {
        return newsOpt;
    }

    public void setNewsOpt(String newsOpt) {
        this.newsOpt = newsOpt;
    }
}

package com.sdl.knowlegdetrove;

public class UserhelperClass {
    private String fullName,email,userName,password, phoneno;


    public UserhelperClass() {
    }

    public UserhelperClass(String fullName, String email, String userName, String password, String phoneno) {
        this.fullName = fullName;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.phoneno = phoneno;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneno() {
        return phoneno;
    }
}

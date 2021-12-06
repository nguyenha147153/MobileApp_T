package com.example.mobileapplayout1;

public class Account {
    public String fullname, address, email, password, phone;

    public Account(){
    }
    public Account(String fullname, String address, String email, String password, String phone){
        this.fullname = fullname;
        this.address = address;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }
}

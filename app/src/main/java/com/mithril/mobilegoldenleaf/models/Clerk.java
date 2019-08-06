package com.mithril.mobilegoldenleaf.models;


public class Clerk extends User {
    private String email;
    private String password;

    public Clerk(String name, String phoneNumber, String email, String password) {
        super(name, phoneNumber);
        this.email = email;
        this.password = password;
    }

    public Clerk(int id, String name, String phoneNumber, String email, String password) {
        super(id, name, phoneNumber);
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}

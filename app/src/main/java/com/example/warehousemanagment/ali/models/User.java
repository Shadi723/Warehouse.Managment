package com.example.warehousemanagment.ali.models;

public class User {

    private int id;
    private String name;
    private String userName;
    private String email;
    private String permissions;
    private String company;

    public User() {

        name = null;
        userName = null;
        email = null;
    }

    public User(int id, String name, String userName, String email, String permissions, String company) {

        this.id = id;
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.permissions = permissions;
        this.company = company;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPermissions() {
        return permissions;
    }

    public String getCompany() {
        return company;
    }

}

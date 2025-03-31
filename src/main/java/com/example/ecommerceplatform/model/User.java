package com.example.ecommerceplatform.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class User {
    private String id;
    private String role; // "Merchant" or "Consumer"

    public User(String id, String role) {
        this.id = id;
        this.role = role;
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public static ResultSet getIdByPhone(String phone, String table){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();
        String connectQuery = "select * from "+table+" where phone=\""+ phone+"\"";

        try{
            Statement statement = connectionDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            return queryOutput;


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
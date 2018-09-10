package com.zerohunger.zerohungertrader.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Dhanushka Dharmasena on 10/09/2018.
 */
@IgnoreExtraProperties
public class User {

    public String firstName;
    public String lastName;
    public String type;

    public User() {
    }

    public User(String firstName, String lastName, String type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
    }

}
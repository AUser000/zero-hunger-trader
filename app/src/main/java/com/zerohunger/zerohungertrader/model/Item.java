package com.zerohunger.zerohungertrader.model;

/**
 * Created by Dhanushka Dharmasena on 10/09/2018.
 */
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Item {

    public String name;
    public String categoryId;

    public Item() {
    }

    public Item(String name, String categoryId) {
        this.name = name;
        this.categoryId = categoryId;
    }
}

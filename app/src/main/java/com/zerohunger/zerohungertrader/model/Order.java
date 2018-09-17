package com.zerohunger.zerohungertrader.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Dhanushka Dharmasena on 12/09/2018.
 */

@IgnoreExtraProperties
public class Order {

    public String clientId;
    public String clientName;
    public String itemId;
    public String itemName;
    public int price;
    public int quantity;
    public Long startedTime;
    public String traderId;

    public Order() {
    }

    public Order(String clientId,String clientName ,String itemId,String itemName ,int price, int qut, Long startedTime, String traderId) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.quantity = qut;
        this.startedTime = startedTime;
        this.traderId = traderId;
    }

}

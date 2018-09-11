package com.zerohunger.zerohungertrader.model;

public class Inventory {

    public String traderId;
    public String itemId;
    public int price;
    public int quantity;
    public Long startedTime;
    public Double lat;
    public Double lng;

    public Inventory() {
    }

    public Inventory(
            String traderId,
            String itemId,
            int price,
            int quantity,
            Long startedTime,
            Double lat,
            Double lng)
    {
        this.traderId = traderId;
        this.itemId = itemId;
        this.price = price;
        this.quantity = quantity;
        this.startedTime = startedTime;
        this.lat = lat;
        this.lng = lng;
    }
}

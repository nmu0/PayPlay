package com.example.demo;

public class RedeemRequest {
    private int coins;
    private double lat;
    private double lng;
    private int radiusMiles = 10;

    public int getCoins() { return coins; }
    public void setCoins(int coins) { this.coins = coins; }
    public double getLat() { return lat; }
    public void setLat(double lat) { this.lat = lat; }
    public double getLng() { return lng; }
    public void setLng(double lng) { this.lng = lng; }
    public int getRadiusMiles() { return radiusMiles; }
    public void setRadiusMiles(int radiusMiles) { this.radiusMiles = radiusMiles; }
}

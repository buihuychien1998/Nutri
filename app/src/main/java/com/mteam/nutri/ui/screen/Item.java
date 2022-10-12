package com.mteam.nutri.ui.screen;

public class Item {
    private String cost;
    private final String itemTitle;
    private int itemImageResourceId;
    private final String placeLocation;
    private String[] place_Highlights;
    private String itemProvider;
    private String imageUrl;

    public Item(String title, int imageResourceId, String location) {
        itemTitle = title;
        itemImageResourceId = imageResourceId;
        placeLocation = location;
    }

    public Item(String title, String imageUrl, String location) {
        itemTitle = title;
        this.imageUrl = imageUrl;
        placeLocation = location;
    }

    public Item(String title,String location) {
        itemTitle = title;
        placeLocation = location;
    }




    public Item(String title, int imageResourceId, String location, String[] highlights, String provider) {
        itemTitle = title;
        itemImageResourceId = imageResourceId;
        placeLocation = location;
        place_Highlights = highlights;
        itemProvider = provider;
    }


    public String[] getHighlights() {
        return place_Highlights;
    }


    public String getProvider() {
        return itemProvider;
    }


    public String getTitle() {
        return itemTitle;
    }


    public int getImageResourceId() {
        return itemImageResourceId;
    }
    public String getImageUrl() {
        return imageUrl;
    }


    public String getLocation() {
        return placeLocation;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}

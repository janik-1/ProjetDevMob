package com.example.trainingapp.Classe;

public class Match {
    private int id;
    private String date;
    private String adresse;
    private double latitude;
    private double longitude;
    private String image;

    public Match() {
    }

    public Match(String date, String adresse, double latitude, double longitude, String image) {
        this.date = date;
        this.adresse = adresse;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
    }

    public Match(int id, String date, String adresse, double latitude, double longitude, String image) {
        this.id = id;
        this.date = date;
        this.adresse = adresse;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

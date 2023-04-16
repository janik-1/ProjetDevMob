package com.example.trainingapp.Classe;

public class Statistiques {
    private int id;
    private int matchId;

    private String adresseM;
    private int butsMarques;
    private int butsEncaisses;
    private double possession;
    private int tirs;
    private int tirsCadres;

    public Statistiques(String adresse, int butsMarques, int butsEncaisses, double possession, int tirs, int tirsCadres) {
        this.adresseM = adresse;
        this.butsMarques = butsMarques;
        this.butsEncaisses = butsEncaisses;
        this.possession = possession;
        this.tirs = tirs;
        this.tirsCadres = tirsCadres;
    }

    public Statistiques(int id, int matchId, int butsMarques, int butsEncaisses, double possession, int tirs, int tirsCadres) {
        this.id = id;
        this.matchId = matchId;
        this.butsMarques = butsMarques;
        this.butsEncaisses = butsEncaisses;
        this.possession = possession;
        this.tirs = tirs;
        this.tirsCadres = tirsCadres;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdresseM() {
        return adresseM;
    }
    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getButsMarques() {
        return butsMarques;
    }

    public void setButsMarques(int butsMarques) {
        this.butsMarques = butsMarques;
    }

    public int getButsEncaisses() {
        return butsEncaisses;
    }

    public void setButsEncaisses(int butsEncaisses) {
        this.butsEncaisses = butsEncaisses;
    }

    public double getPossession() {
        return possession;
    }

    public void setPossession(double possession) {
        this.possession = possession;
    }

    public int getTirs() {
        return tirs;
    }

    public void setTirs(int tirs) {
        this.tirs = tirs;
    }

    public int getTirsCadres() {
        return tirsCadres;
    }

    public void setTirsCadres(int tirsCadres) {
        this.tirsCadres = tirsCadres;
    }
}

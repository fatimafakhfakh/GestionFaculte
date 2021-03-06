package com.example.gestionfaculte.model;

public class Etablissement {


    private  int id ;
    private String Libelle  ;
    private String adresse  ;


    public Etablissement(int id, String libelle, String adresse) {
        this.id = id;
        Libelle = libelle;
        this.adresse = adresse;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return Libelle;
    }

    public void setLibelle(String libelle) {
        Libelle = libelle;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Override
    public String toString() {
        return "Etablissement{" +
                "id=" + id +
                ", Libelle='" + Libelle + '\'' +
                ", adresse='" + adresse + '\'' +
                '}';
    }
}

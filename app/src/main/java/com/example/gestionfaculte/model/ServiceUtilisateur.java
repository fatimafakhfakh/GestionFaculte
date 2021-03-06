package com.example.gestionfaculte.model;

import java.util.Date;

public class ServiceUtilisateur {

    private  int  id ;
    private  int id_service  ;
    private  String  libelle_service  ;
    private Date dateDemande  ;
    private  String numeroEtat  ;
    private  String  libelle_etat ;


    public ServiceUtilisateur(int id, int id_service, String libelle_service, Date dateDemande, String numeroEtat, String libelle_etat) {
        this.id = id;
        this.id_service = id_service;
        this.libelle_service = libelle_service;
        this.dateDemande = dateDemande;
        this.numeroEtat = numeroEtat;
        this.libelle_etat = libelle_etat;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_service() {
        return id_service;
    }

    public void setId_service(int id_service) {
        this.id_service = id_service;
    }

    public String getLibelle_service() {
        return libelle_service;
    }

    public void setLibelle_service(String libelle_service) {
        this.libelle_service = libelle_service;
    }

    public Date getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(Date dateDemande) {
        this.dateDemande = dateDemande;
    }

    public String getNumeroEtat() {
        return numeroEtat;
    }

    public void setNumeroEtat(String numeroEtat) {
        this.numeroEtat = numeroEtat;
    }

    public String getLibelle_etat() {
        return libelle_etat;
    }

    public void setLibelle_etat(String libelle_etat) {
        this.libelle_etat = libelle_etat;
    }

    @Override
    public String toString() {
        return "ServiceUtilisateur{" +
                "id=" + id +
                ", id_service=" + id_service +
                ", libelle_service='" + libelle_service + '\'' +
                ", dateDemande=" + dateDemande +
                ", numeroEtat='" + numeroEtat + '\'' +
                ", libelle_etat='" + libelle_etat + '\'' +
                '}';
    }
}

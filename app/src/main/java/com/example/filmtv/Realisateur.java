package com.example.filmtv;

public class Realisateur {
    private String idRealisateur;
    // Nom du réalisateur
    private String nom;
    // Date de naissance
    private String datenaissance;
    // nationalite du realisateur
    private String nationalite;

    // Constructeur de la classe Realisateur
    public Realisateur(String idRealisateur, String nom, String datenaissance, String nationalite) {
        this.idRealisateur = idRealisateur;
        this.nom = nom;
        this.datenaissance = datenaissance;
        this.nationalite = nationalite;
    }

    // Les méthodes de la classe Realisateur

    public String getIdRealisateur() {
        return idRealisateur;
    }

    public void setIdRealisateur(String idRealisateur) {
        this.idRealisateur = idRealisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    public String getDatenaissance() {
        return datenaissance;
    }

    public void setDatenaissance(String datenaissance) {
        this.datenaissance = datenaissance;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

}

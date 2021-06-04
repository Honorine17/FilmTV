package com.example.filmtv;

public class Genre {
    //id du genre
    String  id;
    // nom du genre
     String nom;
    // Description du genre
     String description;



    // Constructeur de la classe Genre
    public Genre(String id, String nom, String description) {
        this.id=id;
        this.nom = nom;
        this.description = description;

    }

    // Les méthodes de la classe Genre


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}


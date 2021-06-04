package com.example.filmtv;

public class Film {
    //id du film
    private String id;
    // Description du film
    private String description;
    // Année de sortie du film
    private String annee;
    // Nom du film
    private String name;
    // Pays d'origine du film
    private String pays;
    // id du réalisateur
    private String idreal;
    //id genre
    private String idgenre;


    // Constructeur de la classe Film
    public Film(String id, String name, String description, String annee, String pays, String idreal, String idgenre) {
        this.id = id;
        this.name= name;
        this.description = description;
        this.annee = annee;
        this.pays=pays;
        this.idreal=idreal;
        this.idgenre=idgenre;
    }
    // Les méthodes de la classe Film

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id=id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee (String annee) {
        this.annee = annee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getIdreal() {
        return idreal;
    }

    public void setIdreal(String idreal) {
        this.idreal = idreal;
    }

    public String getIdgenre() {
        return idgenre;
    }

    public void setIdgenre(String idgenre) {
        this.idgenre = idgenre;
    }

}
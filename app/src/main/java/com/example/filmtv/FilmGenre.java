package com.example.filmtv;

public class FilmGenre {

    private String idFilm;
    private String idGenre;

    public FilmGenre(String idFilm, String idGenre){
        this.idFilm = idFilm;
        this.idGenre = idGenre;
    }

    public String getIdFilm(){
        return idFilm;
    }

    public String getIdGenre(){
        return idGenre;
    }

    public void setIdFilm(String idFilm) {
        this.idFilm = idFilm;
    }

    public void setIdGenre(String idGenre) {
        this.idGenre = idGenre;
    }
}

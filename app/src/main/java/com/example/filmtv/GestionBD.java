package com.example.filmtv;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GestionBD {

    // Instanciation des valeurs
    SQLiteDatabase maBase;
    BDHelper bdHelper;

    // Constructeur de GestionBD
    public GestionBD(Context c) {
        bdHelper = new BDHelper(c, "FilmTV", null, 1);
    }

    // Méthode pour ouvrir la base de données
    public void open() {
        maBase = bdHelper.getWritableDatabase();
    }

    //Méthode pour fermer la base de données
    public void close() {
        maBase.close();
    }

    //Méthode pour supprimer les tables de la base de données

    public void vidertable() {
        maBase.delete("listeFilmGenre", null, null);
        maBase.delete("film", null, null);
        maBase.delete("genre", null, null);
        maBase.delete("realisateur", null, null);
    }

    // méthode pour supprimer la table que l'on veut
    public void vidertableRealisateur() {
        maBase.delete("realisateur", null, null);
    }

    public void videLaTableFilm() {
        maBase.delete("film", null, null);
    }

    public void vidertableGenre() {
        maBase.delete("genre", null, null);
    }

    public void vidertableListeFilmGenre() {
        maBase.delete("listeFilmGenre", null, null);
    }

    // CRUD Table Film

    // méthode pour récupérer le libelle de la table film
    public ArrayList<String> donnelesFilms() {
        // on instancie une liste
        ArrayList<String> listeSQL = new ArrayList<String>();
        // requete pour recuperer les libelles de la table film
        String req = "select titreFilm " +
                "from film " +
                "order by titreFilm asc";
        // on instancie le curseur
        Cursor c = maBase.rawQuery(req, null);
        while (c.moveToNext()) {
            //ajout du titre dans la liste
            listeSQL.add(c.getString(0));
        }
        // return de la liste
        return listeSQL;
    }

    // méthode pour ajouter une ligne dans la table film
    public void ajouteFilm(Film leFilm) {
        // on instancie le ContentValues
        ContentValues val = new ContentValues();
        // on ajoute des valeurs dans le ContentValues
        val.put("idFilm", leFilm.getId());
        val.put("titreFilm", leFilm.getName());
        val.put("anneeFilm", leFilm.getAnnee());
        val.put("descriptionFilm", leFilm.getDescription());
        val.put("paysFilm", leFilm.getPays());
        val.put("idRealisateur", leFilm.getIdreal());
        // la fonciton pour ajouter une ligne dans la table film
        maBase.insert("film", null, val);
    }

    // méthode pour supprimer une ligne dans la table film
    public void supprimeFilm(String id) {
        // la requete pour supprimer une ligne de la table film
        maBase.execSQL("DELETE " +
                "FROM film " +
                "WHERE film.idFilm =\"" + id + "\";");
    }

    // méthode pour modifier une ligne  dans la table film
    public void ModifFilm(Film leFilm, String CdeFilmOriginal) {
        // la requete pour modifier la ligne de la table film
        maBase.execSQL("UPDATE film " +
                "SET idFilm = \"" + leFilm.getId() + "\", " +
                "titreFilm = \"" + leFilm.getName() + "\", " +
                "anneeFilm = \"" + leFilm.getAnnee() + "\", " +
                "descriptionFilm = \"" + leFilm.getDescription() + "\", " +
                "paysFilm = \"" + leFilm.getPays() + "\", " +
                "idRealisateur = \"" + leFilm.getIdreal() + "\" " +
                "WHERE idFilm = \"" + CdeFilmOriginal + "\"");
    }

    // méthode pour récupérer l'id d'une ligne de la table film
    public String getIDFilm(String leTitre) {
        // la requete pour récupérer le titre
        String req = "SELECT idFilm " +
                "FROM film " +
                "WHERE titreFilm = \"" + leTitre + "\";";
        // on instancie un curseur
        Cursor c = maBase.rawQuery(req, null);
        c.moveToNext();
        // on récupère le résultat
        String res = c.getString(c.getColumnIndex("idFilm"));
        // on retourne le résultat
        return res;
    }

    //méthode pour récupérer le titre d'une ligne de la table film
    public String getTitreFilm(String idFilm) {
        // laa requete pour récupèrer le titre
        String req = "SELECT titreFilm " +
                "FROM film " +
                "WHERE idFilm = \"" + idFilm + "\";";
        // on instancie le curseur
        Cursor c = maBase.rawQuery(req, null);
        c.moveToNext();
        // on récupère le résultat
        String res = c.getString(c.getColumnIndex("titreFilm"));
        // on retourne le résultat
        return res;
    }

    //méthode pour récupérer l'année d'une ligne de la table film
    public String getAnneeFilm(String leTitre) {
        // la requete pour récupérer l'année
        String req = "SELECT anneeFilm " +
                "FROM film " +
                "WHERE titreFilm = \"" + leTitre + "\";";
        // on instancie le curseur
        Cursor c = maBase.rawQuery(req, null);
        c.moveToNext();
        // on récupère le résultat
        String res = c.getString(c.getColumnIndex("anneeFilm"));
        // on retourne le résultat
        return res;
    }

    //méthode pour récupérer la description d'une ligne de la table film
    public String getDescriptionFilm(String leTitre) {
        // la requete pour récupérer la description
        String req = "SELECT descriptionFilm " +
                "FROM film " +
                "WHERE titreFilm = \"" + leTitre + "\";";
        // on instancie le curseur
        Cursor c = maBase.rawQuery(req, null);
        c.moveToNext();
        // on récupère le résultat
        String res = c.getString(c.getColumnIndex("descriptionFilm"));
        // on retourne le résultat
        return res;
    }

    //méthode pour récupérer le pays d'une ligne de la table film
    public String getPaysFilm(String leTitre) {
        // la requete pour récupérer le pays
        String req = "SELECT paysFilm " +
                "FROM film " +
                "WHERE titreFilm = \"" + leTitre + "\";";
        // on instancie le curseur
        Cursor c = maBase.rawQuery(req, null);
        c.moveToNext();
        // on récupère le résultat
        String res = c.getString(c.getColumnIndex("paysFilm"));
        // on retourne le résultat
        return res;
    }

    //méthode pour récupérer l'id du réalisateur d'une ligne de la table film
    public String geIdRealisateurFilm(String leTitre) {
        // la requete pour récupérer l'id du réalisateur d'une ligne de la table film
        String req = "SELECT idRealisateur " +
                "FROM film " +
                "WHERE titreFilm = \"" + leTitre + "\";";
        // on instancie le curseur
        Cursor c = maBase.rawQuery(req, null);
        c.moveToNext();
        // on récupère le résultat
        String res = c.getString(c.getColumnIndex("idRealisateur"));
        // on retourne le résultat
        return res;
    }

    //méthode pour récupèrer le nom du réalisateur d'une ligne de la table realisateur
    public String getIdRealFilm(String leTitre) {
        // requette pour recuperer le libelle de la catégorie
        String req = "SELECT realisateur.nomRealisateur " +
                "AS libelleReal " +
                "FROM film " +
                "INNER JOIN realisateur " +
                "ON film.idRealisateur = realisateur.idRealisateur " +
                "WHERE film.titreFilm =\""+leTitre+"\";";
        // instanciation d'un curseur
        Cursor c = maBase.rawQuery(req,null);
        c.moveToNext();
        // recuperation du resultat
        String res = c.getString(c.getColumnIndex("libelleReal"));
        // return du resultat
        return res;
    }

    //méthode pour récupèrer le nom du réalisateur d'une ligne de la table realisateur
    public String getIdGenreFilm(String leTitre) {
        // la requete pour récupèrer le nom du réalisateur
        String req = "SELECT nom " +
                "FROM genre " +
                "INNER JOIN listeFilmGenre " +
                "ON genre.idGenre = listeFilmGenre.numeroGenre " +
                "INNER JOIN film " +
                "ON listeFilmGenre.codeFilm = film.idFilm " +
                "WHERE film.titreFilm = \""+leTitre+"\";";
        Log.i("doInBack",""+req);
        // on instancie le curseur
        Cursor c = maBase.rawQuery(req, null);
        c.moveToNext();
        // on récupère l'id du réalisateur
        String res = c.getString(c.getColumnIndex("nom"));
        // on retourne le résultat
        return res;
    }

    // CRUD table Realisateur

    // methode pour récupérer les noms de la table réalisateur
    public ArrayList<String> donneLesRealisateur() {
        //on instancie la liste
        ArrayList<String> listeSQL = new ArrayList<>();
        // la requete pour récupérer les noms de la table réalisateur
        String req = "SELECT nomRealisateur " +
                "FROM realisateur " +
                "ORDER BY nomRealisateur asc";
        // on instancie le curseur
        Cursor c = maBase.rawQuery(req, null);
        while (c.moveToNext()) {
            // on ajoute le nom dans la liste
            listeSQL.add(c.getString(0));
        }
        // on retourne la liste
        return listeSQL;
    }

    //méthode pour ajouter une ligne dans la table réalisateur
    public void ajouterRealisateur(Realisateur leRealisateur) {
        // on instancie le ContentValues
        ContentValues val = new ContentValues();
        // on ajoute les valeurs dans le ContentValues
        val.put("idRealisateur", leRealisateur.getIdRealisateur());
        val.put("nomRealisateur", leRealisateur.getNom());
        val.put("datenaissanceRealisateur", leRealisateur.getDatenaissance());
        val.put("nationaliteRealisateur", leRealisateur.getNationalite());
        // la fonction permettant d'ajouter une ligne dan la table réalisateur
        maBase.insert("realisateur", null, val);
    }

    //méthode pour modifier une ligne de la table réalisateur
    public void ModifRealisateur(Realisateur leRealisateur, String lIdRealisateurOriginal) {
        // la requete pour ajouter une ligne de la table réalisateur
        maBase.execSQL("UPDATE realisateur " +
                "SET idRealisateur = \"" + leRealisateur.getIdRealisateur() + "\", " +
                "nomRealisateur = \"" + leRealisateur.getNom() + "\", " +
                "datenaissanceRealisateur = \"" + leRealisateur.getDatenaissance() + "\", " +
                "nationaliteRealisateur = \"" + leRealisateur.getNationalite() + "\" " +
                "WHERE idRealisateur = " + lIdRealisateurOriginal + "");
    }

    //méthode pour supprimer une ligne de la table réalisateur
    public void supprimeRealisateur(String lIdReal) {
        //la fonction pour supprimer une ligne de la table réalisateur
        maBase.execSQL("DELETE " +
                "FROM realisateur " +
                "WHERE idRealisateur = \"" + lIdReal + "\";");
    }

    //méthode pou récupérer l'id du réalisateur d'une ligne de la table réalisateur
    public String getidRealRealisateur(String leTitre) {
        // la requete pour récupérer l'id du réalisateur d'une ligne de la table réalisateur
        String req = "SELECT idRealisateur " +
                "FROM realisateur " +
                "WHERE nomRealisateur = \"" + leTitre + "\";";
        // on instancie le curseur
        Cursor c = maBase.rawQuery(req, null);
        c.moveToNext();
        // on récupère l'id du réalisateur
        String res = c.getString(c.getColumnIndex("idRealisateur"));
        // on retourne le résultat
        return res;
    }

    //méthode pou récupérer le nom réalisateur d'une ligne de la table réalisateur
    public String getNomRealisateur(String leTitre) {
        // la requete pour récupérer le nom du réalisateur d'une ligne de la table réalisateur
        String req = "SELECT nomRealisateur " +
                "FROM realisateur " +
                "WHERE nomRealisateur = \"" + leTitre + "\";";
        // on instancie le curseur
        Cursor c = maBase.rawQuery(req, null);
        c.moveToNext();
        // on récupère l'id du réalisateur
        String res = c.getString(c.getColumnIndex("nomRealisateur"));
        // on retourne le résultat
        return res;
    }

    //méthode pou récupérer le prenom réalisateur d'une ligne de la table réalisateur
    public String getPrenomRealisateur(String leTitre) {
        // la requete pour récupérer le nom du réalisateur d'une ligne de la table réalisateur
        String req = "SELECT prenomRealisateur " +
                "FROM realisateur " +
                "WHERE nomRealisateur = \"" + leTitre + "\";";
        // on instancie le curseur
        Cursor c = maBase.rawQuery(req, null);
        c.moveToNext();
        // on récupère l'id du réalisateur
        String res = c.getString(c.getColumnIndex("prenomRealisateur"));
        // on retourne le résultat
        return res;
    }

    //méthode pou récupérer la date de naissance réalisateur d'une ligne de la table réalisateur
    public String getDateNaissanceRealisateur(String leTitre) {
        // la requete pour récupérer la date de naissance du réalisateur d'une ligne de la table réalisateur
        String req = "SELECT datenaissanceRealisateur " +
                "FROM realisateur " +
                "WHERE nomRealisateur = \"" + leTitre + "\";";
        // on instancie le curseur
        Cursor c = maBase.rawQuery(req, null);
        c.moveToNext();
        // on récupère l'id du réalisateur
        String res = c.getString(c.getColumnIndex("datenaissanceRealisateur"));
        // on retourne le résultat
        return res;
    }

    //méthode pou récupérer la nationalité du réalisateur d'une ligne de la table réalisateur
    public String getNationaliteRealisateur(String leTitre) {
        // la requete pour récupérer la nationalité du réalisateur d'une ligne de la table réalisateur
        String req = "SELECT nationaliteRealisateur " +
                "FROM realisateur " +
                "WHERE nomRealisateur = \"" + leTitre + "\";";
        // on instancie le curseur
        Cursor c = maBase.rawQuery(req, null);
        c.moveToNext();
        // on récupère l'id du réalisateur
        String res = c.getString(c.getColumnIndex("nationaliteRealisateur"));
        // on retourne le résultat
        return res;
    }

    //CRUD table genre

    //méthode pour récupérer le nom de la tbale genre
    public ArrayList<String> donneLesGenre() {
        // on instancie la liste
        ArrayList<String> listeSQL = new ArrayList<String>();
        // la requete pour récupérer les noms de la table genre
        String req = "SELECT nom " +
                "FROM genre " +
                "ORDER BY nom asc";
        // on instancie le curseur
        Cursor c = maBase.rawQuery(req, null);
        while (c.moveToNext()) {
            // on ajoute les valeurs dans la liste
            listeSQL.add(c.getString(0));
        }
        // on retourne la liste
        return listeSQL;
    }

    //méthode pour ajouter une ligne dans la table genre
    public void ajouteGenre(Genre leGenre) {
        // on instancie le ContentValues
        ContentValues val = new ContentValues();
        //on ajoute les valeurs dans le ContentValues
        val.put("idGenre", leGenre.getId());
        val.put("nom", leGenre.getNom());
        val.put("description", leGenre.getDescription());
        //la fonction pour ajouter une ligne dans la table genre
        maBase.insert("genre", null, val);
    }

    //méthode pour modifier une ligne de la table genre
    public void ModifGenre(Genre lIGenre, String lIdGenreOriginal) {
        // la requete pour modifier une ligne de la table genre
        maBase.execSQL("UPDATE genre SET idGenre = \"" + lIGenre.getId() + "\",  " +
                "nom = \"" + lIGenre.getNom() + "\",  " +
                "description = \"" + lIGenre.getDescription() + "\" " +
                "WHERE idGenre = \"" + lIdGenreOriginal + "\"");
    }

    //méthode pour supprimer une ligne de la table genre
    public void supprimeGenre(String lIdGenr) {
        // la requete  pour supprimer une ligne de la table genre
        maBase.execSQL("DELETE " +

                "FROM genre " +
                "WHERE idGenre = \"" + lIdGenr + "\";");
    }

    //méthode pour récupérer l'id d'une ligne de la table genre
    public String getNumeroGenre(String leTitre) {
        // la requete pour récupérer l"id d'une ligne de la table genre
        String req = "SELECT idGenre " +
                "FROM genre " +
                "WHERE nom = \"" + leTitre + "\";";
        // on instancie le curseur
        Cursor c = maBase.rawQuery(req, null);
        c.moveToNext();
        // on récupère le résultat
        String res = c.getString(c.getColumnIndex("idGenre"));
        // on retourne le résultat
        return res;
    }

    //méthode pour récupérer le nom d'une ligne de la table genre
    public String getNomGenre(String lId) {
        // la requete pour récupérer l"id d'une ligne de la table genre
        String req = "SELECT nom " +
                "FROM genre " +
                "WHERE idGenre = \"" + lId + "\";";
        // on instancie le curseur
        Cursor c = maBase.rawQuery(req, null);
        c.moveToNext();
        // on récupère le résultat
        String res = c.getString(c.getColumnIndex("nom"));
        // on retourne le résultat
        return res;
    }

    //méthode pour récupérer la description d'une ligne de la table genre
    public String getDescriptionGenre(String leTitre) {
        // la requete pour récupérer l"id d'une ligne de la table genre
        String req = "SELECT description " +
                "FROM genre " +
                "WHERE nom = \"" + leTitre + "\";";
        // on instancie le curseur
        Cursor c = maBase.rawQuery(req, null);
        c.moveToNext();
        // on récupère le résultat
        String res = c.getString(c.getColumnIndex("description"));
        // on retourne le résultat
        return res;
    }

    //méthode pour récupérer le nom de la table filmgenre
    public ArrayList<String> donneLesFilmGenre(String id) {
        // on instancie la liste
        ArrayList<String> listeSQL = new ArrayList<String>();
        // la requete pour récupérer les noms de la table genre
        String req = "SELECT film.titreFilm " +
                "AS leTitre " +
                "FROM film " +
                "INNER JOIN listeFilmGenre " +
                "ON film.idFilm = listeFilmGenre.codeFilm " +
                "INNER JOIN genre " +
                "ON listeFilmGenre.numeroGenre = genre.idGenre " +
                "WHERE genre.idGenre = \"" + id + "\""+
                "ORDER BY nom asc";
        // on instancie le curseur
        Cursor c = maBase.rawQuery(req, null);
        while (c.moveToNext()) {
            // on ajoute les valeurs dans la liste
            listeSQL.add(c.getString(0));
        }
        // on retourne la liste
        return listeSQL;
    }

    public void ajouterFilmGenre(FilmGenre leFilmGenre) {
        // on instancie le ContentValues
        ContentValues val = new ContentValues();
        //on ajoute les valeurs dans le ContentValues
        val.put("codeFilm", leFilmGenre.getIdFilm());
        val.put("numeroGenre", leFilmGenre.getIdGenre());
        //la fonction pour ajouter une ligne dans la table listeFilmGenre
        maBase.insert("listeFilmGenre", null, val);
    }

    //méthode pour supprimer une ligne de la table genre
    public void supprimeFilmGenre(String idFilm, String idGenre) {
        // la requete  pour supprimer une ligne de la table genre
        maBase.execSQL("DELETE " +
                "FROM listeFilmGenre " +
                "WHERE codeFilm = \"" + idFilm + "\"" +
                "AND numeroGenre = \"" + idGenre +"\";");
    }

}

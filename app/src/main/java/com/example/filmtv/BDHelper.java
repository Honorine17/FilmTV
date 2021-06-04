package com.example.filmtv;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BDHelper  extends SQLiteOpenHelper {
    public BDHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // CREATION DES TABLES
        // requete pour créer table réalisateur
        String req =
                "CREATE TABLE realisateur (idRealisateur text not null," +
                        "nomRealisateur text not null,"+
                        "datenaissanceRealisateur date not null,"+
                        "nationaliteRealisateur text not null,"+
                        "PRIMARY KEY (idRealisateur))";
        // execution de la requete
        db.execSQL(req);

        // requete pour créer table genre
        String req1 =
                "CREATE TABLE genre (idGenre text not null," +
                        "nom text not null,"+
                        "description text not null,"+
                        "PRIMARY KEY (idGenre))";
        // execution de la requete
        db.execSQL(req1);

        // requete pour créer table film
        String req2 =
                "CREATE TABLE film (idFilm text not null," +
                        "titreFilm text not null,"+
                        "anneeFilm date not null,"+
                        "descriptionFilm text not null,"+
                        "paysFilm text not null," +
                        "idRealisateur text not null,"+
                        "PRIMARY KEY (idFilm),"+
                        "FOREIGN KEY (idRealisateur) REFERENCES realisateur (idRealisateur))";
        // execution de la requete
        db.execSQL(req2);
        // requete pour créer table listefilmgenre
        String req3 =
                "CREATE TABLE listeFilmGenre (codeFilm text not null," +
                        "numeroGenre text not null," +
                        "PRIMARY KEY (codeFilm, numeroGenre),"+
                        "FOREIGN KEY (codeFilm) REFERENCES film (idFilm),"+
                        "FOREIGN KEY (numeroGenre) REFERENCES genre (idGenre))";
        // execution de la requete
        db.execSQL(req3);

        // CREATION DES TRIGGER

        // requete du trigger avant la suppression d'une ligne de réalisateur
        String req4 =
                "CREATE TRIGGER tr_delete_realisateur "+
                        "BEFORE DELETE "+
                        "ON realisateur "+
                        "FOR EACH ROW "+
                        "BEGIN "+
                        "DELETE "+
                        "FROM film "+
                        "WHERE idRealisateur = old.idRealisateur; "+
                        "END;";
        // exectution de la requete
        db.execSQL(req4);

        // requete du trigger apres la modification d'une ligne de réalisateur
        String req5 =
                "CREATE TRIGGER tr_update_realisateur "+
                        "AFTER UPDATE "+
                        "ON realisateur "+
                        "FOR EACH ROW "+
                        "BEGIN "+
                        "UPDATE film "+
                        "SET idRealisateur = new.idRealisateur "+
                        "WHERE idRealisateur = old.idRealisateur; "+
                        "END;";
        // execution de la requete
        db.execSQL(req5);

        //requete du trigger avant la suppression d'une ligne de film
        String req6 =
                "CREATE TRIGGER tr_delete_film "+
                        "BEFORE DELETE "+
                        "ON film "+
                        "FOR EACH ROW "+
                        "BEGIN "+
                        "DELETE "+
                        "FROM listeFilmGenre "+
                        "WHERE codeFilm = old.idFilm; " +
                        "END;";
        // execution de la requete
        db.execSQL(req6);

        //requete du trigger apres la modification d'une ligne de film

        String req7 =
                "CREATE TRIGGER tr_update_film "+
                        "AFTER UPDATE "+
                        "ON film "+
                        "FOR EACH ROW "+
                        "BEGIN "+
                        "UPDATE listeFilmGenre "+
                        "SET codeFilm = new.idFilm " +
                        "WHERE codeFilm = old.idFilm; "+
                        "END;";
        //execution de la requete
        db.execSQL(req7);

        //requete du trigger avant la suppression d'une ligne de genre

        String req8 =
                "CREATE TRIGGER tr_delete_genre "+
                        "BEFORE DELETE "+
                        "ON genre "+
                        "FOR EACH ROW "+
                        "BEGIN "+
                        "DELETE "+
                        "FROM listeFilmGenre "+
                        "WHERE numeroGenre = old.idGenre; "+
                        "END;";
        // execution de la requete
        db.execSQL(req8);

        // requete du trigger apres la modification d'une ligne de genre

        String req9 =
                "CREATE TRIGGER tr_update_genre "+
                        "AFTER UPDATE "+
                        "ON genre "+
                        "FOR EACH ROW "+
                        "BEGIN "+
                        "UPDATE listeFilmGenre "+
                        "SET numeroGenre = new.idGenre "+
                        "WHERE numeroGenre = old.idGenre; "+
                        "END;";
        // execution de la requete
        db.execSQL(req9);

        //requete du trigger avant la suppression d'une ligne de genre

        String req10 =
                "CREATE TRIGGER tr_delete_genre2 "+
                        "BEFORE DELETE "+
                        "ON genre "+
                        "FOR EACH ROW "+
                        "BEGIN "+
                        "DELETE "+
                        "FROM film " +
                        "WHERE idFilm IN (" +
                        "SELECT idFilm " +
                        "FROM film " +
                        "INNER JOIN listeFilmGenre " +
                        "ON film.idFilm = listeFilmGenre.codeFilm " +
                        "WHERE listeFilmGenre.numeroGenre = old.idGenre); "+
                        "END;";
        // execution de la requete
        db.execSQL(req10);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

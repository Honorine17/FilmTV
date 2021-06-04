package com.example.filmtv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Filmvue extends AppCompatActivity implements AdapterView.OnItemClickListener{

    // instanciation de la liste de film vie le json
    ArrayList<String> lesFilmsViaJson = new ArrayList<String>();
    // instanciation de la liste de genre via le json
    ArrayList<String> lesGenresViaJson = new ArrayList<String>();
    // instanciation de la liste de realisateur vie le json
    ArrayList<String> lesRealisateurViaJson = new ArrayList<String>();
    // instanciation de la liste de filmGenre vie le json
    ArrayList<String> lesFilmsGenresViaJson = new ArrayList<String>();
    // instanciation de la liste de films
    ArrayList<Film> lesFilms = new ArrayList<Film>();
    // instanciation de la liste de genre
    ArrayList<Genre> lesGenres = new ArrayList<Genre>();
    // instanciation de la liste de réalisateur
    ArrayList<Realisateur> lesRealisateurs = new ArrayList<Realisateur>();
    // instanciation de la liste de filmgenres
    ArrayList<FilmGenre> lesFilmGenres = new ArrayList<FilmGenre>();
    // instanciation de la liste de film via le SQL
    ArrayList<String> lesFilmsViaSQL = new ArrayList<String>();
    // instanciation du JSONObjet
    JSONObject jObj = null;
    // creation de la variable sgbd
    GestionBD sgbd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // instanciation de la sgbd
        sgbd = new GestionBD(this);
        // ouverture de la sgbd
        sgbd.open();
        // affichage de l'activité
        setContentView(R.layout.activity_film);
        // Permet de récupérer la liste écrite en dur et de la passer dans la listeView (dans la vue)
        ListView lvfilm = findViewById(R.id.listfilm);

        // lecture du fichier local
        String ficFilm =  lectureFichierLocal();
        // test fichier local
        Log.i("Recup fichier JSON","Étape 1 : Le fichier contient : " + ficFilm);
        JSONObject jsonFilms = parse(ficFilm);
        recFilms(jsonFilms);

        String ficRealisateur =  lectureFichierLocalReal();
        Log.i("Recup fichier JSON","Étape 1 : Le fichier contient : " + ficRealisateur);
        JSONObject jsonRealisateurs = parse(ficRealisateur);
        recReal(jsonRealisateurs);

        // lecture du fichier local
        String ficGenre =  lectureFichierLocalGenre();
        // test fichier local
        Log.i("Recup fichier JSON","Étape 1 : Le fichier contient : " + ficGenre);
        JSONObject jsonGenres = parse(ficGenre);
        recGenres(jsonGenres);

        // lecture du fichier local
        String ficFilmGenre =  lectureFichierLocalFilmGenre();
        // test fichier local
        Log.i("Recup fichier JSON","Étape 1 : Le fichier contient : " + ficFilmGenre);
        JSONObject jsonFilmGenre = parse(ficFilmGenre);
        recFilmsGenre(jsonFilmGenre);




        // ajout des libelle dans la liste
        lesFilmsViaSQL = sgbd.donnelesFilms();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,lesFilmsViaSQL);

        lvfilm.setAdapter(adapter);

        lvfilm.setOnItemClickListener(this);

    }

    // menu de mon application
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id==R.id.actionfilm){
            Intent pagefilm = new Intent(this, Filmvue.class);
            startActivity(pagefilm);
            finish();
        }
        if(id==R.id.actionrealisateur){
            Intent pagerealisateur = new Intent(this, Realisateurvue.class);
            startActivity(pagerealisateur);
            finish();
        }
        if(id==R.id.actiongenre){
            Intent pagegenre = new Intent(this, Genrevue.class);
            startActivity(pagegenre);
            finish();
        }

        if (id == R.id.actioncreerfilm){
            Intent pagecreer = new Intent (this, CreerFilmvue.class);

            startActivity(pagecreer);
            finish();
        }
        if (id == R.id.actioncreerreal){
            Intent pagecreerreal = new Intent (this, CreerRealisateurvue.class);
            startActivity(pagecreerreal);
            finish();
        }
        if (id == R.id.actioncreergenre){
            Intent pagecreergenre = new Intent (this, CreerGenrevue.class);
            startActivity(pagecreergenre);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void recFilms(JSONObject jsonFilms) {
        JSONArray lesFilm = null;
        //sgbd.videLaTableFilm();
        try {
            lesFilm = jsonFilms.getJSONArray("films");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < lesFilm.length(); i++) {
            JSONObject pJson = null;
            String lid, leTitre, laDescription, lAnnee, lePays, lIdreal, lIdgenre;
            Film leFilm;

            try {
                pJson = lesFilm.getJSONObject(i);
                // recuperation des données dans le json
                lid = pJson.getString("id");
                leTitre = pJson.getString("titre");
                laDescription = pJson.getString("description");
                lAnnee = pJson.getString("annee");
                lePays =pJson.getString("pays");
                lIdreal=pJson.getString("idreal");
                lIdgenre=pJson.getString("idgenre");
                // test dans le logcat pour verifier la recuperation des données
                Log.i("doInBack","Test lid vue produit : " + lid);
                Log.i("doInBack","Test leTitre vue produit : " + leTitre);
                Log.i("doInBack","Test lAnnee vue produit : " + lAnnee);
                Log.i("doInBack","Test lAnnee vue produit : " + lePays);
                Log.i("doInBack","Test laDescription vue produit : " + laDescription);
                Log.i("doInBack","Test lIdreal vue produit : " + lIdreal);
                Log.i("doInBack","Test lIdgenre vue produit : " + lIdgenre);

                // instanciation du produit
                leFilm = new Film(lid,leTitre,laDescription,lAnnee,lePays, lIdreal, lIdgenre);
                // ajoutr du libelle dans la liste
                lesFilmsViaJson.add(leTitre);
                // ajout du produit dans la liste
                lesFilms.add(leFilm);
                // methode pour ajouter une ligne dans la sgbd
                sgbd.ajouteFilm(leFilm);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void recReal(JSONObject jsonRealisateurs) {
        JSONArray lesRealisateur = null;
        try {
            lesRealisateur = jsonRealisateurs.getJSONArray("realisateurs");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for(int i=0; i<lesRealisateur.length();i++){
            JSONObject pJson = null;
            String lid;
            String lenom;
            String leprenom;
            String ladatenaissance;
            String lanationalite;
            Realisateur leRealisateur;
            try {
                pJson = lesRealisateur.getJSONObject(i);
                lid=pJson.getString("id");
                lenom = pJson.getString("nom");
                ladatenaissance = pJson.getString("datenaissance");
                lanationalite =pJson.getString("nationalite");

                leRealisateur = new Realisateur(lid,lenom, ladatenaissance, lanationalite);

                lesRealisateurViaJson.add(lenom);
                lesRealisateurs.add(leRealisateur);
                // methode pour ajouter une ligne dans la sgbd
                sgbd.ajouterRealisateur(leRealisateur);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void recFilmsGenre(JSONObject jsonFilmGenre) {
        JSONArray lesFilmGenre = null;
        try {
            lesFilmGenre = jsonFilmGenre.getJSONArray("filmgenres");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for(int i=0; i<lesFilmGenre.length();i++){
            JSONObject pJson = null;
            String lidFilm;
            String lidGenre;
            FilmGenre leFilmGenre;
            try {
                pJson = lesFilmGenre.getJSONObject(i);
                lidFilm=pJson.getString("idFilm");
                lidGenre = pJson.getString("idGenre");
                String leNomFilm = sgbd.getTitreFilm(lidFilm);
                leFilmGenre = new FilmGenre(lidFilm,lidGenre);

                lesFilmsGenresViaJson.add(leNomFilm);
                lesFilmGenres.add(leFilmGenre);
                // methode pour ajouter une ligne dans la sgbd
                sgbd.ajouterFilmGenre(leFilmGenre);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void recGenres(JSONObject jsonGenre) {
        JSONArray lesGenre = null;
        //sgbd.vidertableGenre();
        try {
            lesGenre = jsonGenre.getJSONArray("genres");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for(int i=0; i<lesGenre.length();i++){
            JSONObject pJson = null;
            String lId;
            String leNom;
            String laDescription;
            Genre leGenre;
            try {
                pJson = lesGenre.getJSONObject(i);
                lId = pJson.getString("id");
                laDescription = pJson.getString("description");
                leNom = pJson.getString("nom");


                leGenre = new Genre(lId,leNom,laDescription);

                lesGenresViaJson.add(leNom);
                lesGenres.add(leGenre);
                // methode pour ajouter une ligne dans la sgbd
                sgbd.ajouteGenre(leGenre);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private JSONObject parse(String chaineJson) {
        JSONObject jObj = null;
        try {
            jObj = new JSONObject(chaineJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jObj;

    }

    private String lectureFichierLocal() {
        String ligne = null;
        InputStream is = getResources().openRawResource(R.raw.films);
        StringBuilder builder = new StringBuilder();
        // On stocke temporairement ce qui est lu dans le fichier
        BufferedReader bfr = null;
        try {
            bfr = new BufferedReader(new InputStreamReader(is,"UTF-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        while (true){
            try {
                if(!((ligne=bfr.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            builder.append(ligne);
        }
        return builder.toString();
    }

    private String lectureFichierLocalGenre() {
        String ligne = null;
        InputStream is = getResources().openRawResource(R.raw.genres);
        StringBuilder builder = new StringBuilder();
        // On stocke temporairement ce qui est lu dans le fichier
        BufferedReader bfr = null;
        try {
            bfr = new BufferedReader(new InputStreamReader(is,"UTF-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        while (true){
            try {
                if(!((ligne=bfr.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            builder.append(ligne);
        }
        return builder.toString();
    }

    private String lectureFichierLocalFilmGenre() {
        String ligne = null;
        InputStream is = getResources().openRawResource(R.raw.filmgenres);
        StringBuilder builder = new StringBuilder();
        // On stocke temporairement ce qui est lu dans le fichier
        BufferedReader bfr = null;
        try {
            bfr = new BufferedReader(new InputStreamReader(is,"UTF-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        while (true){
            try {
                if(!((ligne=bfr.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            builder.append(ligne);
        }
        return builder.toString();
    }

    private String lectureFichierLocalReal() {
        String ligne = null;
        InputStream is = getResources().openRawResource(R.raw.realisateurs);
        StringBuilder builder = new StringBuilder();
        // on stocke temporairement ce qui est lu dans le fichier
        BufferedReader bfr = null;
        try {
            bfr = new BufferedReader(new InputStreamReader(is,"UTF-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        while (true){
            try {
                if(!((ligne=bfr.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            builder.append(ligne);
        }
        return builder.toString();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // on récupère du text via l'IHM
        String leNomClique = parent.getAdapter().getItem(position).toString();
        Log.i("Recup fichier JSON","Étape 1 : Le fichier contient : " + lesFilms);

        // recup du text via la structure de données
        String leTitre = lesFilmsViaSQL.get(position);
        Log.i("nom JSON","Étape 1 : Le nom contient : " + leTitre);
        // recup de l'id
        String lId = sgbd.getIDFilm(leTitre);
        // recup la description via l'arrayList de films
        String laDescription = sgbd.getDescriptionFilm(leTitre);
        // recup l'année via l'arrayList de films
        String laannee = sgbd.getAnneeFilm(leTitre);
        // recup le pays via l'arrayList de films
        String lepays = sgbd.getPaysFilm(leTitre);
        // recup l'id réalisateur via l'arrayList de films
        String lidreal = sgbd.getIdRealFilm(leTitre);
        // recup l'id genre via l'arrayList de films
        String lidgenre = sgbd.getIdGenreFilm(leTitre);

        Intent affichePerso = new Intent(this, FilmActivity.class);
        affichePerso.putExtra("lId",lId);
        affichePerso.putExtra("leTitre",leTitre);
        affichePerso.putExtra("laDescription",laDescription);
        affichePerso.putExtra("lepays",lepays);
        affichePerso.putExtra("laannee",laannee);
        affichePerso.putExtra("lidreal",lidreal);
        affichePerso.putExtra("lidgenre",lidgenre);

        startActivity(affichePerso);
        finish();
    }

}







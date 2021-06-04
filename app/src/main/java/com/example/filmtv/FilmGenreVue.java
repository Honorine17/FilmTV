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

public class FilmGenreVue extends AppCompatActivity implements AdapterView.OnItemClickListener {

    // instanciation de la liste de filmGenre vie le json
    ArrayList<String> lesFilmsGenresViaJson = new ArrayList<String>();
    // instanciation de la liste de filmgenres
    ArrayList<FilmGenre> lesFilmGenres = new ArrayList<FilmGenre>();
    // instanciation de la liste de film via le SQL
    ArrayList<String> lesFilmGenresViaSQL = new ArrayList<String>();
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
        setContentView(R.layout.activity_film_genre_vue);
        // Permet de récupérer la liste écrite en dur et de la passer dans la listeView (dans la vue)
        ListView lvFilmGenre = findViewById(R.id.listFilmGenre);

        Bundle extras = getIntent().getExtras();
        String lid = extras.getString("lId");

        // lecture du fichier local
        String ficFilmGenre =  lectureFichierLocalFilmGenre();
        // test fichier local
        Log.i("Recup fichier JSON","Étape 1 : Le fichier contient : " + ficFilmGenre);
        JSONObject jsonFilmGenres = parse(ficFilmGenre);
        recFilmsGenre(jsonFilmGenres);

        // ajout des libelle dans la liste
        lesFilmGenresViaSQL = sgbd.donneLesFilmGenre(lid);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,lesFilmGenresViaSQL);

        lvFilmGenre.setAdapter(adapter);

        lvFilmGenre.setOnItemClickListener(this);
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

    private JSONObject parse(String chaineJson) {
        JSONObject jObj = null;
        try {
            jObj = new JSONObject(chaineJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jObj;

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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // on récupère du text via l'IHM
        String leNomClique = parent.getAdapter().getItem(position).toString();
        Log.i("Recup fichier JSON","Étape 1 : Le fichier contient : " + lesFilmGenres);

        // recup du text via la structure de données
        String leTitre = lesFilmGenresViaSQL.get(position);
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
package com.example.filmtv;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Genrevue extends AppCompatActivity implements AdapterView.OnItemClickListener {

    // les Genres présent dans ma list
    private ArrayList<String> lesGenresViaJson = new ArrayList<String>();
    private ArrayList<Genre> lesGenres = new ArrayList<Genre>();
    ArrayList<String> lesGenresViaSQL = new ArrayList<String>();
    Context context;
    JSONObject jObj = null;
    // creation de la variable sgbd
    GestionBD sgbd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);
        // instanciation de la sgbd
        sgbd = new GestionBD(this);
        // ouverture de la sgbd
        sgbd.open();
        // Permet de récupérer la liste écrite en dur et de la passer dans la listeView (dans la vue)
        ListView lvgenre = findViewById(R.id.listgenre);

        // ajout des libelle dans la liste
        lesGenresViaSQL = sgbd.donnelesFilms();
        String ficGenre =  lectureFichierLocal();
        Log.i("Recup fichier JSON","Étape 1 : Le fichier contient : " + ficGenre);
        JSONObject jsonGenres = parsePersonnages(ficGenre);
        recGenres(jsonGenres);
        lesGenresViaSQL = sgbd.donneLesGenre();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,lesGenresViaSQL);
        lvgenre.setAdapter(adapter);
        lvgenre.setOnItemClickListener(this);

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

    private JSONObject parseGenres(String chaineJson) {
        try {
            jObj = new JSONObject(chaineJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jObj;

    }

    private String lectureFichierLocal() {
        String ligne = null;
        InputStream is = getResources().openRawResource(R.raw.genres);
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
        Log.i("Recup fichier JSON","Étape 1 : Le fichier contient : " + lesGenres);

        // on récupère du text via la structure de données
        String leTitre = lesGenresViaSQL.get(position);
        Log.i("nom JSON","Étape 1 : Le nom contient : " + leTitre);
        // on récupère la description via l'arrayList de genres
        String laDescription = sgbd.getDescriptionGenre(leTitre);
        String lId = sgbd.getNumeroGenre(leTitre);



        Intent afficheGenre = new Intent(this, GenreActivity.class);
        afficheGenre.putExtra("lId",lId);
        afficheGenre.putExtra("leTitre",leTitre);
        afficheGenre.putExtra("laDescription",laDescription);

        startActivity(afficheGenre);

        finish();
    }

    private JSONObject parsePersonnages(String chaineJson) {
        JSONObject jObj = null;
        try {
            jObj = new JSONObject(chaineJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jObj;

    }




}





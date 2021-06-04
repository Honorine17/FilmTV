package com.example.filmtv;
import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Realisateurvue extends AppCompatActivity implements AdapterView.OnItemClickListener {

    // instanciation de la liste de realisateur vie le json
    ArrayList<String> lesRealisateurViaJson = new ArrayList<String>();
    // instanciation de la liste de réalisateur
    ArrayList<Realisateur> lesRealisateurs = new ArrayList<Realisateur>();
    // instanciation de la liste de film via le SQL
    ArrayList<String> lesRealisateurViaSQL = new ArrayList<String>();
    Context context;
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
        setContentView(R.layout.activity_realisateur);
        // Permet de récupérer la liste écrite en dur et de la passer dans la listeView (dans la vue)
        ListView lvrealisateur = findViewById(R.id.listrealisateur);
        String ficRealisateur =  lectureFichierLocal();
        Log.i("Recup fichier JSON","Étape 1 : Le fichier contient : " + ficRealisateur);
        JSONObject jsonRealisateurs = parsePersonnages(ficRealisateur);
        recReal(jsonRealisateurs);

        // ajout des libelle dans la liste
        lesRealisateurViaSQL = sgbd.donneLesRealisateur();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,lesRealisateurViaSQL);
        lvrealisateur.setAdapter(adapter);
        lvrealisateur.setOnItemClickListener(this);

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

    private JSONObject parse(String chaineJson) {
        try {
            jObj = new JSONObject(chaineJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jObj;

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
            String ladatenaissance;
            String lanationalite;
            Realisateur leRealisateur;
            try {
                pJson = lesRealisateur.getJSONObject(i);
                lid=pJson.getString("id");
                lenom = pJson.getString("nom");
                ladatenaissance = pJson.getString("datenaissance");
                lanationalite =pJson.getString("nationalite");
                // test dans le logcat pour verifier la recuperation des données
                Log.i("doInBack","Test lid vue realisateur : " + lid);
                Log.i("doInBack","Test leNom vue realisateur : " + lenom);
                Log.i("doInBack","Test lAnnee vue realisateur : " + ladatenaissance);
                Log.i("doInBack","Test laNationalite vue realisateur : " + lanationalite);
                // instanciation du realisateur
                leRealisateur = new Realisateur(lid,lenom, ladatenaissance, lanationalite);
                //ajout du libelle dans la liste
                lesRealisateurViaJson.add(lenom);
                //ajout du réalisateur dans la liste
                lesRealisateurs.add(leRealisateur);
                // methode pour ajouter une ligne dans la sgbd
                sgbd.ajouterRealisateur(leRealisateur);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private String lectureFichierLocal() {
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
        Log.i("Recup fichier JSON","Étape 1 : Le fichier contient : " + lesRealisateurs);

        // on récupère du text via la structure de données
        String leTitrereal = lesRealisateurViaSQL.get(position);
        Log.i("nom JSON","Étape 1 : Le nom contient : " + leTitrereal);
        // recup de l'id
        String lId = sgbd.getidRealRealisateur(leTitrereal);
        // recup du nom via l'arrayList de realisateurs
        String lenom = sgbd.getNomRealisateur(leTitrereal);
        // recup date de naissance via l'arrayList de realisateurs
        String ladatenaissance = sgbd.getDateNaissanceRealisateur(leTitrereal);
        // recup la nationalite via l'arrayList de realisateurs
        String lanationalite = sgbd.getNationaliteRealisateur(leTitrereal);

        Intent affichePerso = new Intent(this, RealisateurActivity.class);
        affichePerso.putExtra("lId",lId);
        affichePerso.putExtra("leTitrereal",leTitrereal);
        affichePerso.putExtra("lenom",lenom);
        affichePerso.putExtra("ladatenaissance",ladatenaissance);
        affichePerso.putExtra("lanationalite",lanationalite);

        startActivity(affichePerso);
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



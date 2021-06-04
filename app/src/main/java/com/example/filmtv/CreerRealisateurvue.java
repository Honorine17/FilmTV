package com.example.filmtv;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CreerRealisateurvue extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    GestionBD sgbd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creerreal);

        //on récupère l'id du button
        Button btnaccueil= this.findViewById(R.id.btnaccueil) ;
        Button btnAdd= this.findViewById(R.id.btnCreerRealisateur) ;
        //on ajoute le setOnClickListener pour povoir cliquer sur le bouton
        btnaccueil.setOnClickListener(this);
        //on ajoute le setOnClickListener pour povoir cliquer su le bouton
        btnaccueil.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        //instanciation de la sgbd
        sgbd = new GestionBD(this);
        //ouverture de la base de données
        sgbd.open();
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    // parametré le button pour retourner sur la list film
    @Override
    public void onClick(View v) {
        // on récupère l'id du button
        int id = v.getId();
        //si l'id correspond au bon button
        if(id == R.id.btnaccueil){
            // on récupère la page que l'on souhaite afficher
            Intent pageaccueil = new Intent (this, Filmvue.class);
            // on ferme la page actuel et on ouvre la page que l'on souhaite afficher
            startActivity(pageaccueil);
            // on ferme la page
            this.finish();
        }
        if(id == R.id.btnCreerRealisateur){
            //recuperation de la page a afficher
            Intent pageRealisateurAdd = new Intent(this, Realisateurvue.class);
            //recuperation des id des editText
            EditText edIdRealisateur = (EditText) findViewById(R.id.CreerIdRealisateur);
            EditText edNomRealisateur = (EditText) findViewById(R.id.CreerNomRealisateur);
            EditText edDatenaissanceRealisateur = (EditText) findViewById(R.id.CreerDatenaissanceRealisateur);
            EditText edNationaliteRealisateur = (EditText) findViewById(R.id.CreerNationaliteRealisateur);

            //recuperation des valeurs
            String idRealisateur = edIdRealisateur.getText().toString();
            String nomRealisateur = edNomRealisateur.getText().toString();
            String datenaissanceRealisateur= edDatenaissanceRealisateur.getText().toString();
            String nationaliteRealisateur = edNationaliteRealisateur.getText().toString();

            //creation de l'instance de film
            Realisateur leRealisateur = new Realisateur(idRealisateur,nomRealisateur,datenaissanceRealisateur,nationaliteRealisateur);
            //methode pour ajouter le film
            sgbd.ajouterRealisateur(leRealisateur);
            //fermeture de la sgbd
            sgbd.close();
            // affichage de la page
            startActivity(pageRealisateurAdd);
            //fermeture de la page
            this.finish();
        }
    }

}

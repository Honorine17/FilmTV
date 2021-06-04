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

public class CreerGenrevue extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    GestionBD sgbd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creergenre);
        //on récupère l'id du button
        Button btnaccueil= this.findViewById(R.id.btnaccueil) ;
        Button btnAdd = this.findViewById(R.id.btnCreerFilm);
        //on ajoute le setOnClickListener pour povoir cliquer sur le bouton
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

    // paramétré le bouton pour retourner sur la liste film
    @Override
    public void onClick(View v) {
        //on récupère l'id du button
        int id = v.getId();
        //si l'id correspond au bon button
        if(id == R.id.btnaccueil){
            // on récupère la page que l'on souhaite afficher
            Intent pageaccueil = new Intent (this, Filmvue.class);
            // on ferme la page actuel et on ouvre la page récupérer
            startActivity(pageaccueil);
            // on ferme la page
            this.finish();
        }
        if(id == R.id.btnCreerFilm){
            // on récupère la page que l'on souhaite afficher
            Intent pageaccueil = new Intent (this, Genrevue.class);
            //recup des id
            EditText txtId = (EditText) findViewById(R.id.CreerIdGenre);
            EditText txtNom = (EditText) findViewById(R.id.CreerNomGenre);
            EditText txtDescription = (EditText) findViewById(R.id.CreerDescriptionGenre);
            //recup valeur
            String lId = txtId.getText().toString();
            String leNom = txtNom.getText().toString();
            String laDescription = txtDescription.getText().toString();
            // instance genre
            Genre leGenre = new Genre(lId,leNom,laDescription);
            // ajout genre
            sgbd.ajouteGenre(leGenre);
            // fermeture sgbd
            sgbd.close();
            // on ferme la page actuel et on ouvre la page récupérer
            startActivity(pageaccueil);
            // on ferme la page
            this.finish();
        }
    }
}



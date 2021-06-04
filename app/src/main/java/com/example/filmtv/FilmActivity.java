package com.example.filmtv;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FilmActivity extends AppCompatActivity implements View.OnClickListener{

    GestionBD sgbd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sgbd = new GestionBD(this);
        sgbd.open();
        setContentView(R.layout.activity_filmdetails);
        Bundle extras = getIntent().getExtras();
        String lid = extras.getString("lId");
        String leTitre = extras.getString("leTitre");
        String laannee = extras.getString("laannee");
        String lePays = extras.getString("lepays");
        String laDescription = extras.getString("laDescription");
        String lidreal = extras.getString("lidreal");
        String lidgenre = extras.getString("lidgenre");


        //on affiche les données du json
        TextView tvId = findViewById(R.id.txtId);
        tvId.setText(lid);
        TextView tvTitre = findViewById(R.id.txtTitre);
        tvTitre.setText(leTitre);
        TextView tvDescription = findViewById(R.id.txtDescription);
        tvDescription.setText(laDescription);
        TextView tvAnnee = findViewById(R.id.txtAnnee);
        tvAnnee.setText(laannee);
        TextView tvIdreal = findViewById(R.id.txtIdreal);
        tvIdreal.setText(lidreal);
        TextView tvIdgenre = findViewById(R.id.txtIdgenre);
        tvIdgenre.setText(lidgenre);
        TextView tvPays = findViewById(R.id.txtDetailFilm);
        tvPays.setText(lePays);
        Button btnSupprFilm = findViewById(R.id.btnsuppfilm);
        btnSupprFilm.setOnClickListener(this);
        Button btnModifFilm = findViewById(R.id.btnmodifierfilm);
        btnModifFilm.setOnClickListener(this);



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
    public void onClick(View v) {
        int id= v.getId();
        if (id == R.id.btnsuppfilm){
            // recuperation de la page a afficher
            Intent pageFilm = new Intent(this, Filmvue.class);
            Bundle extras = getIntent().getExtras();
            String lid = extras.getString("lId");
            String leTitre = extras.getString("leTitre");
            sgbd.supprimeFilm(lid);
            sgbd.close();
            startActivity(pageFilm);
            this.finish();
            Toast.makeText(this, "Suppresion du film : " + leTitre, Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.btnmodifierfilm){
            Bundle extras = getIntent().getExtras();
            String lid = extras.getString("lId");
            String leTitre = extras.getString("leTitre");
            String laannee = extras.getString("laannee");
            String lePays = extras.getString("lepays");
            String laDescription = extras.getString("laDescription");
            String lidreal = extras.getString("lidreal");
            String lidgenre = extras.getString("lidgenre");
            // recuperation de la page a afficher
            Intent pageFilmModif = new Intent(this, ModifierFilmvue.class);
            // ajout des valeurs dans l'intent
            pageFilmModif.putExtra("lId",lid);
            pageFilmModif.putExtra("leTitre",leTitre);
            pageFilmModif.putExtra("laannee",laannee);
            pageFilmModif.putExtra("lepays",lePays);
            pageFilmModif.putExtra("laDescription",laDescription);
            pageFilmModif.putExtra("lidreal",lidreal);
            pageFilmModif.putExtra("lidgenre",lidgenre);
            // affichage de la page
            startActivity(pageFilmModif);
            // fermeture de la sgbd
            sgbd.close();
            // fermeture de la page en cours
            this.finish();
        }
    }

    }

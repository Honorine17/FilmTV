package com.example.filmtv;

import android.content.EntityIterator;
import android.content.Intent;
import android.media.MediaFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.DesignTool;

public class ModifierFilmvue extends AppCompatActivity implements View.OnClickListener {

    GestionBD sgbd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sgbd = new GestionBD(this);
        sgbd.open();
        setContentView(R.layout.activity_modiffilm);

        Bundle extras = getIntent().getExtras();
        String lid = extras.getString("lId");
        String leTitre = extras.getString("leTitre");
        String laannee = extras.getString("laannee");
        String lePays = extras.getString("lepays");
        String laDescription = extras.getString("laDescription");
        String lidreal = extras.getString("lidreal");
        String lidgenre = extras.getString("lidgenre");

        Button btnAnnuler = this.findViewById(R.id.btnaccueil);
        Button btnModif = this.findViewById(R.id.btnModifFilm);
        btnAnnuler.setOnClickListener(this);
        btnModif.setOnClickListener(this);

        EditText txtId = this.findViewById(R.id.txtIdModifFilm);
        EditText txtNom = this.findViewById(R.id.CreerIdfilm);
        EditText txtDescription = this.findViewById(R.id.CreerNomFilm);
        EditText txtAnnee = this.findViewById(R.id.CreerAnneeFilm);
        EditText txtPays = this.findViewById(R.id.CreerPaysFilm);
        EditText txtGenre = this.findViewById(R.id.CreerGenreFilm);
        EditText txtReal = this.findViewById(R.id.CreerIdrealFilm);

        txtId.setText(lid);
        txtNom.setText(leTitre);
        txtDescription.setText(laDescription);
        txtAnnee.setText(laannee);
        txtPays.setText(lePays);
        txtGenre.setText(sgbd.getNumeroGenre(lidgenre));
        txtReal.setText(sgbd.getidRealRealisateur(lidreal));
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
        int id=v.getId();
        if( id == R.id.btnaccueil ){
            Intent pageRetour = new Intent(this, Filmvue.class);
            startActivity(pageRetour);
            this.finish();
        }
        if(id == R.id.btnModifFilm){
            Intent pageRetour = new Intent(this, Filmvue.class);
            EditText txtId = this.findViewById(R.id.txtIdModifFilm);
            EditText txtNom = this.findViewById(R.id.CreerIdfilm);
            EditText txtDescription = this.findViewById(R.id.CreerNomFilm);
            EditText txtAnnee = this.findViewById(R.id.CreerAnneeFilm);
            EditText txtPays = this.findViewById(R.id.CreerPaysFilm);
            EditText txtGenre = this.findViewById(R.id.CreerGenreFilm);
            EditText txtReal = this.findViewById(R.id.CreerIdrealFilm);
            Bundle extras = getIntent().getExtras();
            String lidFilmOriginal = extras.getString("lId");
            String leLibelleGenreOriginal = extras.getString("lidgenre");
            String lidGenreOriginal = sgbd.getNumeroGenre(leLibelleGenreOriginal);
            String lId = txtId.getText().toString();
            String leNom = txtNom.getText().toString();
            String laDescription = txtDescription.getText().toString();
            String lAnnee = txtAnnee.getText().toString();
            String lePays = txtPays.getText().toString();
            String leGenre = txtGenre.getText().toString();
            String leReal = txtReal.getText().toString();
            Film leFilm = new Film(lId,leNom,laDescription,lAnnee,lePays,leReal,leGenre);
            FilmGenre leFilmGenre = new FilmGenre(lId,leGenre);
            sgbd.ModifFilm(leFilm,lidFilmOriginal);
            sgbd.supprimeFilmGenre(lidFilmOriginal,lidGenreOriginal);
            sgbd.ajouterFilmGenre(leFilmGenre);
            sgbd.close();
            startActivity(pageRetour);
            finish();
            Toast.makeText(getApplicationContext(),"Modification du film : " + leNom,Toast.LENGTH_SHORT).show();
        }
    }
}

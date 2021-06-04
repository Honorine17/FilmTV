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

public class GenreActivity extends AppCompatActivity implements View.OnClickListener {

    GestionBD sgbd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sgbd = new GestionBD(this);
        sgbd.open();
        setContentView(R.layout.activity_genredetails);
        Bundle extras = getIntent().getExtras();
        String lId = extras.getString("lId");
        String leTitre = extras.getString("leTitre");
        String laDescription = extras.getString("laDescription");

        TextView tvId = findViewById(R.id.txtIdgenreGenre);
        tvId.setText(lId);
        TextView tvNom = findViewById(R.id.txtNomgenreGenre);
        tvNom.setText(leTitre);
        TextView tvDescription = findViewById(R.id.txtDescription);
        tvDescription.setText(laDescription);

        Button btnSuppr = this.findViewById(R.id.btnsuppgenre);
        Button btnModif = this.findViewById(R.id.btnmodifiergenre);
        Button btnFilmGenre = this.findViewById(R.id.btnAfficheFilmGenre);
        btnModif.setOnClickListener(this);
        btnSuppr.setOnClickListener(this);
        btnFilmGenre.setOnClickListener(this);
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
        if (id == R.id.btnsuppgenre){
            // recuperation de la page a afficher
            Intent pageGenre = new Intent(this, Genrevue.class);
            Bundle extras = getIntent().getExtras();
            String lId = extras.getString("lId");
            String leTitre = extras.getString("leTitre");
            sgbd.supprimeGenre(lId);
            sgbd.close();
            startActivity(pageGenre);
            finish();
            Toast.makeText(this, "Suppresion du genre : " + leTitre, Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.btnmodifiergenre){
            Bundle extras = getIntent().getExtras();
            String lId = extras.getString("lId");
            String leTitre = extras.getString("leTitre");
            String laDescription = extras.getString("laDescription");
            // recuperation de la page a afficher
            Intent pageGenreModif = new Intent(this, ModifierGenrevue.class);
            pageGenreModif.putExtra("lId",lId);
            pageGenreModif.putExtra("leTitre",leTitre);
            pageGenreModif.putExtra("laDescription",laDescription);
            // affichage de la page
            startActivity(pageGenreModif);
            // fermeture de la sgbd
            sgbd.close();
            // fermeture de la page en cours
            finish();
        }
        if (id == R.id.btnAfficheFilmGenre){
            Bundle extras = getIntent().getExtras();
            String lId = extras.getString("lId");
            // recuperation de la page a afficher
            Intent pageFilmGenre = new Intent(this, FilmGenreVue.class);
            pageFilmGenre.putExtra("lId",lId);
            // affichage de la page
            startActivity(pageFilmGenre);
            // fermeture de la sgbd
            sgbd.close();
            // fermeture de la page en cours
            finish();
        }
    }
}

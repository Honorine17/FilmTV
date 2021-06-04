package com.example.filmtv;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RealisateurActivity extends AppCompatActivity implements View.OnClickListener {
    GestionBD sgbd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sgbd = new GestionBD(this);
        sgbd.open();
        setContentView(R.layout.activity_realdetails);
        Bundle extras = getIntent().getExtras();
        String lid = extras.getString("lId");
        String lenom = extras.getString("lenom");
        String ladatenaissance = extras.getString("ladatenaissance");
        String lanationalite = extras.getString("lanationalite");

        // on affiche les données du json
        TextView tvId = findViewById(R.id.AfficheIdRealisateur);
        tvId.setText(lid);
        Log.i("doInBack","Test lid activity realisateur : " + lid);
        TextView tvNom = findViewById(R.id.afficheNomRealisateur);
        tvNom.setText(lenom);
        TextView tvDate = findViewById(R.id.afficheDatenaissanceRealisateur);
        tvDate.setText(ladatenaissance);
        TextView tvNationalite = findViewById(R.id.afficheNationaliteRealisateur);
        tvNationalite.setText(lanationalite);
        Button btnsuppreal = findViewById(R.id.btnSuppRealisateur);
        btnsuppreal.setOnClickListener(this);
        Button btnModifRealisateur = findViewById(R.id.btnModifierRealisateur);
        btnModifRealisateur.setOnClickListener(this);

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
        if (id == R.id.btnSuppRealisateur){
            // recuperation de la page a afficher
            Intent pageRealisateur = new Intent(this, Realisateurvue.class);
            Bundle extras = getIntent().getExtras();
            String lid = extras.getString("lId");
            String leNom = extras.getString("leNom");
            sgbd.supprimeRealisateur(lid);
            sgbd.close();
            startActivity(pageRealisateur);
            this.finish();
            Toast.makeText(this, "Suppression du realisateur : " + leNom, Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.btnModifierRealisateur){
            Bundle extras = getIntent().getExtras();
            String lid = extras.getString("lId");
            String lenom = extras.getString("lenom");
            String ladatenaissance = extras.getString("ladatenaissance");
            String lanationalite = extras.getString("lanationalite");

            // recuperation de la page a afficher
            Intent pageRealisateurModif = new Intent(this, ModifierRealisateurvue.class);
            // ajout des valeurs dans l'intent
            pageRealisateurModif.putExtra("lId",lid);
            pageRealisateurModif.putExtra("leNom",lenom);
            pageRealisateurModif.putExtra("ladatenaissance",ladatenaissance);
            pageRealisateurModif.putExtra("lanationalite",lanationalite);

            // affichage de la page
            startActivity(pageRealisateurModif);
            // fermeture de la sgbd
            sgbd.close();
            // fermeture de la page en cours
            this.finish();
        }
    }
}
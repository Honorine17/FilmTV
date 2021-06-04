package com.example.filmtv;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ModifierGenrevue extends AppCompatActivity implements View.OnClickListener {

    GestionBD sgbd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifgenre);

        Bundle extras = getIntent().getExtras();
        String lId = extras.getString("lId");
        String leTitre = extras.getString("leTitre");
        String laDescription = extras.getString("laDescription");

        //on récupère l'id du button
        Button btnaccueil= this.findViewById(R.id.btnaccueil) ;
        Button btnAdd= this.findViewById(R.id.btnCreerFilm) ;
        //on ajoute le setOnClickListener pour povoir cliquer su le bouton
        btnaccueil.setOnClickListener(this);
        btnAdd.setOnClickListener(this);

        EditText txtId = this.findViewById(R.id.CreerIdGenre);
        EditText txtNom = this.findViewById(R.id.CreerNomGenre);
        EditText txtDescription = this.findViewById(R.id.CreerDescriptionGenre);

        txtId.setText(lId);
        txtNom.setText(leTitre);
        txtDescription.setText(laDescription);

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
    public void onClick(View v) {
        int id=v.getId();
        if( id == R.id.btnaccueil ){
            Intent pageRetour = new Intent(this, Genrevue.class);
            startActivity(pageRetour);
            this.finish();
        }
        if( id == R.id.btnCreerFilm ){
            Intent pageRetour = new Intent(this, Genrevue.class);

            EditText txtId = this.findViewById(R.id.CreerIdGenre);
            EditText txtNom = this.findViewById(R.id.CreerNomGenre);
            EditText txtDescription = this.findViewById(R.id.CreerDescriptionGenre);

            Bundle extras = getIntent().getExtras();
            String lidOriginal = extras.getString("lId");

            String lId = txtId.getText().toString();
            String leNom = txtNom.getText().toString();
            String laDescription = txtDescription.getText().toString();

            Genre leGenre = new Genre(lId,leNom,laDescription);

            sgbd.ModifGenre(leGenre,lidOriginal);
            sgbd.close();

            startActivity(pageRetour);
            Toast.makeText(getApplicationContext(),"Modification du genre : " + leNom,Toast.LENGTH_SHORT).show();

            finish();
        }
    }
}

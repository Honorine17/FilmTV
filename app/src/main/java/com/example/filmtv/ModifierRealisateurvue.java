package com.example.filmtv;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ModifierRealisateurvue extends AppCompatActivity implements View.OnClickListener {
    GestionBD sgbd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sgbd = new GestionBD(this);
        sgbd.open();
        setContentView(R.layout.activity_modifreal);

        Bundle extras = getIntent().getExtras();
        String lid = extras.getString("lId");
        String leNom = extras.getString("leNom");
        String ladatenaissance = extras.getString("ladatenaissance");
        String laNationalite = extras.getString("lanationalite");

        Button btnAnnuler = this.findViewById(R.id.btnaccueil);
        Button btnModif = this.findViewById(R.id.btnModifReal);
        btnAnnuler.setOnClickListener(this);
        btnModif.setOnClickListener(this);

        EditText txtId = this.findViewById(R.id.CreerIdRealisateur);
        EditText txtNom = this.findViewById(R.id.CreerNomRealisateur);
        Log.i("doInBack","Test laNom modifier realisateur : " + leNom);
        EditText txtDatenaissance = this.findViewById(R.id.CreerDatenaissanceRealisateur);
        EditText txtNationalite = this.findViewById(R.id.CreerNationaliteRealisateur);


        txtId.setText(lid);
        txtNom.setText(leNom);
        txtDatenaissance.setText(ladatenaissance);
        txtNationalite.setText(laNationalite);

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
        if(id == R.id.btnModifReal){
            Intent pageRetour = new Intent(this, Realisateurvue.class);
            EditText txtId = this.findViewById(R.id.CreerIdRealisateur);
            EditText txtNom = this.findViewById(R.id.CreerNomRealisateur);
            EditText txtDatenaissance = this.findViewById(R.id.CreerDatenaissanceRealisateur);
            EditText txtNationalite = this.findViewById(R.id.CreerNationaliteRealisateur);
            Bundle extras = getIntent().getExtras();
            String lIdRealisateurOriginal = extras.getString("lId");
            String lId = txtId.getText().toString();
            String leNom = txtNom.getText().toString();
            String laDatenaissance = txtDatenaissance.getText().toString();
            String laNationalite = txtNationalite.getText().toString();
            Realisateur leRealisateur = new Realisateur(lId,leNom,laDatenaissance,laNationalite);
            sgbd.ModifRealisateur(leRealisateur,lIdRealisateurOriginal);
            sgbd.close();
            startActivity(pageRetour);
            Toast.makeText(getApplicationContext(),"Modification du realisateur : " + leNom,Toast.LENGTH_SHORT).show();
        }
    }
}



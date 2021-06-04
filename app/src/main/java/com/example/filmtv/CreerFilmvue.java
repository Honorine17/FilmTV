package com.example.filmtv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
        import android.os.Bundle;
import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;


public class CreerFilmvue extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    GestionBD sgbd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creerfilm);
        //on récupère l'id du button
        Button btnaccueil= this.findViewById(R.id.btnaccueil) ;
        Button btnAdd= this.findViewById(R.id.btnCreerFilm) ;
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

    // paramétré le bouton pour retourner sur la liste film
    @Override
    public void onClick(View v) {
        //on récupère l'id du button
        int id = v.getId();
        //si l'id correspond au bon button
        if(id == R.id.btnaccueil){
            // on récupère la page à afficher
            Intent pageaccueil = new Intent (this, Filmvue.class);
            // on ferme la page actuel et on ouvre la page récupérer
            startActivity(pageaccueil);
            // on ferme la page
            this.finish();
        }
        if(id == R.id.btnCreerFilm){
            //recuperation de la page a afficher
            Intent pageFilmAdd = new Intent(this, Filmvue.class);
            //recuperation des id des editText
            EditText edIdFilm = (EditText) findViewById(R.id.CreerIdfilm);
            EditText edTitreFilm = (EditText) findViewById(R.id.CreerNomFilm);
            EditText edDescriptionFilm = (EditText) findViewById(R.id.CréerDescriptionfilm);
            EditText edDateFilm = (EditText) findViewById(R.id.CreerAnneeFilm);
            EditText edPaysFilm = (EditText) findViewById(R.id.CreerPaysFilm);
            EditText edGenreFilm = (EditText) findViewById(R.id.CreerGenreFilm);
            EditText edIdRealFilm = (EditText) findViewById(R.id.CreerIdrealFilm);
            //recuperation des valeurs
            String idFilm = edIdFilm.getText().toString();
            String titreFilm = edTitreFilm.getText().toString();
            String descriptionFilm = edDescriptionFilm.getText().toString();
            String dateFilm = edDateFilm.getText().toString();
            String paysFilm = edPaysFilm.getText().toString();
            String genreFilm = edGenreFilm.getText().toString();
            String idRealFilm = edIdRealFilm.getText().toString();
            //creation de l'instance de film
            Film leFilm = new Film(idFilm,titreFilm,descriptionFilm,dateFilm,paysFilm,idRealFilm,genreFilm);
            //creation de l'instance de filmGenre
            FilmGenre lefilmgenre = new FilmGenre(idFilm,genreFilm);
            //methode pour ajouter le film
            sgbd.ajouteFilm(leFilm);
            //methode pour ajouter le filmgenre
            sgbd.ajouterFilmGenre(lefilmgenre);
            //fermeture de la sgbd
            sgbd.close();
            // affichage de la page
            startActivity(pageFilmAdd);
            //fermeture de la page
            this.finish();
        }
    }
}
package com.jb.celisstore.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.jb.celisstore.R;
import com.jb.celisstore.model.Produit;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //CREATION DE VARIABLES
    TextView special, articles;

    RecyclerView recyclerViewHorizontal;
    HorizontalAdapter horizontalAdapter;
    public static List<Produit> produitARabais = new ArrayList<>();

    RecyclerView recyclerViewVertical;
    VerticalAdapter verticalAdapter;
    public static List<Produit> produits = new ArrayList<>();

    public static List<Integer> quantiteEnStock = new ArrayList<>();

    private static final String BASE_URL = "http://192.168.1.83/celisstore/getProduit.php";

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //REFERENCEMENT DES ELEMENTS
        special = findViewById(R.id.activity_main_header_special_discount);
        articles = findViewById(R.id.activity_main_header_articles);

        recyclerViewHorizontal = findViewById(R.id.recyclerViewHorizontal);
        recyclerViewVertical = findViewById(R.id.recyclerViewVertical);

        //IMPLEMENTATION MENU AVEC LES DIFFERENTES OPTIONS
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home : {
                        Toast.makeText(MainActivity.this, "Acceuil", Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawers();
                        break;
                    }
                    case R.id.login: {
                        Toast.makeText(MainActivity.this, "Connexion", Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawers();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.createAccount : {
                        Toast.makeText(MainActivity.this, "Creer compte", Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawers();
                        Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
                        startActivity(intent);
                        break;
                    }
                }
                return false;
            }
        });

        //DESIGN DE LA VUE HORIZONTALE
        LinearLayoutManager layoutManagerHorizontal = new LinearLayoutManager(
                MainActivity.this, LinearLayoutManager.HORIZONTAL, false
        );
        recyclerViewHorizontal.setLayoutManager(layoutManagerHorizontal);
        recyclerViewHorizontal.setItemAnimator(new DefaultItemAnimator());

        //DESIGN DE LA VUE VERTICALE
        LinearLayoutManager layoutManagerVertical = new LinearLayoutManager(
                MainActivity.this, LinearLayoutManager.VERTICAL, false
        );
        recyclerViewVertical.setLayoutManager(layoutManagerVertical);

        //APPEL DE METHODE POUR RECUPERER LES DONNEES ET LES AFFICHER
        getProduit();
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Rechercher produit...");


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                verticalAdapter.getFilter().filter(newText);

                //CACHE LE RECYCLER VIEW HORIZONTAL ET LES TITRES
                recyclerViewHorizontal.setVisibility(View.GONE);
                special.setVisibility(View.GONE);
                articles.setVisibility(View.GONE);

                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                //FAIRE APPARAITRE LE RECYCLER VIEW HORIZONTAL ET LES TITRES
                recyclerViewHorizontal.setVisibility(View.VISIBLE);
                special.setVisibility(View.VISIBLE);
                articles.setVisibility(View.VISIBLE);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    //
    private void getProduit(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray array = new JSONArray(response);
                    for(int i = 0; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);

                        String nom = object.getString("nom");
                        double prix = object.getDouble("prix");
                        double taxe = object.getDouble("taxe");
                        double discount = object.getDouble("discount");
                        int anneeDeSortie = object.getInt("anneeDeSortie");
                        String marque = object.getString("marque");
                        String gamme = object.getString("gamme");
                        String categorie = object.getString("categorie");
                        String image = object.getString("image");

                        Produit produit = new Produit(nom, prix, taxe, discount, anneeDeSortie, marque, gamme, categorie, image);
                        produits.add(produit);

                        if(produit.getDiscount() > 0){
                            produitARabais.add(produit);
                        }

                        quantiteEnStock.add(object.getInt("quantiteEnStock"));
                    }

                    //AFFICHAGE TITRE
                    special.setText("Deal Special");
                    articles.setText("Articles");
                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                }

                //INITIALISATION DE LA VUE HORIZONTALE
                horizontalAdapter = new HorizontalAdapter(produitARabais, MainActivity.this);
                recyclerViewHorizontal.setAdapter(horizontalAdapter);

                //INITIALISATION DE LA VUE VERTICALE
                verticalAdapter = new VerticalAdapter(produits, MainActivity.this);
                recyclerViewVertical.setAdapter(verticalAdapter);

                verticalAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        Volley.newRequestQueue(MainActivity.this).add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

}
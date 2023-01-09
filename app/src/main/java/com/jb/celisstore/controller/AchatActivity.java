package com.jb.celisstore.controller;

import androidx.appcompat.app.AppCompatActivity;

import com.jb.celisstore.R;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class AchatActivity extends AppCompatActivity {

    TextView nom, prix;
    int indexProduit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achat);

        //REFERENCEMENT DES ELEMENTS GRAPHIQUES
        nom = findViewById(R.id.activity_achat_nom_produit);
        prix = findViewById(R.id.activity_achat_prix_produit);

        //RECUPERATION DE L'INDEX DU PRODUIT
        Intent intent = getIntent();
        indexProduit = intent.getIntExtra("index", -1);

        //AFFICHAGE DU NOM ET DU PRIX
        nom.setText(MainActivity.produits.get(indexProduit).getNom());
        prix.setText(MainActivity.produits.get(indexProduit).getPrix() + "$");
    }
}
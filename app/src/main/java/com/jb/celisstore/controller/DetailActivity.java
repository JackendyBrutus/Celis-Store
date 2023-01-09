package com.jb.celisstore.controller;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.jb.celisstore.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

public class DetailActivity extends AppCompatActivity {

    TextView header, price, discount, description;
    ImageView image;
    Button acheter;

    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        header = findViewById(R.id.activity_detail_tv_header);
        price = findViewById(R.id.activity_detail_tv_price);
        discount = findViewById(R.id.activity_detail_tv_discount);
        description = findViewById(R.id.activity_detail_tv_description);
        image = findViewById(R.id.activity_detail_image_view_image);
        acheter = findViewById(R.id.activity_detail_btn_acheter);

        //RECUPERATION DE L'INDEX DU PRODUIT
        Intent intent = getIntent();

        if(intent.getIntExtra("position", -1) >= 0){
            if(intent.getIntExtra("liste", -1) == -5){
                index = MainActivity.produits.indexOf(MainActivity.produitARabais.get(intent.getIntExtra("position", -1)));
            }
            else{
                index = intent.getIntExtra("position", -1);
            }
        }

        //AFFICHAGE
        if(index >= 0){
            //AJOUT DES DONNÉES RELATIVES AU PRODUIT DANS LES DIFFERENTS COMPOSANTS
            header.setText(MainActivity.produits.get(index).getNom() + " - " + MainActivity.produits.get(index).getMarque() + " - " + MainActivity.produits.get(index).getAnneeDeSortie());
            Glide.with(getApplicationContext()).load(MainActivity.produits.get(index).getImage()).into(image);
            price.setText(MainActivity.produits.get(index).getPrix() + "$");

            if(MainActivity.produits.get(index).getDiscount() > 0){
                discount.setText(
                        "Obtenir jusqu'a " +
                        (new DecimalFormat("#.0").format(MainActivity.produits.get(index).getDiscount() * 100 / MainActivity.produits.get(index).getPrix())) +
                        "% de rabais, economiser jusqu'à " +
                        MainActivity.produits.get(index).getDiscount() +
                                "$"
                );
            }
            else{
                discount.setText("");
            }
        }

        //ACHETER
        acheter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //OUVRIR L'ACTIVITE ACHAT EN LUI PASSANT L'INDEX DU PRODUIT QUE LE CLIENT SOUHAITE ACHETER
                Intent intent = new Intent(getApplicationContext(), AchatActivity.class);
                intent.putExtra("index", index);
                startActivity(intent);
            }
        });
    }
}
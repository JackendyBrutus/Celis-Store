package com.jb.celisstore.controller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jb.celisstore.R;
import com.jb.celisstore.model.Produit;

import java.text.DecimalFormat;
import java.util.List;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.ViewHolder> {
    List<Produit> produitARabais;
    Context context;

    public HorizontalAdapter(List<Produit> produitARabais, Context context) {
        this.produitARabais = produitARabais;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //AFFICHAGE DES ELEMENTS DANS LA VUE
        holder.discount.setText(new DecimalFormat("#.0").format((produitARabais.get(position).getDiscount() * 100) / produitARabais.get(position).getPrix()) + "% rabais");
        Glide.with(context).load(produitARabais.get(position).getImage()).into(holder.imageProduitARabais);
        holder.nomProduitARabais.setText(produitARabais.get(position).getNom());

        //ACHAT D'UN PRODUIT
        holder.element.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //OUVRIR L'ACTIVITE DETAIL
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("position", produitARabais.indexOf(produitARabais.get(holder.getPosition())));
                intent.putExtra("liste", -5);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return produitARabais.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //CREATION DES VARIABLES DES ELEMENTS DE LA VUE
        TextView discount;
        ImageView imageProduitARabais;
        TextView nomProduitARabais;
        LinearLayout element;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //REFERENCEMENT DES ELEMENTS DE LA VUE
            discount = itemView.findViewById(R.id.horizontal_rv_textview_discount);
            imageProduitARabais = itemView.findViewById(R.id.horizontal_rv_imageview);
            nomProduitARabais = itemView.findViewById(R.id.horizontal_rv_textview_nom);
            element = itemView.findViewById(R.id.linear_layout_vue_horizontale);
        }
    }
}

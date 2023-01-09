package com.jb.celisstore.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jb.celisstore.R;
import com.jb.celisstore.model.Produit;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.MyViewHolder> implements Filterable {

    List<Produit> produitsListFull;
    Context context;

    List<Produit> produits;

    public VerticalAdapter(List<Produit> produits, Context context) {
        this.produitsListFull = produits;
        this.context = context;
        this.produits = new ArrayList<>(produitsListFull);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vertical_row_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //AFFICHAGE DES ELEMENTS DANS LA VUE
        Glide.with(context).load(produits.get(position).getImage()).into(holder.imageViewProduit);
        holder.nomProduit.setText(produits.get(position).getNom());
        holder.prixProduit.setText(String.valueOf(produits.get(position).getPrix()) + "$");
        holder.qteEnStock.setText("Plus que " + MainActivity.quantiteEnStock.get(position) + " en Stock");

        //ACHAT D'UN PRODUIT
        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //OUVRIR L'ACTIVITE DETAIL
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("position", produits.indexOf(produits.get(holder.getPosition())));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return produits.size();
    }

    @Override
    public Filter getFilter() {
        return produitFilter;
    }

    private final Filter produitFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Produit> filteredProductList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredProductList.addAll(produitsListFull);
            }
            else{
                //TO LOWER METHOD SHOULD BE ADDED IN THE LIGNE BELOW
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(Produit prod : produitsListFull){
                    //TO LOWER METHOD SHOULD BE ADDED IN THE LIGNE BELOW
                    if(prod.getNom().toLowerCase().contains(filterPattern)){
                        filteredProductList.add(prod);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredProductList;
            results.count = filteredProductList.size();
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            produits.clear();
            produits.addAll((ArrayList)results.values);
            notifyDataSetChanged();

        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewProduit;
        TextView nomProduit;
        TextView prixProduit;
        TextView qteEnStock;
        LinearLayout row;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewProduit = itemView.findViewById(R.id.horizontal_rv_imageview);
            nomProduit = itemView.findViewById(R.id.horizontal_rv_nom);
            prixProduit = itemView.findViewById(R.id.horizontal_rv_prix);
            qteEnStock = itemView.findViewById(R.id.horizontal_rv_stock);
            row = itemView.findViewById(R.id.vertical_row_item);
        }
    }

}

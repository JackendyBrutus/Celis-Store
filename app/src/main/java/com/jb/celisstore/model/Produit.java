package com.jb.celisstore.model;

public class Produit {
    private String nom;
    private double prix;
    private double taxe;
    private double discount;
    private int anneeDeSortie;
    private String marque;
    private String gamme;
    private String categorie;
    private String image;

    public Produit(String nom, double prix, double taxe, double discount, int anneeDeSortie, String marque, String gamme, String categorie, String image) {
        this.nom = nom;
        this.prix = prix;
        this.taxe = taxe;
        this.discount = discount;
        this.anneeDeSortie = anneeDeSortie;
        this.marque = marque;
        this.gamme = gamme;
        this.categorie = categorie;
        this.image = image;
    }

    public Produit(String nom, double discount, String image){
        this.nom = nom;
        this.discount = discount;
        this.image = image;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public double getTaxe() {
        return taxe;
    }

    public void setTaxe(double taxe) {
        this.taxe = taxe;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getAnneeDeSortie() {
        return anneeDeSortie;
    }

    public void setAnneeDeSortie(int anneeDeSortie) {
        this.anneeDeSortie = anneeDeSortie;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getGamme() {
        return gamme;
    }

    public void setGamme(String gamme) {
        this.gamme = gamme;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "nom='" + nom + '\'' +
                ", prix=" + prix +
                ", taxe=" + taxe +
                ", discount=" + discount +
                ", anneeDeSortie=" + anneeDeSortie +
                ", marque='" + marque + '\'' +
                ", gamme='" + gamme + '\'' +
                ", categorie='" + categorie + '\'' +
                ", image=" + image +
                '}';
    }
}

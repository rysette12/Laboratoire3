package com.example.maryse.laboratoire3;

public class Article {
    private String nom;
    private String saison;
    private String type;
    private String couleur ;
    private String categorie;
    private String image;
    private String date;

    public Article() {

    }

    public Article(String nom, String saison, String type, String couleur, String categorie, String image, String date) {
        setNom(nom);
        setSaison(saison);
        setType(type);
        setCouleur(couleur);
        setCategorie(categorie);
        setImage(image);
        setDate(date);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSaison() {
        return saison;
    }

    public void setSaison(String saison) {
        this.saison = saison;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

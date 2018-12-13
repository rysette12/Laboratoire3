package com.example.maryse.laboratoire3;

public class Article {
    private String type;
    private String couleur ;
    private String saison;
    private String categorie;
    private String nomArticle;
    private String DernierDatePortee;

    public String getType() {
        return type;
    }

    public String getCouleur() {
        return couleur;
    }

    public String getSaison() {
        return saison;
    }

    public String getCategorie() {
        return categorie;
    }

    public String getNomArticle() {
        return nomArticle;
    }

    public String getDernierDatePortee() {
        return DernierDatePortee;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public void setSaison(String saison) {
        this.saison = saison;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setNomArticle(String nomArticle) {
        this.nomArticle = nomArticle;
    }

    public void setDernierDatePortee(String dernierDatePortee) {
        DernierDatePortee = dernierDatePortee;
    }

    public Article(String type, String couleur, String saison, String categorie,
        String nomArticle, String dernierDatePortee) {
        this.type = type;
        this.couleur = couleur;
        this.saison = saison;
        this.categorie = categorie;
        this.nomArticle = nomArticle;
        DernierDatePortee = dernierDatePortee;
    }

    public Article() {

    }
}

package com.example.maryse.laboratoire3;

import java.util.Date;

public class Article {
    private String vetement;
    private String couleur ;
    private String saison;
    private String materiel;
    private String categorie;
    private String nomArticle;
    private Date dateAchat;
    private Date DernierDatePortee;

    public String getVetement() {
        return vetement;
    }

    public String getCouleur() {
        return couleur;
    }

    public String getSaison() {
        return saison;
    }

    public String getMateriel() {
        return materiel;
    }

    public String getCategorie() {
        return categorie;
    }

    public String getNomArticle() {
        return nomArticle;
    }

    public Date getDateAchat() {
        return dateAchat;
    }

    public Date getDernierDatePortee() {
        return DernierDatePortee;
    }

    public void setVetement(String vetement) {
        this.vetement = vetement;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public void setSaison(String saison) {
        this.saison = saison;
    }

    public void setMateriel(String materiel) {
        this.materiel = materiel;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setNomArticle(String nomArticle) {
        this.nomArticle = nomArticle;
    }

    public void setDateAchat(Date dateAchat) {
        this.dateAchat = dateAchat;
    }

    public void setDernierDatePortee(Date dernierDatePortee) {
        DernierDatePortee = dernierDatePortee;
    }

    public Article(String vetement, String couleur, String saison, String materiel, String categorie, String nomArticle, Date dateAchat, Date dernierDatePortee) {
        this.vetement = vetement;
        this.couleur = couleur;
        this.saison = saison;
        this.materiel = materiel;
        this.categorie = categorie;
        this.nomArticle = nomArticle;
        this.dateAchat = dateAchat;
        DernierDatePortee = dernierDatePortee;
    }

    public Article() {

    }
}

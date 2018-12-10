package com.example.maryse.laboratoire3;

public class Utilisateur {
        private String nom;
        private String prenom ;
        private String password;
        private String adresseCouriel;

        public Utilisateur() {
        }

        public Utilisateur(String nom, String prenom, String password, String adresseCouriel) {
            this.nom = nom;
            this.prenom = prenom;
            this.password = password;
            this.adresseCouriel = adresseCouriel;
        }

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public String getPrenom() {
            return prenom;
        }

        public void setPrenom(String prenom) {
            this.prenom = prenom;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAdresseCouriel() {
            return adresseCouriel;
        }

        public void setAdresseCouriel(String adresseCouriel) {
            this.adresseCouriel = adresseCouriel;
        }
    }


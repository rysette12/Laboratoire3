package com.example.maryse.laboratoire3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "vestiaire";
    public static final String COLONNE_ID = "_id";
    public static final String COLONNE_NOM = "nom";

    public static final String TABLE_ARTICLE = "articles";
    public static final String COLONNE_SAISON = "saison";
    public static final String COLONNE_TYPE = "type";
    public static final String COLONNE_COULEUR = "couleur";
    public static final String COLONNE_CATEGORIE = "categorie";
    public static final String COLONNE_IMAGE = "image";
    public static final String COLONNE_FAVORIS ="favoris";
    public static final String COLONNE_DATE = "derniereDate";

    public static final String TABLE_TYPE = "types";
    public static final String TABLE_COULEUR = "couleurs";
    public static final String COLONNE_VALEUR = "valeur";
    public static final String TABLE_CATEGORIE = "categories";

    public static final String TABLE_CART ="choix_utilisateur";
    public static final String COLONNE_NUMERO_ARTICLE ="numero_article";
    public int maxEnsemble;
    public static final String TABLE_ENSEMBLE ="choix_utilisateur";
    public static final String COLONNE_NUMERO_ENSEMBLE="numero_ensemble";

    public static final String TABLE_UTILISATEUR ="utilisateurs";
    public static final String COLONNE_PASSWORD = "password";
    public static final String COLONNE_PRENOM ="prenom";
    public static final String COLONNE_ADRESSE ="adresse_courriel" ;

    // requête de base permettant de sélectionner toutes les colonnes de TABLE_ARTICLE
    // avec jointures sur les tables TABLE_TYPE, TABLE_COULEUR et TABLE_CATEGORIE.
    // * Ajouter par concaténation une clause WHERE (etc) au besoin.
    private static final String SELECT_ALL =
        "SELECT "
                + TABLE_ARTICLE + "." + COLONNE_ID + ", "
                + TABLE_ARTICLE + "." + COLONNE_NOM + ", "
                + TABLE_ARTICLE + "." + COLONNE_SAISON + ", "
                + TABLE_TYPE + "." + COLONNE_TYPE + ", "
                + TABLE_COULEUR + "." + COLONNE_COULEUR + ", "
                + TABLE_COULEUR + "." + COLONNE_VALEUR + ", "
                + TABLE_CATEGORIE + "." + COLONNE_CATEGORIE + ", "
                + TABLE_ARTICLE + "." + COLONNE_IMAGE + ", "
                + TABLE_ARTICLE + "." + COLONNE_FAVORIS + ", "
                + TABLE_ARTICLE + "." + COLONNE_DATE
        + " FROM " + TABLE_ARTICLE + ", " + TABLE_TYPE + ", " + TABLE_COULEUR + ", " + TABLE_CATEGORIE
        + " WHERE " + TABLE_ARTICLE + "." + COLONNE_TYPE + " = " + TABLE_TYPE + "." + COLONNE_ID
            + " AND " + TABLE_ARTICLE + "." + COLONNE_COULEUR + " = " + TABLE_COULEUR + "." + COLONNE_ID
            + " AND " + TABLE_ARTICLE + "." + COLONNE_CATEGORIE + " = " + TABLE_CATEGORIE + "." + COLONNE_ID;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    @Override
    public void onCreate (SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_ARTICLE + " ("
                + COLONNE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLONNE_NOM + " INTEGER, "
                + COLONNE_SAISON + " TEXT, "
                + COLONNE_TYPE + " INTEGER REFERENCES " + TABLE_TYPE + "(" + COLONNE_ID + "), "
                + COLONNE_COULEUR + " INTEGER REFERENCES " + TABLE_COULEUR + "(" + COLONNE_ID + "), "
                + COLONNE_CATEGORIE + " INTEGER REFERENCES " + TABLE_CATEGORIE + "(" + COLONNE_ID + "), "
                + COLONNE_IMAGE + " TEXT, "
                + COLONNE_FAVORIS + " NUMERIC, "
                + COLONNE_DATE + " DATE)";
        db.execSQL(sql);

        sql  = "CREATE TABLE IF NOT EXISTS " + TABLE_TYPE + " ("
                + COLONNE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLONNE_TYPE +" TEXT)";
        db.execSQL(sql);

        sql = "CREATE TABLE IF NOT EXISTS " + TABLE_COULEUR + " ("
                + COLONNE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLONNE_COULEUR + " TEXT, "
                + COLONNE_VALEUR + " INTEGER)";
        db.execSQL(sql);

        sql = "CREATE TABLE IF NOT EXISTS " + TABLE_CATEGORIE + " ("
                + COLONNE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLONNE_CATEGORIE +" TEXT)";
        db.execSQL(sql);

        sql = "CREATE TABLE IF NOT EXISTS " + TABLE_ENSEMBLE + " ("
                + COLONNE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLONNE_NUMERO_ENSEMBLE +"INTEGER,"
                + COLONNE_NUMERO_ARTICLE + " INTEGER REFERENCES " + TABLE_ARTICLE + "(" + COLONNE_ID + "))";
        db.execSQL(sql);

        sql = "CREATE TABLE IF NOT EXISTS " + TABLE_UTILISATEUR + " ("
                + COLONNE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLONNE_NOM + " TEXT, "
                + COLONNE_PRENOM + " TEXT, "
                + COLONNE_PASSWORD + " TEXT, "
                + COLONNE_ADRESSE + " TEXT)";
        db.execSQL(sql);

        donnees(db);
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLE );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPE );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COULEUR );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIE );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UTILISATEUR );
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys = ON;");
    }

    // données de base de l'application //
    public void donnees (SQLiteDatabase db ) {
        ajouterType(db, "T-shirt");
        ajouterType(db, "Pantalon");
        ajouterType(db, "Robe");
        ajouterCouleur(db, "Rouge", (int) Color.RED);
        ajouterCouleur(db, "Jaune", (int) Color.YELLOW);
        ajouterCouleur(db, "Blanc", (int) Color.WHITE);
        ajouterCategorie(db, "Sports");
        ajouterCategorie(db, "Décontracté");
        ajouterCategorie(db, "Soiré");

        ContentValues values = new ContentValues();
        values.put(COLONNE_NOM, 1);
        values.put(COLONNE_SAISON, 1);
        values.put(COLONNE_TYPE, 1 );
        values.put(COLONNE_COULEUR, 1);
        values.put(COLONNE_CATEGORIE, 1);
        db.insert(TABLE_ARTICLE, null, values);
    }

    public void ajouterArticle (SQLiteDatabase db, Article article) {
        ContentValues values = new ContentValues();
        values.put(COLONNE_NOM, article.getNom());
        values.put(COLONNE_SAISON, article.getSaison());
        values.put(COLONNE_TYPE, idType(db, article.getType()) );
        values.put(COLONNE_COULEUR, idCouleur(db, article.getCouleur()));
        values.put(COLONNE_CATEGORIE, idCategorie(db, article.getCategorie()));
        values.put(COLONNE_IMAGE, article.getImage());
        values.put(COLONNE_DATE, article.getDate());
        db.insert(TABLE_ARTICLE, null, values);
    }

    public void modifierArticle(SQLiteDatabase db, Article article, int id){
        String strFilter = "_id=" + id;
        ContentValues values = new ContentValues();
        values.put(COLONNE_NOM, article.getNom());
        values.put(COLONNE_SAISON, article.getSaison());
        values.put(COLONNE_TYPE, idType(db, article.getType()) );
        values.put(COLONNE_COULEUR, idCouleur(db, article.getCouleur()));
        values.put(COLONNE_CATEGORIE, idCategorie(db, article.getCategorie()));
        values.put(COLONNE_IMAGE, article.getImage());
        values.put(COLONNE_DATE, article.getDate());
        db.update(TABLE_ARTICLE,values, strFilter, null);
    }

    public void supprimerArticle (SQLiteDatabase db, int id) {
        db.delete(TABLE_ARTICLE, COLONNE_ID + " = ?", new String[]{ Integer.toString(id) });
    }

    public void ajouterUtilisateur (SQLiteDatabase db, Utilisateur utilisateur) {
        ContentValues values = new ContentValues();
        values.put(COLONNE_NOM, utilisateur.getNom());
        values.put(COLONNE_PRENOM, utilisateur.getPrenom());
        values.put(COLONNE_PASSWORD, utilisateur.getPassword() );
        values.put(COLONNE_COULEUR, utilisateur.getAdresseCouriel());
        db.insert(TABLE_UTILISATEUR, null, values);
    }

    public void ajouterEnsemble (SQLiteDatabase db, Cursor article ) {
        maxEnsemble++;
        ContentValues values = new ContentValues();
        for (int a = 0 ;a< article.getCount();a++){
            values.put(COLONNE_NUMERO_ENSEMBLE, maxEnsemble);
            values.put(COLONNE_NUMERO_ARTICLE, article.getInt(article.getColumnIndexOrThrow(COLONNE_NUMERO_ARTICLE)));
            db.insert(TABLE_ENSEMBLE, null, values);
        }
    }

    public void supprimerEnsemble (SQLiteDatabase db, int ensemble ) {
        db.delete(TABLE_ENSEMBLE, COLONNE_NUMERO_ENSEMBLE + " = ?", new String[]{ Integer.toString(ensemble) });
    }

    public Cursor listerEnsemble (SQLiteDatabase db) {
        String sql = "SELECT "
                + TABLE_ARTICLE + "." + COLONNE_NOM + ", "
                + TABLE_ARTICLE + "." + COLONNE_SAISON + ", "
                + TABLE_TYPE + "." + COLONNE_TYPE + ", "
                + TABLE_COULEUR + "." + TABLE_COULEUR + ", "
                + TABLE_CATEGORIE + "." + COLONNE_CATEGORIE
                + TABLE_ENSEMBLE +  "." + COLONNE_NUMERO_ENSEMBLE
                + " FROM " + TABLE_ARTICLE + ", " + TABLE_ENSEMBLE
                + " WHERE " + TABLE_ARTICLE + "." + COLONNE_ID + " = " + TABLE_ENSEMBLE+ "." + COLONNE_NUMERO_ARTICLE;
        Cursor cursor = db.rawQuery(sql, null);
        return cursor ;
    }

    public void ajouterAuPanier(SQLiteDatabase db , String id ) {
        ContentValues values = new ContentValues();
        values.put(COLONNE_NUMERO_ARTICLE, id );
        db.insert(TABLE_CART, null, values);
    }

    public void retirerDuPanier (SQLiteDatabase db, String id) {
        db.delete(TABLE_CART, COLONNE_ID + " = ?", new String[]{ id });
    }

    public Cursor listerCart (SQLiteDatabase db) {
        String sql = "SELECT "
                        + TABLE_ARTICLE + "." + COLONNE_NOM + ", "
                        + TABLE_ARTICLE + "." + COLONNE_SAISON + ", "
                        + TABLE_TYPE + "." + COLONNE_TYPE + ", "
                        + TABLE_COULEUR + "." + TABLE_COULEUR + ", "
                        + TABLE_CATEGORIE + "." + COLONNE_CATEGORIE
                        + " FROM " + TABLE_ARTICLE + ", " + TABLE_CART
                        + " WHERE " + TABLE_ARTICLE + "." + COLONNE_ID + " = " + TABLE_CART+ "." + COLONNE_NUMERO_ARTICLE;
        Cursor cursor = db.rawQuery(sql, null);
        return cursor ;
    }

    // Verifier
    public Utilisateur Verifier(SQLiteDatabase db,Utilisateur utilisateur) {
        Cursor cursor = db.rawQuery(SELECT_ALL + " AND " + TABLE_UTILISATEUR + "." + COLONNE_ADRESSE+ " = ?",
                new String[]{ utilisateur.getAdresseCouriel() });
        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            Utilisateur utilisateur11 = new Utilisateur(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            if (utilisateur.getPassword().equalsIgnoreCase(utilisateur11.getPassword()))
                return utilisateur11;
        }
        return null;
    }

    // gestion des types, couleurs, catégories //
    public void ajouterType (SQLiteDatabase db, String type) {
        ContentValues values = new ContentValues();
        values.put(COLONNE_TYPE, type);
        db.insert(TABLE_TYPE, null, values);
    }

    public void ajouterCouleur (SQLiteDatabase db, String couleur, int valeur) {
        ContentValues values = new ContentValues();
        values.put(COLONNE_COULEUR, couleur);
        values.put(COLONNE_VALEUR, valeur);
        db.insert(TABLE_COULEUR, null, values);
    }

    public void ajouterCategorie (SQLiteDatabase db , String categorie) {
        ContentValues values = new ContentValues();
        values.put(COLONNE_CATEGORIE, categorie);
        db.insert(TABLE_CATEGORIE, null, values);
    }

    public ArrayList<String> tousLesTypes (SQLiteDatabase db) {
        ArrayList<String> liste = new ArrayList<String>();
        Cursor cursor = db.rawQuery("SELECT " + COLONNE_TYPE + " FROM " + TABLE_TYPE, null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            liste.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLONNE_TYPE)));
        }
        return liste;
    }

    public ArrayList<String> toutesLesCouleurs (SQLiteDatabase db) {
        ArrayList<String> liste = new ArrayList<String>();
        Cursor cursor = db.rawQuery("SELECT " + COLONNE_COULEUR + " FROM " + TABLE_COULEUR, null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            liste.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLONNE_COULEUR)));
        }
        return liste;
    }

    public ArrayList<Integer> toutesLesValeursCouleurs (SQLiteDatabase db) {
        ArrayList<Integer> liste = new ArrayList<Integer>();
        Cursor cursor = db.rawQuery("SELECT " + COLONNE_VALEUR + " FROM " + TABLE_COULEUR, null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            liste.add(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLONNE_VALEUR)));
        }
        return liste;
    }

    public ArrayList<String> toutesLesCategories (SQLiteDatabase db) {
        ArrayList<String> liste = new ArrayList<String>();
        Cursor cursor = db.rawQuery("SELECT " + COLONNE_CATEGORIE + " FROM " + TABLE_CATEGORIE, null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            liste.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLONNE_CATEGORIE)));
        }
        return liste;
    }

    public int idType (SQLiteDatabase db, String type) {
        Cursor cursor = db.rawQuery("SELECT " + COLONNE_ID + " FROM " + TABLE_TYPE + " WHERE " + COLONNE_TYPE + " = ?",
                new String[]{ type });
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex(COLONNE_ID));
    }

    public int idCouleur (SQLiteDatabase db, String couleur ) {
        Cursor cursor = db.rawQuery("SELECT " + COLONNE_ID + " FROM " + TABLE_COULEUR + " WHERE " + COLONNE_COULEUR + " = ?",
                new String[]{ couleur });
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex(COLONNE_ID));
    }

    public int idCategorie (SQLiteDatabase db, String categorie) {
        Cursor cursor = db.rawQuery("SELECT " + COLONNE_ID + " FROM " + TABLE_CATEGORIE + " WHERE " + COLONNE_CATEGORIE + " = ?",
                new String[]{ categorie });
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex(COLONNE_ID));
    }

    public boolean supprimerType (SQLiteDatabase db, String type) {
        boolean resultat;
        try {
            resultat = db.delete(TABLE_TYPE, COLONNE_TYPE + " = ?", new String[]{type}) > 0;
        } catch (SQLiteConstraintException e) {
            resultat = false;
        }
        return resultat;
    }

    public boolean supprimerCouleur (SQLiteDatabase db, String couleur ) {
        boolean resultat;
        try {
            resultat = db.delete(TABLE_COULEUR, COLONNE_COULEUR + " = ?", new String[]{couleur}) > 0;
        } catch (SQLiteConstraintException e) {
            resultat = false;
        }
        return resultat;
    }

    public boolean supprimerCategorie (SQLiteDatabase db, String categorie) {
        boolean resultat;
        try {
            resultat = db.delete(TABLE_CATEGORIE, COLONNE_CATEGORIE + " = ?", new String[]{categorie}) > 0;
        } catch (SQLiteConstraintException e) {
            resultat = false;
        }
        return resultat;
    }

    // lister article. par id unique ou selon des critères //
    public Cursor detailsArticle (SQLiteDatabase db, int id) {
        Cursor cursor = db.rawQuery(SELECT_ALL + " AND " + TABLE_ARTICLE + "." + COLONNE_ID + " = ?", new String[]{ Integer.toString(id) });
        return cursor;
    }

    public Cursor listerTout (SQLiteDatabase db) {
        Cursor cursor = db.rawQuery(SELECT_ALL, null);
        return cursor;
    }

    public Cursor listerParSaison (SQLiteDatabase db, String saison) {
        Cursor cursor = db.rawQuery(SELECT_ALL + " AND " + COLONNE_SAISON + " = ?", new String[]{ saison });
        return cursor;
    }

    public Cursor listerParType (SQLiteDatabase db, String type) {
        Cursor cursor = db.rawQuery(SELECT_ALL + " AND " + TABLE_TYPE + "." + COLONNE_TYPE + " = ?",
                new String[]{ type });
        return cursor;
    }

    public Cursor listerParCouleur (SQLiteDatabase db, String couleur) {
        Cursor cursor = db.rawQuery(SELECT_ALL + " AND " + TABLE_COULEUR + "." + COLONNE_COULEUR + " = ?",
                new String[]{ couleur });
        return cursor;
    }

    public Cursor listerParCategorie (SQLiteDatabase db, String categorie) {
        Cursor cursor = db.rawQuery(SELECT_ALL + " AND " + TABLE_CATEGORIE + "." + COLONNE_CATEGORIE + " = ?",
                new String[]{ categorie });
        return cursor;
    }

    public Cursor listerArticlesFavoris (SQLiteDatabase db) {
        Cursor cursor = db.rawQuery(SELECT_ALL + " AND " + TABLE_ARTICLE + "." + COLONNE_FAVORIS + " = ?",
                new String[]{ Integer.toString(1) });
        return cursor;
    }

    public Cursor afficherPlusRecent (SQLiteDatabase db) {
        return afficherPlusRecent(db, 20);
    }

    public Cursor afficherPlusRecent (SQLiteDatabase db, int limite) {
        Cursor cursor = db.rawQuery(SELECT_ALL + " ORDER BY " + TABLE_ARTICLE + "." + COLONNE_DATE + " DESC LIMIT " + limite, null);
        return cursor;
    }

    // gestion des favoris //
    public void ajouterAuxFavoris (SQLiteDatabase db, int id) {
        ContentValues values = new ContentValues();
        values.put(COLONNE_FAVORIS, 1);
        db.update(TABLE_ARTICLE, values, COLONNE_ID + " = ?", new String[]{ Integer.toString(id)} );
    }

    public void retirerDesFavoris (SQLiteDatabase db, int id) {
        ContentValues values = new ContentValues();
        values.put(COLONNE_FAVORIS, 0);
        db.update(TABLE_ARTICLE, values, COLONNE_ID + " = ?", new String[]{ Integer.toString(id) });
    }

}

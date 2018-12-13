package com.example.maryse.laboratoire3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "vestiaire_directory";
    public static final String COLONNE_ID = "_id";
    public static final String COLONNE_DESCRIPTION = "description";
    public static final String COLONNE_NAME = "_id";

    public static final String TABLE_ARTICLE = "articles";
    public static final String COLONNE_SAISON = "saison";
    public static final String COLONNE_TYPE = "type";
    public static final String COLONNE_COULEUR = "couleur";
    public static final String COLONNE_CATEGORIE = "categorie";
    public static final String COLONNE_FAVORIS ="favoris";
    public static final String COLONNE_DATE = "derniereDate";

    public static final String TABLE_TYPE = "types";
    public static final String TABLE_COULEUR = "couleurs";
    public static final String TABLE_CATEGORIE = "categories";

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
                + TABLE_ARTICLE + "." + COLONNE_DESCRIPTION + ", "
                + TABLE_ARTICLE + "." + COLONNE_SAISON + ", "
                + TABLE_TYPE + "." + COLONNE_TYPE + ", "
                + TABLE_COULEUR + "." + COLONNE_COULEUR + ", "
                + TABLE_CATEGORIE + "." + COLONNE_CATEGORIE
        + " FROM " + TABLE_ARTICLE + ", " + TABLE_TYPE + ", " + TABLE_COULEUR + ", " + TABLE_CATEGORIE
        + " WHERE " + TABLE_ARTICLE + "." + COLONNE_TYPE + " = " + TABLE_TYPE+ "." + COLONNE_TYPE + " AND "
            + TABLE_ARTICLE + "." + COLONNE_COULEUR + " = " + TABLE_COULEUR + "." + COLONNE_COULEUR + " AND "
            + TABLE_ARTICLE + "." + COLONNE_CATEGORIE + " = " + TABLE_CATEGORIE + "." + COLONNE_CATEGORIE;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    @Override
    public void onCreate (SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_ARTICLE + " ("
                + COLONNE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLONNE_DESCRIPTION + " INTEGER, "
                + COLONNE_SAISON + " TEXT, "
                + COLONNE_TYPE + " INTEGER REFERENCES " + TABLE_TYPE + "(" + COLONNE_ID + "), "
                + COLONNE_COULEUR + " INTEGER REFERENCES " + TABLE_COULEUR + "(" + COLONNE_ID + "), "
                + COLONNE_CATEGORIE + " INTEGER REFERENCES " + TABLE_CATEGORIE + "(" + COLONNE_ID + "), "
                + COLONNE_FAVORIS + " NUMERIC, "
                + COLONNE_DATE + " DATE)";
        db.execSQL(sql);

        sql  = "CREATE TABLE IF NOT EXISTS " + TABLE_TYPE + " ("
                + COLONNE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLONNE_TYPE +" TEXT)";
        db.execSQL(sql);

        sql = "CREATE TABLE IF NOT EXISTS " + TABLE_COULEUR + " ("
                + COLONNE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLONNE_COULEUR +" TEXT)";
        db.execSQL(sql);

        sql = "CREATE TABLE IF NOT EXISTS " + TABLE_CATEGORIE + " ("
                + COLONNE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLONNE_CATEGORIE +" TEXT)";
        db.execSQL(sql);

        sql = "CREATE TABLE IF NOT EXISTS " + TABLE_UTILISATEUR + " ("
                + COLONNE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLONNE_DESCRIPTION + " TEXT, "
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UTILISATEUR );
        onCreate(db);
    }

    // données de base de l'application //
    public void donnees (SQLiteDatabase db ) {
        ajouterType(db, "T-shirt");
        ajouterType(db, "Pantalon");
        ajouterType(db, "Robe");
        ajouterCouleur(db, "Rouge");
        ajouterCouleur(db, "Jaune");
        ajouterCouleur(db, "Blanc");
        ajouterCategorie(db, "Sports");
        ajouterCategorie(db, "Décontracté");
        ajouterCategorie(db, "Soiré");
    }

    // Ajouts //
    public void ajouterArticle (SQLiteDatabase db, Article article) {
        ContentValues values = new ContentValues();
        values.put(COLONNE_DESCRIPTION, article.getNomArticle());
        values.put(COLONNE_SAISON, article.getSaison());
        values.put(COLONNE_TYPE, idType(db, article.getType()) );
        values.put(COLONNE_COULEUR, idCouleur(db, article.getCouleur()));
        values.put(COLONNE_CATEGORIE, idCategorie(db, article.getCategorie()));
        db.insert(TABLE_ARTICLE, null, values);
    }

    public void ajouterType (SQLiteDatabase db, String type) {
        ContentValues values = new ContentValues();
        values.put(COLONNE_TYPE, type);
        db.insert(TABLE_TYPE, null, values);
    }

    public void ajouterCouleur (SQLiteDatabase db, String couleur) {
        ContentValues values = new ContentValues();
        values.put(COLONNE_COULEUR, couleur);
        db.insert(TABLE_COULEUR, null, values);
    }

    public void ajouterCategorie (SQLiteDatabase db , String categorie) {
        ContentValues values = new ContentValues();
        values.put(COLONNE_CATEGORIE, categorie);
        db.insert(TABLE_CATEGORIE, null, values);
    }

    //modifier
    public void modifierArticle(SQLiteDatabase db, Article article, int id){
        String strFilter = "_id=" + id;
        ContentValues values = new ContentValues();
        values.put(COLONNE_DESCRIPTION, article.getNomArticle());
        values.put(COLONNE_TYPE, article.getType());
        values.put(COLONNE_SAISON, article.getSaison());
        values.put(COLONNE_CATEGORIE, article.getCategorie());
        values.put(COLONNE_DATE, article.getDernierDatePortee());
        values.put(COLONNE_DATE, article.getDernierDatePortee());
        values.put(COLONNE_DATE, article.getDernierDatePortee());
        db.update(TABLE_ARTICLE,values, strFilter, null);
    }

    // lister les types, couleurs, catégories //
    public Cursor tousLesTypes (SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT " + COLONNE_TYPE + " FROM " + TABLE_TYPE, null);
        return cursor;
    }

    public Cursor toutesLesCouleurs (SQLiteDatabase db ) {
        Cursor cursor = db.rawQuery("SELECT " + COLONNE_COULEUR + " FROM " + TABLE_COULEUR, null);
        return cursor;
    }

    public Cursor toutesLesCategories(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT " + COLONNE_CATEGORIE + " FROM " + TABLE_CATEGORIE, null);
        return cursor;
    }

    public String idType (SQLiteDatabase db, String type) {
        Cursor cursor = db.rawQuery("SELECT " + COLONNE_ID + " FROM " + TABLE_CATEGORIE + " WHERE " + COLONNE_NAME + " = ? ",
                new String[]{type});
        return cursor.getString(cursor.getColumnIndex(COLONNE_ID));
    }

    public String idCouleur (SQLiteDatabase db, String couleur ) {
        Cursor cursor = db.rawQuery("SELECT " + COLONNE_ID + " FROM " + TABLE_TYPE + " WHERE " + COLONNE_NAME + " = ? ",
                new String[]{couleur});
        return cursor.getString(cursor.getColumnIndex(COLONNE_ID));
    }

    public String idCategorie (SQLiteDatabase db, String categorie) {
        Cursor cursor = db.rawQuery("SELECT " + COLONNE_ID + " FROM " + TABLE_CATEGORIE + " WHERE " + COLONNE_NAME + " = ? ",
                new String[]{categorie});
        return cursor.getString(cursor.getColumnIndex(COLONNE_ID));
    }


    // lister article. par id unique ou selon des critères //
    public Cursor detailsArticle (SQLiteDatabase db, int id) {
        Cursor cursor = db.rawQuery(SELECT_ALL + " AND " + COLONNE_ID + " = ?", new String[]{ Integer.toString(id) });
        return cursor ;
    }

    public Cursor listerTout (SQLiteDatabase db) {
        Cursor cursor = db.rawQuery(SELECT_ALL, null);
        return cursor ;
    }

    public Cursor listerParSaison (SQLiteDatabase db, String saison) {
        Cursor cursor = db.rawQuery(SELECT_ALL + " AND " + COLONNE_SAISON + " = ?", new String[]{ saison });
        return cursor ;
    }

    public Cursor listerParType (SQLiteDatabase db, String type) {
        Cursor cursor = db.rawQuery(SELECT_ALL + " AND " + TABLE_TYPE + "." + COLONNE_TYPE + " = ?",
                new String[]{ type });
        return cursor ;
    }

    public Cursor listerParCouleur (SQLiteDatabase db, String couleur) {
        Cursor cursor = db.rawQuery(SELECT_ALL + " AND " + TABLE_COULEUR + "." + COLONNE_COULEUR + " = ?",
                new String[]{ couleur });
        return cursor ;
    }

    public Cursor listerParCategorie (SQLiteDatabase db, String categorie) {
        Cursor cursor = db.rawQuery(SELECT_ALL + " AND " + TABLE_CATEGORIE + "." + COLONNE_CATEGORIE + " = ?",
                new String[]{ categorie });
        return cursor ;
    }

    public Cursor listerArticlesFavoris (SQLiteDatabase db) {
        Cursor cursor = db.rawQuery(SELECT_ALL + " AND " + COLONNE_FAVORIS + " = ?",
                new String[]{ Integer.toString(1) });
        return cursor ;
    }

    public Cursor afficherPlusRecent (SQLiteDatabase db) {
        return afficherPlusRecent(db, 20);
    }

    public Cursor afficherPlusRecent (SQLiteDatabase db, int limite) {
        Cursor cursor = db.rawQuery(SELECT_ALL + " ORDER BY " + COLONNE_DATE + " DESC LIMIT " + limite, null);
        return cursor ;
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




    /// À IMPLÉMENTER!!!!!!!!!!!!!!!!!!!!!!!!
    // AJOUTER LES NOUVELLES MÉTHODES ICI AVANT DE FINALISER

    // TODO: remplacer les utilisations par des appels à tousLesTypes, toutesLesCouleurs ou toutesLesCategories
    public List<String> listerValeursDistinctes(String nomtable) {
        List<String> liste= new ArrayList<String>();

        String selectQuery = "SELECT distinct * FROM " + nomtable;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                liste.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return liste;
    }

}

package com.example.maryse.laboratoire3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "vestiaire_directory";
    public static final String TABLE_ARTICLE = "article";
    public static final String COLONNE_ID = "_id";
    public static final String COLONNE_DESCRIPTION = "description";
    public static final String COLONNE_SAISON = "saison";
    public static final String COLONNE_TYPE = "type";
    public static final String COLONNE_COULEUR = "Couleur";
    public static final String COLONNE_CATEGORIE = "categorie";
    public static final String COLONNE_FAVORIS ="favoris";
    public static final String COLONNE_DATE ="derniereDate";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_ARTICLE + " (" +
                COLONNE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLONNE_TYPE + " TEXT, " +
                COLONNE_SAISON + " TEXT, " +
                COLONNE_COULEUR + " TEXT, " +
                MATERIEL_COLUMN+ " TEXT, " +
                COLONNE_CATEGORIE + " TEXT," +
                COLONNE_DESCRIPTION + " TEXT, " +
                COLONNE_FAVORIS + " NUMERIC," +
                "dateAchat DATE, " +
                "situation TEXT)";

        String sql2 = "CREATE TABLE IF NOT EXISTS " + COLONNE_SAISON +"(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nom TEXT)";
        String sql3 = "CREATE TABLE IF NOT EXISTS " + COLONNE_CATEGORIE + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nom TEXT)";

        String sql4 = "CREATE TABLE IF NOT EXISTS "+ COLONNE_TYPE + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "type TEXT)";

        db.execSQL(sql);
        db.execSQL(sql2);  db.execSQL(sql3);   db.execSQL(sql4);

        for (int i = 0; i < 10; i++ ){
            ContentValues values = new ContentValues();
            values.put(COLONNE_TYPE, "VETEMENT");
            if (i %2 == 0)
            values.put(COLONNE_COULEUR, "ÉTÉ");
            else
                values.put(COLONNE_COULEUR, "Hiver");
            values.put(MATERIEL_COLUMN, "MATERIEL");
            values.put(COLONNE_CATEGORIE, "CATEGORIE");
            values.put(COLONNE_DESCRIPTION, "NOM");
            values.put(COLONNE_FAVORIS, (i %2 == 0)?1:0);
            db.insert(TABLE_ARTICLE, null, values);
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS vestiaire");
        onCreate(db);
    }

    public void ajouterArticle(SQLiteDatabase db , Article article ) {
        ContentValues values = new ContentValues();
        values.put(COLONNE_TYPE, article.getVetement());
        values.put(COLONNE_SAISON,article.getCouleur());
        values.put(COLONNE_COULEUR, article.getSaison());
        values.put(MATERIEL_COLUMN, article.getMateriel());
        values.put(COLONNE_DESCRIPTION, article.getNomArticle());
        db.insert("vestiaires", null,values);
    }

    public  Cursor afficherdetails(SQLiteDatabase db, int id  )  {
        Cursor cursor = db.rawQuery("SELECT * FROM vestiaire as vest WHERE vest._id = ?",
                new String[]{""+id});
        return  cursor ;
    }
    //public Cursor listerTous(SQLiteDatabase db) {
    //Cursor cursor = db.rawQuery("SELECT * FROM vestiaire");
    // return  cursor ;
    // }

    public  Cursor afficherTous(SQLiteDatabase db)  {
        Cursor cursor = db.rawQuery("SELECT * FROM vestiaire as vest", null);
        return  cursor ;
    }

    public  Cursor afficherCouleur(SQLiteDatabase db, String couleur   )  {
        Cursor cursor = db.rawQuery("SELECT * FROM vestiaire as vest WHERE vest." + COLONNE_SAISON + " = ?",
                new String[]{""+couleur});
        return  cursor ;
    }

    public  Cursor afficherSaison(SQLiteDatabase db, String saison)  {
        Cursor cursor = db.rawQuery("SELECT * FROM vestiaire as vest WHERE vest." + COLONNE_COULEUR + " like ?",
                new String[]{ saison });
        return  cursor ;
    }

    public  Cursor afficherCategorie(SQLiteDatabase db, String categorie   )  {
        Cursor cursor = db.rawQuery("SELECT * FROM vestiaire as vest WHERE vest." + COLONNE_CATEGORIE + " = ?",
                new String[]{""+categorie});
        return  cursor ;
    }

    public  Cursor afficherType(SQLiteDatabase db, String type)  {
        Cursor cursor = db.rawQuery("SELECT * FROM vestiaire as vest WHERE vest." + COLONNE_TYPE + " = ?",
                new String[]{""+type});
        return  cursor ;
    }

    public  Cursor afficherFavoris(SQLiteDatabase db)  {
        Cursor cursor = db.rawQuery("SELECT * FROM vestiaire as vest WHERE vest." + COLONNE_FAVORIS + " = ?",
                new String[]{""+1});
        return  cursor ;
    }

    public  Cursor afficherPlusRecent(SQLiteDatabase db)  {
        Cursor cursor = db.rawQuery("SELECT * FROM vestiaire as vest WHERE vest." + COLONNE_FAVORIS + " = ?",
                new String[]{""+1});
        return  cursor ;
    }

    public  Cursor afficherPlusPorte(SQLiteDatabase db)  {
        Cursor cursor = db.rawQuery("SELECT * FROM vestiaire as vest WHERE vest." + COLONNE_FAVORIS + " = ?",
                new String[]{""+1});
        return  cursor ;
    }

    public void ajouterType(SQLiteDatabase db, String type) {}
    public void ajouterCouleur(SQLiteDatabase db, String type) {}
    public void ajouterCategorie(SQLiteDatabase db, String type) {}

    public void ajouterAuxFavoris(SQLiteDatabase db, int id) {
        ContentValues values = new ContentValues();
        values.put(COLONNE_FAVORIS, 1);
        db.update(TABLE_ARTICLE, values, COLONNE_ID + " = ?",
                new String[]{Integer.toString(id)});
    }

    public void retirerDesFavoris(SQLiteDatabase db, int id) {
        ContentValues values = new ContentValues();
        values.put(COLONNE_FAVORIS, 0);
        db.update(TABLE_ARTICLE, values, COLONNE_ID + " = ?",
                new String[]{Integer.toString(id)});
    }

}

package com.example.maryse.laboratoire3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "vestiaire_directory";
    public static final String VESTIAIRE_TABLE = "vestiaire";
    public static final String  VETEMENT_COLUMN = "vetement";
    public static final String  COULEUR_COLUMN = "saison";
    public static final String  SAISON_COLUMN = "Couleur";
    public static final String  MATERIEL_COLUMN = "materiel";
    public static final String CATEGORIE_COLUMN ="categorie";
    public static final String NOM_COLUMN ="nom";
    public static final String FAVORIS_COLUMN ="favoris";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + VESTIAIRE_TABLE + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                VETEMENT_COLUMN + " TEXT, " +
                COULEUR_COLUMN + " TEXT, " +
                SAISON_COLUMN+ " TEXT, " +
                MATERIEL_COLUMN+ " TEXT, " +
                CATEGORIE_COLUMN  + " TEXT," +
                NOM_COLUMN + " TEXT, " +
                FAVORIS_COLUMN + " NUMERIC," +
                "dateAchat DATE, " +
                "situation TEXT)";

        String sql2 = "CREATE TABLE IF NOT EXISTS " +COULEUR_COLUMN +"(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nom TEXT)";
        String sql3 = "CREATE TABLE IF NOT EXISTS " + CATEGORIE_COLUMN + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nom TEXT)";

        String sql4 = "CREATE TABLE IF NOT EXISTS "+ VETEMENT_COLUMN + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "type TEXT)";

        db.execSQL(sql);
        db.execSQL(sql2);  db.execSQL(sql3);   db.execSQL(sql4);

        for (int i = 0; i < 10; i++ ){
            ContentValues values = new ContentValues();
            values.put(VETEMENT_COLUMN, "VETEMENT");
            values.put(COULEUR_COLUMN, "COULEUR");
            if (i %2 == 0)
            values.put(SAISON_COLUMN, "ÉTÉ");
            else
                values.put(SAISON_COLUMN, "Hiver");
            values.put(MATERIEL_COLUMN, "MATERIEL");
            values.put(CATEGORIE_COLUMN, "CATEGORIE");
            values.put(NOM_COLUMN, "NOM");
            values.put(FAVORIS_COLUMN, (i %2 == 0)?1:0);
            db.insert(VESTIAIRE_TABLE, null, values);
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS vestiaire");
        onCreate(db);
    }

    public void ajouterArticle(SQLiteDatabase db , Article article ) {
        ContentValues values = new ContentValues();
        values.put(VETEMENT_COLUMN , article.getVetement());
        values.put(COULEUR_COLUMN ,article.getCouleur());
        values.put(SAISON_COLUMN, article.getSaison());
        values.put(MATERIEL_COLUMN, article.getMateriel());
        values.put(NOM_COLUMN, article.getNomArticle());
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
        Cursor cursor = db.rawQuery("SELECT * FROM vestiaire as vest WHERE vest." + COULEUR_COLUMN + " = ?",
                new String[]{""+couleur});
        return  cursor ;
    }

    public  Cursor afficherSaison(SQLiteDatabase db, String saison)  {
        Cursor cursor = db.rawQuery("SELECT * FROM vestiaire as vest WHERE vest." + SAISON_COLUMN + " like ?",
                new String[]{ saison });
        return  cursor ;
    }

    public  Cursor afficherCategorie(SQLiteDatabase db, String categorie   )  {
        Cursor cursor = db.rawQuery("SELECT * FROM vestiaire as vest WHERE vest." + CATEGORIE_COLUMN + " = ?",
                new String[]{""+categorie});
        return  cursor ;
    }

    public  Cursor afficherType(SQLiteDatabase db, String type)  {
        Cursor cursor = db.rawQuery("SELECT * FROM vestiaire as vest WHERE vest." + VETEMENT_COLUMN + " = ?",
                new String[]{""+type});
        return  cursor ;
    }

    public  Cursor afficherFavoris(SQLiteDatabase db)  {
        Cursor cursor = db.rawQuery("SELECT * FROM vestiaire as vest WHERE vest." + FAVORIS_COLUMN + " = ?",
                new String[]{""+1});
        return  cursor ;
    }

    public  Cursor afficherPlusRecent(SQLiteDatabase db)  {
        Cursor cursor = db.rawQuery("SELECT * FROM vestiaire as vest WHERE vest." + FAVORIS_COLUMN + " = ?",
                new String[]{""+1});
        return  cursor ;
    }

    public  Cursor afficherPlusPorte(SQLiteDatabase db)  {
        Cursor cursor = db.rawQuery("SELECT * FROM vestiaire as vest WHERE vest." + FAVORIS_COLUMN + " = ?",
                new String[]{""+1});
        return  cursor ;
    }

    public void ajouterType(SQLiteDatabase db, String type) {}
    public void ajouterCouleur(SQLiteDatabase db, String type) {}
    public void ajouterCategorie(SQLiteDatabase db, String type) {}

}

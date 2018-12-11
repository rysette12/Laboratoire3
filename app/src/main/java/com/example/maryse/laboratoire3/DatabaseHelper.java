package com.example.maryse.laboratoire3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "vestiaire_directory";
    public static final String COLONNE_ID = "_id";
    public static final String COLONNE_DESCRIPTION = "description";

    public static final String TABLE_ARTICLE = "articles";
    public static final String COLONNE_SAISON = "saison";
    public static final String COLONNE_TYPE = "type";
    public static final String COLONNE_COULEUR = "couleur";
    public static final String COLONNE_CATEGORIE = "categorie";
    public static final String COLONNE_FAVORIS ="favoris";
    public static final String COLONNE_DATE = "derniereDate";

    public static final String TABLE_TYPE = "types";
    public static final String TABLE_COULEUR = "couleurs";
    public static final String TABLE_CATEGORIE = "categorie";

    public static final String TABLE_UTILISATEUR ="utilisateurs";
    public static final String COLONNE_PASSWORD = "password";
    public static final String COLONNE_PRENOM ="prenom";
    public static final String COLONNE_ADRESSE ="adresse_courriel" ;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS vestiaire (" +
                COLONNE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLONNE_DESCRIPTION + " INTEGER, " +
                COLONNE_SAISON + " TEXT, " +
                COLONNE_TYPE + " INTEGER REFERENCES " + TABLE_TYPE + "(" + COLONNE_ID + "), " +
                COLONNE_COULEUR + " INTEGER REFERENCES " + TABLE_COULEUR + "(" + COLONNE_ID + "), " +
                COLONNE_CATEGORIE + " INTEGER REFERENCES " + TABLE_CATEGORIE + "(" + COLONNE_ID + "), " +
                COLONNE_FAVORIS + " NUMERIC, " +
                COLONNE_DATE + " DATE)";

        String sql2 = "CREATE TABLE IF NOT EXISTS " + TABLE_COULEUR +"(" +
                id_couleur_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLONNE_DESCRIPTION +" TEXT)";
        String sql3 = "CREATE TABLE IF NOT EXISTS " + TABLE_CATEGORIE + "(" +
                NUMERO_CATEGORIE +"INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLONNE_DESCRIPTION +"TEXT)";

        String sql4 = "CREATE TABLE IF NOT EXISTS "+ TABLE_ARTICLE + "(" +
                COLONNE_TYPE +"INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "type TEXT)";

        String sql5 = "CREATE TABLE IF NOT EXISTS "
                + TABLE_UTILISATEUR + "(" +" _id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COLONNE_DESCRIPTION +"TEXT,"+ COLONNE_PRENOM +"TEXT,"+ COLONNE_PASSWORD +"TEXT," + COLONNE_ADRESSE +"TEXT)";
        db.execSQL(sql);
        db.execSQL(sql2);  db.execSQL(sql3);   db.execSQL(sql4);
        db.execSQL(sql5);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS vestiaire");
        onCreate(db);
    }

    public void ajouterArticle(SQLiteDatabase db , Article article ) {
        String couleur ="";
        String categorie="";
        String typeVetement="";
        if((!isExist(article.getCouleur(),db,"couleur"))&&(!isExist(article.getCategorie(),db, TABLE_CATEGORIE)) ){
            db.execSQL("INSERT INTO couleur ( "+ COLONNE_DESCRIPTION +") VALUES('"+article.getCouleur()+"')");
            Cursor cursor = db.rawQuery("SELECT * FROM couleur as c  WHERE c.NOM_COLUMN = ?",new String[]{""+article.getCouleur()});
            couleur = cursor.getString(cursor.getColumnIndex( id_couleur_COLUMN));
            db.execSQL("INSERT INTO" + TABLE_CATEGORIE +"( "+ COLONNE_DESCRIPTION +") VALUES('"+article.getCategorie()+"')");
            Cursor cursor1 = db.rawQuery("SELECT * FROM "+ TABLE_CATEGORIE +" as c  WHERE c.NOM_COLUMN = ?",new String[]{""+article.getCategorie()});
            categorie = cursor1.getString(cursor1.getColumnIndex(COLONNE_TYPE));
        }
        if (!isExist(article.getVetement(),db, TABLE_ARTICLE)){
            db.execSQL("INSERT INTO" + TABLE_ARTICLE +"( "+ COLONNE_DESCRIPTION +") VALUES('"+article.getVetement()+"')");
            Cursor cursor1 = db.rawQuery("SELECT * FROM "+ TABLE_ARTICLE +" as c  WHERE c.NOM_COLUMN = ?",new String[]{""+article.getVetement()});
            typeVetement = cursor1.getString(cursor1.getColumnIndex(COLONNE_TYPE));
        }
        else{
            Cursor cursor = db.rawQuery("SELECT * FROM couleur as c  WHERE c.NOM_COLUMN = ?",new String[]{""+article.getCouleur()});
            couleur =cursor.getString(cursor.getColumnIndex( id_couleur_COLUMN));
            Cursor cursor1 = db.rawQuery("SELECT * FROM "+ TABLE_CATEGORIE +" as c  WHERE c.NOM_COLUMN = ?",new String[]{""+article.getCategorie()});
            categorie = cursor1.getString(cursor1.getColumnIndex( NUMERO_CATEGORIE));
            Cursor cursor2= db.rawQuery("SELECT * FROM "+ TABLE_ARTICLE +" as c  WHERE c.NOM_COLUMN = ?",new String[]{""+article.getVetement()});
            typeVetement = cursor1.getString(cursor1.getColumnIndex(COLONNE_TYPE));
        }
        ContentValues values = new ContentValues();
        values.put(COLONNE_TYPE, typeVetement);
        values.put(id_couleur_COLUMN ,couleur);
        values.put(NUMERO_CATEGORIE,categorie);
        values.put(COLONNE_SAISON, article.getSaison());
        values.put(MATERIEL_COLUMN, article.getMateriel());
        values.put(COLONNE_DESCRIPTION, article.getNomArticle());
        db.insert("vestiaires", null,values);
    }
    public  Cursor afficherdetails(SQLiteDatabase db, int id  )  {
        Cursor cursor = db.rawQuery("SELECT * FROM vestiaire as vest WHERE vest._id = ?",
                new String[]{""+id});
        return  cursor ;
    }
    public Cursor listerTous(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT * FROM vestiaire",null);
        return  cursor ;
    }
    public  Cursor afficherCouleur(SQLiteDatabase db, String couleur)  {
        Cursor cursor = db.rawQuery("SELECT v.VETEMENT_COLUMN,v.SAISON_COLUMN,v.MATERIEL_COLUMN, c.nom FROM vestiaire v ,COULEUR_TABLE c WHERE c.id_couleur_COLUMN = v.id_couleur_COLUMN AND c.NOM_COLUMN= ?",new String[]{""+couleur});
        return  cursor ;
    }

    public boolean isExist(String num,SQLiteDatabase db,String tableName){
        String query = "SELECT number FROM "+tableName +"WHERE number='"+num+"' LIMIT 1";
        Cursor row = db.rawQuery(query, null);
        return row.moveToFirst();
    }
    public  Cursor afficherSaison(SQLiteDatabase db, String saison   )  {

        Cursor cursor = db.rawQuery("SELECT * FROM vestiaire as vest WHERE vest.SAISON_COLUMN = ?",
                new String[]{""+saison});
        return  cursor ;
    }
    public  Cursor afficherCategorie(SQLiteDatabase db, String categorie   )  {
        Cursor cursor = db.rawQuery("SELECT v.VETEMENT_COLUMN,v.SAISON_COLUMN,v.MATERIEL_COLUMN, c.nom FROM vestiaire v ,CATEGORIE_TABLE c WHERE c.NUMERO_CATEGORIE = v.NUMERO_CATEGORIE AND c.NOM_COLUMN= ?",
                new String[]{""+categorie});
        return  cursor ;
    }
    public  Cursor afficherType(SQLiteDatabase db, String type   )  {
        Cursor cursor = db.rawQuery("SELECT t.VETEMENT_COLUMN,v.SAISON_COLUMN,v.NOM_COLUMN, t.NOM_COLUMN FROM vestiaire v ,VETEMENT_TABLE t WHERE t.VETEMENT_ID = v.VETEMENT_ID AND t.NOM_COLUMN= ?",
                new String[]{""+type});
        return  cursor ;
    }

    public  Cursor afficherFavoris(SQLiteDatabase db, int favoris   )  {
        Cursor cursor = db.rawQuery("SELECT * FROM vestiaire as vest WHERE vest.FAVORIS_COLUMN  = ?",
                new String[]{""+favoris});
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

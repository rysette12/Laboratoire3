package com.example.maryse.laboratoire3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "vestiaire_directory";
    public static final String  VETEMENT_ID = "NumeroVetement";
    public static final String VETEMENT_TABLE = "TYpeVetement";
    public static final String  COULEUR_TABLE = "Couleur";
    public static final String id_couleur_COLUMN = "_id_couleur";
    public static final String  SAISON_COLUMN = "saison";
    public static final String  MATERIEL_COLUMN = "materiel";
    public static final String CATEGORIE_TABLE="categorie";
    public static final String NUMERO_CATEGORIE="numero_categorie";
    public static final String NOM_COLUMN ="nom";
    public static final String FAVORIS_COLUMN ="favoris";
    public static final String  PASSWORD_COLUMN = "password";
    public static final String PRENOM_COLUMN="prenom";
    public static final String ADRESSE_COLUMN ="adresse_courriel" ;
    public static final String  DATE_ACHAT ="dateAchat";
    public static final String  DATE_PORTEE ="datePortee";
    public static final String TABLE_NAME ="utilisateur";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS vestiaire (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                id_couleur_COLUMN +"INTEGER, " +
                VETEMENT_ID +"TEXT," +
                SAISON_COLUMN +"TEXT , " +
                MATERIEL_COLUMN+"TEXT, " +
                NUMERO_CATEGORIE  +"TEXT," +
                NOM_COLUMN +"TEXT, " +
                FAVORIS_COLUMN+"NUMERIC," +
                "dateAchat DATE, " +
                "situation TEXT)";

        String sql2 = "CREATE TABLE IF NOT EXISTS " + COULEUR_TABLE +"(" +
                id_couleur_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NOM_COLUMN+" TEXT)";
        String sql3 = "CREATE TABLE IF NOT EXISTS " +  CATEGORIE_TABLE + "(" +
                NUMERO_CATEGORIE +"INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NOM_COLUMN+"TEXT)";

        String sql4 = "CREATE TABLE IF NOT EXISTS "+ VETEMENT_TABLE + "(" +
                VETEMENT_ID+"INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "type TEXT)";

        String sql5 = "CREATE TABLE IF NOT EXISTS "
                +TABLE_NAME+ "(" +" _id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                NOM_COLUMN +"TEXT,"+  PRENOM_COLUMN +"TEXT,"+ PASSWORD_COLUMN +"TEXT," + ADRESSE_COLUMN +"TEXT)";
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
        if((!isExist(article.getCouleur(),db,"couleur"))&&(!isExist(article.getCategorie(),db,CATEGORIE_TABLE)) ){
            db.execSQL("INSERT INTO couleur ( "+NOM_COLUMN+") VALUES('"+article.getCouleur()+"')");
            Cursor cursor = db.rawQuery("SELECT * FROM couleur as c  WHERE c.NOM_COLUMN = ?",new String[]{""+article.getCouleur()});
            couleur = cursor.getString(cursor.getColumnIndex( id_couleur_COLUMN));
            db.execSQL("INSERT INTO" +CATEGORIE_TABLE+"( "+NOM_COLUMN+") VALUES('"+article.getCategorie()+"')");
            Cursor cursor1 = db.rawQuery("SELECT * FROM "+CATEGORIE_TABLE+" as c  WHERE c.NOM_COLUMN = ?",new String[]{""+article.getCategorie()});
            categorie = cursor1.getString(cursor1.getColumnIndex( VETEMENT_ID));
        }
        if (!isExist(article.getVetement(),db,VETEMENT_TABLE)){
            db.execSQL("INSERT INTO" +VETEMENT_TABLE+"( "+NOM_COLUMN+") VALUES('"+article.getVetement()+"')");
            Cursor cursor1 = db.rawQuery("SELECT * FROM "+VETEMENT_TABLE+" as c  WHERE c.NOM_COLUMN = ?",new String[]{""+article.getVetement()});
            typeVetement = cursor1.getString(cursor1.getColumnIndex( VETEMENT_ID));
        }
        else{
            Cursor cursor = db.rawQuery("SELECT * FROM couleur as c  WHERE c.NOM_COLUMN = ?",new String[]{""+article.getCouleur()});
            couleur =cursor.getString(cursor.getColumnIndex( id_couleur_COLUMN));
            Cursor cursor1 = db.rawQuery("SELECT * FROM "+CATEGORIE_TABLE+" as c  WHERE c.NOM_COLUMN = ?",new String[]{""+article.getCategorie()});
            categorie = cursor1.getString(cursor1.getColumnIndex( NUMERO_CATEGORIE));
            Cursor cursor2= db.rawQuery("SELECT * FROM "+VETEMENT_TABLE+" as c  WHERE c.NOM_COLUMN = ?",new String[]{""+article.getVetement()});
            typeVetement = cursor1.getString(cursor1.getColumnIndex( VETEMENT_ID));
        }
        ContentValues values = new ContentValues();
        values.put(VETEMENT_ID , typeVetement);
        values.put(id_couleur_COLUMN ,couleur);
        values.put(NUMERO_CATEGORIE,categorie);
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

}

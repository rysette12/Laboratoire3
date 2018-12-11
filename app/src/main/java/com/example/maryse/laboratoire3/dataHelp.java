package com.example.maryse.laboratoire3;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class dataHelp extends SQLiteOpenHelper {
        public static final String DATABASE_NAME = "vestiaire_directory";
        public static final String  VETEMENT_COLUMN = "vetement";
        public static final String  COULEUR_COLUMN = "saison";
        public static final String  SAISON_COLUMN = "Couleur";
        public static final String  MATERIEL_COLUMN = "materiel";
        public static final String CATEGORIE_COLUMN ="categorie";
        public static final String NOM_COLUMN ="nom";
        public static final String FAVORIS_COLUMN ="favoris";
        public static final String IMAGE_COLUMN ="images";
        public static final String  DATE_ACHAT ="dateAchat";
        public static final String  DATE_PORTEE ="datePortee";

        public static final String TABLE_NAME ="utilisateur";
        public static final String  PASSWORD_COLUMN = "password";
        public static final String PRENOM_COLUMN="prenom";
        public static final String ADRESSE_COLUMN ="adresse_courriel" ;

        public dataHelp(Context context) {
            super(context, DATABASE_NAME, null, 1);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql = "CREATE TABLE IF NOT EXISTS vestiaire (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    VETEMENT_COLUMN +"TEXT, " +
                    COULEUR_COLUMN +"TEXT, " +
                    SAISON_COLUMN+"TEXT, " +
                    MATERIEL_COLUMN+"TEXT, " +
                    CATEGORIE_COLUMN  +"TEXT," +
                    NOM_COLUMN +"TEXT, " +
                    FAVORIS_COLUMN+"NUMERIC," +
                    IMAGE_COLUMN+"TEXT,"+
                    DATE_ACHAT +"TEXT, " +
                    DATE_PORTEE+"TEXT,"+"situation TEXT)";

            String sql2 = "CREATE TABLE IF NOT EXISTS " +COULEUR_COLUMN +"(" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nom TEXT)";
            String sql3 = "CREATE TABLE IF NOT EXISTS " + CATEGORIE_COLUMN + "(" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NOM_COLUMN +"TEXT)";

            String sql4 = "CREATE TABLE IF NOT EXISTS "+ VETEMENT_COLUMN + "(" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "type TEXT)";
            String sql5 = "CREATE TABLE IF NOT EXISTS "
                    +TABLE_NAME+ "(" +" _id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    NOM_COLUMN +"TEXT,"+  PRENOM_COLUMN +"TEXT,"+ PASSWORD_COLUMN +"TEXT," + ADRESSE_COLUMN +"TEXT)";
            db.execSQL(sql);
            db.execSQL(sql2);
            db.execSQL(sql3);
            db.execSQL(sql4);
            db.execSQL(sql5);
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

        public void ajouterUtilisateur(SQLiteDatabase db , Utilisateur usager){
            ContentValues values = new ContentValues();
            values.put(NOM_COLUMN , usager.getNom());
            values.put(PRENOM_COLUMN ,usager.getPrenom());
            values.put(PASSWORD_COLUMN, usager.getPrenom());
            values.put(ADRESSE_COLUMN , usager.getAdresseCouriel());
            db.insert(TABLE_NAME, null,values);
        }
        public  Cursor afficherCouleur(SQLiteDatabase db, String couleur   )  {
            Cursor cursor = db.rawQuery("SELECT * FROM vestiaire as vest WHERE vest.COULEUR_COLUMN = ?",
                    new String[]{""+couleur});
            return  cursor ;
        }
        public  Cursor afficherSaison(SQLiteDatabase db, String saison   )  {
            Cursor cursor = db.rawQuery("SELECT * FROM vestiaire as vest WHERE vest.SAISON_COLUMN = ?",
                    new String[]{""+saison});
            return  cursor ;
        }
        public  Cursor afficherCategorie(SQLiteDatabase db, String categorie   )  {
            Cursor cursor = db.rawQuery("SELECT * FROM vestiaire as vest WHERE vest.CATEGORIE_COLUMN  = ?",
                    new String[]{""+categorie});
            return  cursor ;

        } public  Cursor afficherType(SQLiteDatabase db, String type   )  {
            Cursor cursor = db.rawQuery("SELECT * FROM vestiaire as vest WHERE vest.VETEMENT_COLUMN  = ?",
                    new String[]{""+type});
            return  cursor ;
        }
        public  Cursor afficherFavoris(SQLiteDatabase db, int favoris  )  {
            Cursor cursor = db.rawQuery("SELECT * FROM vestiaire as vest WHERE vest.FAVORIS_COLUMN  = ?",
                    new String[]{""+favoris});
            return  cursor ;
        }

    }

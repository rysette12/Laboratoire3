package com.example.maryse.laboratoire3;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ActivityDetails extends BaseActivity {
        protected TextView articleName;
        protected TextView articleCategorie;
        protected TextView articleVetement;
        protected TextView articleCouleur;
        protected TextView articleMateriel;
        protected TextView articleSaison;
        protected TextView officePhone;
        protected TextView cellPhone;
        protected DatabaseHelper data;
        protected int articleId;
        protected Cursor cursor;
        SQLiteDatabase db;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.textview_layout);
            articleId = getIntent().getIntExtra("ARTICLE_ID", 0);
            db = data.getWritableDatabase();
            cursor = data.detailsArticle(db,articleId);
            if (cursor.getCount() == 1)
            { cursor.moveToFirst();
                articleName = (TextView) findViewById(R.id.textViewN);
                articleName.setText(cursor.getString(cursor.getColumnIndex("nom")));
                articleVetement = (TextView)findViewById(R.id.textViewV);
                articleVetement.setText(cursor.getString(cursor.getColumnIndex("vetement")));
                articleCategorie= (TextView)findViewById(R.id.textViewCa);
                articleCategorie.setText(cursor.getString(cursor.getColumnIndex("categorie")));
                articleCouleur = (TextView)findViewById(R.id.textViewCo);
                articleCouleur.setText(cursor.getString(cursor.getColumnIndex("couleur")));
                articleMateriel = (TextView)findViewById(R.id.textViewM);
                articleMateriel.setText(cursor.getString(cursor.getColumnIndex("materiel")));
                articleSaison = (TextView)findViewById(R.id.textViewSa);
                articleSaison.setText(cursor.getString(cursor.getColumnIndex("saison")));
            }

            findViewById(R.id.buttonEdit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db=  data.getWritableDatabase();
                   // Intent intent = new Intent( EdittActivity.class);
                    cursor= data.detailsArticle(db,articleId);
                    //intent.putExtra("ARTICLE_ID", cursor.getInt(cursor.getColumnIndex("_id")));
                    //startActivity(intent);

                }
            });
        }
    }


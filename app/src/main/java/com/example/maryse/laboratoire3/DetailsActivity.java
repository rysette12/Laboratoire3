package com.example.maryse.laboratoire3;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends BaseActivity {
        private DatabaseHelper db;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detail);
            final int id = getIntent().getIntExtra(BaseActivity.ARTICLE_ID_KEY, 0);

            db = new DatabaseHelper(this);

            final Cursor cursor = db.detailsArticle(db.getReadableDatabase(), id);

            if (cursor.getCount() == 1) {
                cursor.moveToFirst();
                ((TextView) findViewById(R.id.textViewN)).setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLONNE_NOM)));
                ((TextView)findViewById(R.id.textViewSa)).setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLONNE_SAISON)));
                ((TextView)findViewById(R.id.textViewV)).setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLONNE_TYPE)));
                ((TextView)findViewById(R.id.textViewCo)).setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLONNE_COULEUR)));
                ((TextView)findViewById(R.id.textViewCouleurDetails)).setBackgroundColor(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLONNE_VALEUR)));
                ((TextView)findViewById(R.id.textViewCa)).setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLONNE_CATEGORIE)));
                String imagePath = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLONNE_IMAGE));
                Bitmap image = BitmapFactory.decodeFile(imagePath);
                ((ImageView) findViewById(R.id.imageViewPreviewDetails)).setImageBitmap(image);
            }

            findViewById(R.id.buttonModifier).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), AjouterActivity.class);
                    i.putExtra(BaseActivity.ARTICLE_ID_KEY, id);
                    startActivity(i);
                }
            });

            findViewById(R.id.buttonSupprimer).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.supprimerArticle(db.getWritableDatabase(), id);
                    Toast.makeText(getApplicationContext(), "L'article a été supprimé.", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), ListerToutActivity.class);
                    startActivity(i);
                }
            });
        }
    }


package com.example.maryse.laboratoire3;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ReglagesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reglages);

        findViewById(R.id.buttonOkType).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = ((TextView) v.findViewById(R.id.editTextType)).getText().toString().trim();
                if (type.length() > 0) {
                    DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                    db.ajouterType(db.getWritableDatabase(), type);
                }
            }
        });

        findViewById(R.id.buttonOkCouleur).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String couleur = ((TextView) v.findViewById(R.id.editTextCouleur)).getText().toString().trim();
                if (couleur.length() > 0) {
                    DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                    db.ajouterCouleur(db.getWritableDatabase(), couleur);
                }
            }
        });

        findViewById(R.id.buttonOkCategorie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categorie = ((TextView) v.findViewById(R.id.editTextCategorie)).getText().toString().trim();
                if (categorie.length() > 0) {
                    DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                    db.ajouterCategorie(db.getWritableDatabase(), categorie);
                }
            }
        });
    }

}

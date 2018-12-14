package com.example.maryse.laboratoire3;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class EnresgistrerActivity extends BaseActivity {
    EditText editTextUtiliseurNom;
    EditText editTextUtiliseurPrenom;
    EditText editTextCourriel;
    EditText editTextPass;
    Button bouttonSauver, bouttonEffacer;
    DatabaseHelper myDb;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logup);
        myDb = new DatabaseHelper(this);
        editTextUtiliseurNom = (EditText) findViewById(R.id.editTextNom);
        editTextUtiliseurPrenom = (EditText) findViewById(R.id.editTextPrenom);
        editTextCourriel = (EditText) findViewById(R.id.editTextCouriel);
        editTextPass = (EditText) findViewById(R.id.editTextPass);
        bouttonSauver = (Button) findViewById(R.id.buttonSauver);
        bouttonEffacer = (Button) findViewById(R.id.buttonEffacer);
        bouttonSauver.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String nom = editTextUtiliseurNom.getText().toString();
                String prenom = editTextUtiliseurPrenom.getText().toString();
                String courriel = editTextCourriel.getText().toString();
                String pass = editTextPass.getText().toString();
                Utilisateur utilisateur = new Utilisateur(nom, prenom, pass,courriel);
                myDb.ajouterUtilisateur(myDb.getWritableDatabase(),utilisateur);
            }
        });
        bouttonEffacer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editTextUtiliseurNom.setText("");
                editTextUtiliseurPrenom.setText("");
                editTextPass.setText("");
                editTextCourriel.setText("");
            }
        });
    }

}

package com.example.maryse.laboratoire3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class OuvertSessionActivity extends BaseActivity {

    EditText editTextCourriel;
    EditText editTextPass;
    Button bouttonSauver, bouttonEffacer;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myDb = new DatabaseHelper(this);
        editTextCourriel = (EditText) findViewById(R.id.editTextCouriel);
        editTextPass = (EditText) findViewById(R.id.editTextPass);
        bouttonSauver = (Button) findViewById(R.id.buttonSauver);
        bouttonEffacer = (Button) findViewById(R.id.buttonEffacer);
        bouttonSauver.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            Utilisateur utilisateur = myDb.Verifier(myDb.getWritableDatabase(),new Utilisateur(null, null,editTextPass.getText().toString(), editTextCourriel.getText().toString()));
            if (utilisateur != null) {

            } else {
                
                }
            }
        });
        bouttonEffacer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editTextPass.setText("");
                editTextCourriel.setText("");
            }
        });
    }
}
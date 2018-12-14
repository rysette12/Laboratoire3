package com.example.maryse.laboratoire3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import yuku.ambilwarna.AmbilWarnaDialog;

public class ReglagesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reglages);

        final DatabaseHelper db = new DatabaseHelper(this);

        // Types
        findViewById(R.id.buttonAjouterType).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = ((TextView) v.findViewById(R.id.editTextReglagesType)).getText().toString().trim();
                if (type.length() > 0)
                    db.ajouterType(db.getWritableDatabase(), type);
            }
        });

        final List<String> listeTypes = db.tousLesTypes(db.getReadableDatabase());
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listeTypes);
        final Spinner spinnerType = ((Spinner) findViewById(R.id.spinnerReglagesType));
        spinnerType.setAdapter(dataAdapter);

        findViewById(R.id.buttonSupprimerType).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ReglagesActivity.this);
                alert.setTitle("Suppression");
                alert.setMessage("Voulez-vous vraiment supprimer ce type?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String type = spinnerType.getSelectedItem().toString();
                        if (db.supprimerType(db.getWritableDatabase(), type)) {
                            listeTypes.remove(type);
                            ((ArrayAdapter<String>) spinnerType.getAdapter()).notifyDataSetChanged();
                        } else
                            Toast.makeText(getApplicationContext(), "Erreur lors de la suppression. Avez-vous encore des articles utilisant ce type?", Toast.LENGTH_LONG).show();
                    }});
                alert.setNegativeButton(android.R.string.no, null);
                alert.show();
            }
        });

        // Couleurs
        final Button choisirCouleur = (Button) findViewById(R.id.buttonChoisirCouleur);
        final ArrayList<Integer> valeursCouleurs = db.toutesLesValeursCouleurs(db.getReadableDatabase());

        choisirCouleur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AmbilWarnaDialog dialog = new AmbilWarnaDialog(ReglagesActivity.this, ((ColorDrawable) choisirCouleur.getBackground()).getColor(),
                        new AmbilWarnaDialog.OnAmbilWarnaListener() {
                            @Override
                            public void onCancel(AmbilWarnaDialog dialog) {

                            }

                            @Override
                            public void onOk(AmbilWarnaDialog dialog, int color) {
                                choisirCouleur.setBackgroundColor(color);
                            }
                        });

                dialog.show();
            }
        });

        findViewById(R.id.buttonAjouterCouleur).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String couleur = ((TextView) v.findViewById(R.id.editTextReglagesCouleur)).getText().toString().trim();
                if (couleur.length() > 0)
                    db.ajouterCouleur(db.getWritableDatabase(), couleur, ((ColorDrawable) choisirCouleur.getBackground()).getColor());
            }
        });

        final List<String> listeCouleurs = db.toutesLesCouleurs(db.getReadableDatabase());
        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listeCouleurs);
        final Spinner spinnerCouleur = ((Spinner) findViewById(R.id.spinnerReglagesCouleur));
        spinnerCouleur.setAdapter(dataAdapter);

        findViewById(R.id.buttonSupprimerCouleur).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ReglagesActivity.this);
                alert.setTitle("Suppression");
                alert.setMessage("Voulez-vous vraiment supprimer cette couleur?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String couleur = spinnerCouleur.getSelectedItem().toString();
                        if (db.supprimerCouleur(db.getWritableDatabase(), couleur)) {
                            int index = listeCouleurs.indexOf(couleur);
                            listeCouleurs.remove(couleur);
                            valeursCouleurs.remove(valeursCouleurs.get(index));
                            ((ArrayAdapter<String>) spinnerCouleur.getAdapter()).notifyDataSetChanged();
                            findViewById(R.id.textViewSupprimerCouleur).setBackgroundColor(valeursCouleurs.get(index > 0 ? index - 1 : 0));
                        } else
                            Toast.makeText(getApplicationContext(), "Erreur lors de la suppression. Avez-vous encore des articles utilisant cette couleur?", Toast.LENGTH_LONG).show();
                    }});
                alert.setNegativeButton(android.R.string.no, null);
                alert.show();
            }
        });

        spinnerCouleur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                findViewById(R.id.textViewSupprimerCouleur).setBackgroundColor(valeursCouleurs.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Categories
        findViewById(R.id.buttonAjouterCategorie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categorie = ((TextView) v.findViewById(R.id.editTextReglagesCategorie)).getText().toString().trim();
                if (categorie.length() > 0)
                    db.ajouterCategorie(db.getWritableDatabase(), categorie);
            }
        });

        final List<String> listeCategories = db.toutesLesCategories(db.getReadableDatabase());
        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listeCategories);
        final Spinner spinnerCategorie = ((Spinner) findViewById(R.id.spinnerReglagesCategorie));
        spinnerCategorie.setAdapter(dataAdapter);

        findViewById(R.id.buttonSupprimerCategorie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ReglagesActivity.this);
                alert.setTitle("Suppression");
                alert.setMessage("Voulez-vous vraiment supprimer cette catégorie?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String categorie = spinnerCategorie.getSelectedItem().toString();
                        if (db.supprimerCategorie(db.getWritableDatabase(), categorie)) {
                            listeCategories.remove(categorie);
                            ((ArrayAdapter<String>) spinnerCategorie.getAdapter()).notifyDataSetChanged();
                        } else
                            Toast.makeText(getApplicationContext(), "Erreur lors de la suppression. Avez-vous encore des articles utilisant cette catégorie?", Toast.LENGTH_LONG).show();
                    }});
                alert.setNegativeButton(android.R.string.no, null);
                alert.show();
            }
        });
    }

}

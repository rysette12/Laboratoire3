package com.example.maryse.laboratoire3;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
public class AjouterActivity extends BaseActivity {
    // images
    static final int PRENDRE_PHOTO_INTENT = 1;
    static final int CHOISIR_PHOTO_INTENT = 2;

    private DatabaseHelper db;

    private int jour, mois, annee;
    private String image = "";
    private int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter);

        db = new DatabaseHelper(this);

        final Spinner spinnerSaison = findViewById(R.id.spinnerSaison);
        String[] listeSaison = getResources().getStringArray(R.array.saisons);
        ArrayAdapter dataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, listeSaison);
        spinnerSaison.setAdapter(dataAdapter);

        final Spinner spinnerType = findViewById(R.id.spinnerType);
        List<String> listeType = db.tousLesTypes(db.getReadableDatabase());
        dataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, listeType);
        spinnerType.setAdapter(dataAdapter);

        final Spinner spinnerCouleur = findViewById(R.id.spinnerCouleur);
        List<String> listeCouleur = db.toutesLesCouleurs(db.getReadableDatabase());
        dataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, listeCouleur);
        spinnerCouleur.setAdapter(dataAdapter);

        final Spinner spinnerCategorie = findViewById(R.id.spinnerCategorie);
        List<String> listeCategorie = db.toutesLesCategories(db.getReadableDatabase());
        dataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, listeCategorie);
        spinnerCategorie.setAdapter(dataAdapter);

        //Datepicker
        Calendar cal = Calendar.getInstance();
        annee = cal.get(Calendar.YEAR);
        mois = cal.get(Calendar.MONTH);
        jour = cal.get(Calendar.DAY_OF_MONTH);

        findViewById(R.id.buttonDate).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                datePicker();
            }
        });

        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            findViewById(R.id.buttonPrendrePhoto).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    prendrePhoto();
                }
            });
        } else
            findViewById(R.id.buttonPrendrePhoto).setVisibility(View.INVISIBLE);

        findViewById(R.id.buttonChoisirPhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choisirPhoto();
            }
        });

        findViewById(R.id.buttonEffacer).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((EditText) findViewById(R.id.editTextNom)).setText("");
                ((EditText) findViewById(R.id.editTextDate)).setText("");

                // Selectionne le Spinner au premier élément
                spinnerSaison.setSelection(0);
                spinnerType.setSelection(0);
                spinnerCouleur.setSelection(0);
                spinnerCategorie.setSelection(0);
            }
        });

        findViewById(R.id.buttonSauver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // EditTexts
                String nom = ((EditText) findViewById(R.id.editTextNom)).getText().toString();
                String saison = spinnerSaison.getSelectedItem().toString();
                String type = spinnerType.getSelectedItem().toString();
                String couleur = spinnerCouleur.getSelectedItem().toString();
                String categorie = spinnerCategorie.getSelectedItem().toString();
                String date = (((EditText) findViewById(R.id.editTextDate)).getText()).toString();
                Article article = new Article(nom, saison, type, couleur, categorie, image, date);

                if (id >= 0)
                    db.ajouterArticle(db.getWritableDatabase(), article);
                else
                    db.modifierArticle(db.getWritableDatabase(), article, id);

                Intent intent = new Intent(AjouterActivity.this, ListerToutActivity.class);
                startActivity(intent);
            }
        });

        // Remplir les données s'il s'agit d'un cas d'édition
        Bundle extras = getIntent().getExtras();
        if (extras.containsKey(BaseActivity.ARTICLE_ID_KEY)) {
            id = extras.getInt(BaseActivity.ARTICLE_ID_KEY);
            Cursor article = db.detailsArticle(db.getReadableDatabase(), id);
            article.moveToFirst();

            String nom = article.getString(article.getColumnIndexOrThrow(DatabaseHelper.COLONNE_NOM));
            ((EditText) findViewById(R.id.editTextNom)).setText(nom);
            String saison = article.getString(article.getColumnIndexOrThrow(DatabaseHelper.COLONNE_SAISON));
            spinnerSaison.setSelection(Arrays.asList(listeSaison).indexOf(saison));
            String type = article.getString(article.getColumnIndexOrThrow(DatabaseHelper.COLONNE_TYPE));
            spinnerType.setSelection(listeType.indexOf(type));
            String couleur = article.getString(article.getColumnIndexOrThrow(DatabaseHelper.COLONNE_COULEUR));
            spinnerCouleur.setSelection(listeCouleur.indexOf(couleur));
            int coul = article.getInt(article.getColumnIndexOrThrow(DatabaseHelper.COLONNE_VALEUR));
            ((TextView) findViewById(R.id.textViewAjouterCouleur)).setBackgroundColor(coul);
            String categorie = article.getString(article.getColumnIndexOrThrow(DatabaseHelper.COLONNE_CATEGORIE));
            spinnerCategorie.setSelection(listeCategorie.indexOf(categorie));
            image = article.getString(article.getColumnIndexOrThrow(DatabaseHelper.COLONNE_IMAGE));
            afficherImage(image);
            String date = article.getString(article.getColumnIndexOrThrow(DatabaseHelper.COLONNE_IMAGE));
            ((EditText) findViewById(R.id.editTextDate)).setText(date);
        }
    }

    private void datePicker(){
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                annee = selectedYear;
                mois = selectedMonth;
                jour = selectedDay;
                ((EditText) findViewById(R.id.editTextDate)).setText(selectedYear + "/" + selectedMonth + "/" + selectedDay);
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(AjouterActivity.this, listener, annee, mois, jour);
        datePickerDialog.show();
    }

    private void prendrePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File img = null;
            try {
                img = ImageHelper.createImageFile(this);
                image = img.getAbsolutePath();
            } catch (IOException ex) {
                Toast.makeText(this, "Erreur: Veuillez recommencer.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (img != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        img);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, PRENDRE_PHOTO_INTENT);
            }
        }
    }

    private void choisirPhoto() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, CHOISIR_PHOTO_INTENT);
    }

    private void afficherImage(String imagePath) {
        Bitmap image = BitmapFactory.decodeFile(imagePath);
        ((ImageView) findViewById(R.id.imageViewPreview)).setImageBitmap(image);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PRENDRE_PHOTO_INTENT && resultCode == RESULT_OK) {
            afficherImage(image);
        } else if (requestCode == CHOISIR_PHOTO_INTENT && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            File copy = null;
            try {
                copy = ImageHelper.createImageFile(this);
                image = copy.getAbsolutePath();
                ImageHelper.copyFile(this, imageUri, copy);
                afficherImage(image);
            } catch (Exception ex) {
                if (copy != null)
                    copy.delete();
                Toast.makeText(this, "Erreur: Veuillez recommencer.", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }
}


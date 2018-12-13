package com.example.maryse.laboratoire3;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

public class EditActivity extends BaseActivity {
    DatabaseHelper myDb;
    private EditText textDate;
    private int annee;
    private int mois;
    private int jour;
    private Button btnAjouter;
    private Button btnEfface;
    private Button btnDate;
    private Spinner spinnerCouleur, spinnerType, spinnerCategorie;
    private String couleur, categorie, type,dateAchat,saison,nom;
    protected int articleId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter);
        myDb = new DatabaseHelper(this);
        spinnerCategorie =  findViewById(R.id.spinnerCategorie);
        spinnerCouleur = findViewById(R.id.spinnerCouleur);
        spinnerType =  findViewById(R.id.spinnerType);
        loadSpinner(DatabaseHelper.TABLE_CATEGORIE, spinnerCategorie);
        loadSpinner(DatabaseHelper.TABLE_COULEUR, spinnerCouleur);
        loadSpinner(DatabaseHelper.TABLE_TYPE, spinnerType);
        btnEfface =  findViewById(R.id.buttonDate);
        btnAjouter =  findViewById(R.id.buttonSauver);
        btnDate = findViewById(R.id.buttonDate);
        ((TextView) findViewById(R.id.textViewTitle)).setText("Modifier");
        btnAjouter.setText("Modifier");
        articleId = getIntent().getIntExtra("ARTICLE_ID", 0);

        //Datepicker
        Calendar cal = Calendar.getInstance();
        annee = cal.get(Calendar.YEAR);
        // 0-11
        mois = cal.get(Calendar.MONTH);
        // Commence à 1
        jour = cal.get(Calendar.DAY_OF_MONTH);


        textDate = (EditText) findViewById(R.id.editTextDate);
        // Spinner
        spinnerCategorie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                categorie = spinnerCategorie.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }});

        spinnerCouleur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                couleur = spinnerCouleur.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }});
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                type = spinnerType.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }});
        btnDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                datePicker();
            }
        });
        btnEfface.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((EditText) findViewById(R.id.editTextNom)).setText("");
                ((EditText) findViewById(R.id.editSaison)).setText("");
                ((EditText) findViewById(R.id.editTextDate)).setText("");

                // Selectionne le Spinner au premier élément
                ((Spinner) findViewById(R.id.spinnerCategorie)).setSelection(0);
                ((Spinner) findViewById(R.id.spinnerCouleur)).setSelection(0);
                ((Spinner) findViewById(R.id.spinnerType)).setSelection(0);
            }
        });

        btnAjouter.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              // EditTexts4
                                              nom = ((EditText) findViewById(R.id.editTextNom)).getText().toString();
                                              saison = ((EditText) findViewById(R.id.editSaison)).getText().toString();
                                              dateAchat = (((EditText) findViewById(R.id.editTextDate)).getText()).toString();
                                              Article article = new Article(type,couleur,saison,categorie,nom,dateAchat);
                                              myDb.modifierArticle(myDb.getWritableDatabase(),article,articleId);
                                          }
                                      }
        );
    }

    private void datePicker(){
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                annee = selectedYear;
                mois = selectedMonth;
                jour = selectedDay;
                textDate.setText(selectedYear + "/" + selectedMonth + "/" + selectedDay);
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, listener, annee, mois, jour);
        datePickerDialog.show();
    }

    private void loadSpinner(String nomtable, Spinner sp) {


        List<String> liste = myDb.listerValeursDistinctes(nomtable);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, liste);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp.setAdapter(dataAdapter);
    }
}


package com.example.maryse.laboratoire3;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import java.util.Calendar;
import java.util.List;

public class AjouterActivity extends BaseActivity {
    DatabaseHelper myDb;
    private EditText textDate;
    private int annee;
    private int mois;
    private int jour;
    private Button btnAjouter;
    private Button btnEfface;
    private Button btnDate;
    private Spinner spcol, spvet,spcat;
    private String couleur ,categarie,materiel,vetement,saison;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter);
        myDb = new DatabaseHelper(this);
        spcat =  findViewById(R.id.spinneCat);
        spcol = findViewById(R.id.spinneCol);
        spvet =  findViewById(R.id.spinneVet);
        loadSpinnerCat(DatabaseHelper.TABLE_CATEGORIE, spcat);
        loadSpinnerCat(DatabaseHelper.TABLE_COULEUR, spcol);
        loadSpinnerCat(DatabaseHelper.TABLE_TYPE, spvet);
        btnEfface =  findViewById(R.id.buttonDate);
        btnAjouter =  findViewById(R.id.buttonSauver);
        btnDate = findViewById(R.id.buttonDate);
        btnAjouter.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              // EditTexts4
                                              String nom = ((EditText) findViewById(R.id.editTextNom)).getText().toString();

                                              //Datepicker
                                              Calendar cal = Calendar.getInstance();
                                              annee = cal.get(Calendar.YEAR);
                                              // 0-11
                                              mois = cal.get(Calendar.MONTH);
                                              // Commence à 1
                                              jour = cal.get(Calendar.DAY_OF_MONTH);
                                              ;

                                              textDate = (EditText) findViewById(R.id.editTextDate);

                                              // Spinner
                                              spcat.setOnItemSelectedListener(new OnItemSelectedListener() {
                                                  @Override
                                                  public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                                      categarie = spcat.getItemAtPosition(position).toString();
                                                  }

                                                  @Override
                                                  public void onNothingSelected(AdapterView<?> parent) {
                                                  }});

                                              spcol.setOnItemSelectedListener(new OnItemSelectedListener() {
                                                  @Override
                                                  public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                                      couleur = spcol.getItemAtPosition(position).toString();
                                                  }

                                                  @Override
                                                  public void onNothingSelected(AdapterView<?> parent) {
                                                  }});
                                              spvet.setOnItemSelectedListener(new OnItemSelectedListener() {
                                                  @Override
                                                  public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                                      vetement= spvet.getItemAtPosition(position).toString();
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


                                                      // Selectionne le Spinner au premier élément
                                                      ((Spinner) findViewById(R.id.spinneCat)).setSelection(0);
                                                      ((Spinner) findViewById(R.id.spinneVet)).setSelection(0);
                                                      ((Spinner) findViewById(R.id.spinneCol)).setSelection(0);
                                                  }
                                              });

                                              String date = ((EditText) ((EditText) findViewById(R.id.editTextDate)).getText()).toString();
                                              //Article a = new Article(vetement, couleur,saison, materiel, categarie, nom,date,date );
                                              //myDb.ajouterArticle(myDb.getWritableDatabase(),a);
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

    private void loadSpinnerCat(String nomtable, Spinner sp) {


        List<String> lables = myDb.listerValeursDistinctes(nomtable);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, lables);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp.setAdapter(dataAdapter);
    }
}

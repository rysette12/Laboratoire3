package com.example.maryse.laboratoire3;

import android.app.DatePickerDialog;
import android.media.effect.Effect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

public class AjouteActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    private EditText textDate;
    private int annee;
    private int mois;
    private int jour;
    private Button btnAjouter;
    private Button btnEfface;
    private Button btnDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_ajouter);
        myDb = new DatabaseHelper(this);
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
           String categarie = ((Spinner) findViewById(R.id.spinneCat)).getSelectedItem().toString();
           String materiel = ((Spinner) findViewById(R.id.spinneMat)).getSelectedItem().toString();
           String color = ((Spinner) findViewById(R.id.spinneCol)).getSelectedItem().toString();
           String vetement = ((Spinner) findViewById(R.id.spinneVet)).getSelectedItem().toString();
           String saison = ((Spinner) findViewById(R.id.spinneSai)).getSelectedItem().toString();

           //Button
           btnDate.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   datePicker();
               }
           });
           btnEfface.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   // Vide les Textboxes
                   ((EditText) findViewById(R.id.editTextNom)).setText("");


                   // Selectionne le Spinner au premier élément
                   ((Spinner) findViewById(R.id.spinneCat)).setSelection(0);
                   ((Spinner) findViewById(R.id.spinneVet)).setSelection(0);
                   ((Spinner) findViewById(R.id.spinneSai)).setSelection(0);
                   ((Spinner) findViewById(R.id.spinneCol)).setSelection(0);
                   ((Spinner) findViewById(R.id.spinneMat)).setSelection(0);
               }
           });
           String date = ((EditText) ((EditText) findViewById(R.id.editTextDate)).getText()).toString();
           Article a = new Article(vetement, color,saison, materiel, categarie, nom,date,date );
           myDb.ajouterArticle(myDb.getWritableDatabase(),a);
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
}

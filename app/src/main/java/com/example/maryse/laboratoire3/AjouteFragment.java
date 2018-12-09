package com.example.maryse.laboratoire3;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AjouteFragment extends Fragment {
    DataProjet myDb;
    private EditText textDate;
    private int annee;
    private int mois;
    private int jour;
    public static AjouteFragment newInstance() {
        return new AjouteFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_ajouter, null);
        myDb = new DataProjet(this.getContext());

        v.findViewById(R.id.buttonSauver).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               // EditTexts4
               String nom = ((EditText) v.findViewById(R.id.editTextNom)).getText().toString();

               //Datepicker
               Calendar cal = Calendar.getInstance();
               annee = cal.get(Calendar.YEAR);
               // 0-11
               mois = cal.get(Calendar.MONTH);
               // Commence à 1
               jour = cal.get(Calendar.DAY_OF_MONTH);
               ;

               textDate = (EditText) v.findViewById(R.id.editTextDate);


               // Spinner
               String categarie = ((Spinner) v.findViewById(R.id.spinneCat)).getSelectedItem().toString();
               String materiel = ((Spinner) v.findViewById(R.id.spinneMat)).getSelectedItem().toString();
               String color = ((Spinner) v.findViewById(R.id.spinneCol)).getSelectedItem().toString();
               String vetement = ((Spinner) v.findViewById(R.id.spinneVet)).getSelectedItem().toString();
               String saison = ((Spinner) v.findViewById(R.id.spinneSai)).getSelectedItem().toString();

               //Button
               v.findViewById(R.id.buttonDate).setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       datePicker();
                   }
               });
               String date = ((EditText) ((EditText) v.findViewById(R.id.editTextDate)).getText()).toString();
               Article a = new Article(vetement, color,saison, materiel, categarie, nom,date,date );
                  myDb.ajouterArticle(myDb.getWritableDatabase(),a);
           }

       }
        );

        v.findViewById(R.id.buttonEffacer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Vide les Textboxes
                ((EditText) v.findViewById(R.id.editTextNom)).setText("");


                // Selectionne le Spinner au premier élément
                ((Spinner) v.findViewById(R.id.spinneCat)).setSelection(0);
                ((Spinner) v.findViewById(R.id.spinneVet)).setSelection(0);
                ((Spinner) v.findViewById(R.id.spinneSai)).setSelection(0);
                ((Spinner) v.findViewById(R.id.spinneCol)).setSelection(0);
                ((Spinner) v.findViewById(R.id.spinneMat)).setSelection(0);
            }
        });
        return v;
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
        DatePickerDialog datePickerDialog = new DatePickerDialog(this.getContext(), listener, annee, mois, jour);
        datePickerDialog.show();
    }



}

package com.example.maryse.laboratoire3;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class Ensemble_CursorAdapter extends BaseActivity  {
    private ListView maListViewPerso;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_layout);
        //Récupération de la listview créée dans le fichier activity_list_view
        maListViewPerso = (ListView) findViewById(R.id.listView);
        //Création de la ArrayList qui nous permettra de remplire la listView
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<String, String>();;
        DatabaseHelper db = new DatabaseHelper(this);
        Cursor cursor = db.listerEnsemble(db.getReadableDatabase());
        for (int i =0; i<cursor.getCount();i++){
            map.put("NomEnsemble","Ensemble"+cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLONNE_NUMERO_ENSEMBLE)));
            map.put("image",cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLONNE_NOM)));
            listItem.add(map);
        }

        //Création d'un SimpleAdapter qui se chargera de mettre les items présent dans notre list (listItem) dans la vue un ensemble
        SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.list_un_ensemble,
                new String[] {"image", "NomEnsemble",}, new int[] {R.id.img, R.id.titre});
        maListViewPerso.setAdapter(mSchedule);
    }
    // J'ai pas malheureusement le temps de tous tester et j'ai fait de mon mieux helas je dois aller travailler j'espere de votre bord s'avance bien pour la base de donne
    // j'ai mis description mais faudrait mettre image je sais pas comment tu l as ajouter dans la base de donne mais merci beaucoup

}

package com.example.maryse.laboratoire3;

import android.database.Cursor;
import android.os.Bundle;

public class ListerParCouleurActivity extends ListerActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Cursor couleurs = db.toutesLesCouleurs(db.getReadableDatabase());
        for (int i = 0; i < couleurs.getCount(); i++) {
            couleurs.moveToPosition(i);
            tabTitles.add(couleurs.getString(couleurs.getColumnIndex(DatabaseHelper.COLONNE_COULEUR)));
        }

        for (int i = 0; i < tabTitles.size(); i++) {
            Cursor c = db.listerParCouleur(db.getReadableDatabase(), tabTitles.get(i));
            fragments.add(ListeArticlesFragment.newInstance(c));
        }

        pagerAdapter.notifyDataSetChanged();
    }
}

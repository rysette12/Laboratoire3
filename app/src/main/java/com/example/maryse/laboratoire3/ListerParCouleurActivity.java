package com.example.maryse.laboratoire3;

import android.database.Cursor;
import android.os.Bundle;

public class ListerParCouleurActivity extends ListerActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tabTitles.add("Couleur1");
        tabTitles.add("Couleur2");
        tabTitles.add("Couleur3");

        for (int i = 0; i < tabTitles.size(); i++) {
            Cursor c = db.afficherCouleur(db.getReadableDatabase(), tabTitles.get(i));
            fragments.add(ListeArticlesFragment.newInstance(c));
        }

        pagerAdapter.notifyDataSetChanged();
    }
}

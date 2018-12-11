package com.example.maryse.laboratoire3;

import android.database.Cursor;
import android.os.Bundle;

public class ListerParSaisonActivity extends ListerActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tabTitles.add("Printemps");
        tabTitles.add("Été");
        tabTitles.add("Automne");
        tabTitles.add("Hiver");

        for (int i = 0; i < tabTitles.size(); i++) {
            Cursor c = db.afficherSaison(db.getReadableDatabase(), tabTitles.get(i));
            fragments.add(ListeArticlesFragment.newInstance(c));
        }

        pagerAdapter.notifyDataSetChanged();
    }
}

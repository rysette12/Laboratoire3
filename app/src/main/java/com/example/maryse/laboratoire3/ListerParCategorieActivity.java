package com.example.maryse.laboratoire3;

import android.database.Cursor;
import android.os.Bundle;

public class ListerParCategorieActivity extends ListerActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tabTitles.add("Categorie1");
        tabTitles.add("Categorie2");
        tabTitles.add("Categorie3");

        for (int i = 0; i < tabTitles.size(); i++) {
            Cursor c = db.listerParCategorie(db.getReadableDatabase(), tabTitles.get(i));
            fragments.add(ListeArticlesFragment.newInstance(c));
        }

        pagerAdapter.notifyDataSetChanged();
    }
}

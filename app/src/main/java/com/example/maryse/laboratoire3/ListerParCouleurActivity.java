package com.example.maryse.laboratoire3;

import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;

public class ListerParCouleurActivity extends ListerActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tabTitles = db.toutesLesCouleurs(db.getReadableDatabase());

        for (int i = 0; i < tabTitles.size(); i++) {
            Cursor c = db.listerParCouleur(db.getReadableDatabase(), tabTitles.get(i));
            fragments.add(ListeArticlesFragment.newInstance(c));
        }

        pagerAdapter.notifyDataSetChanged();
    }
}

package com.example.maryse.laboratoire3;

import android.database.Cursor;
import android.os.Bundle;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class ListerParSaisonActivity extends ListerActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] saisons = getResources().getStringArray(R.array.saisons);
        tabTitles = new ArrayList<String>(Arrays.asList(saisons));

        for (int i = 0; i < tabTitles.size(); i++) {
            Cursor c = db.listerParSaison(db.getReadableDatabase(), tabTitles.get(i));
            fragments.add(ListeArticlesFragment.newInstance(c));
        }

        pagerAdapter.notifyDataSetChanged();
    }
}

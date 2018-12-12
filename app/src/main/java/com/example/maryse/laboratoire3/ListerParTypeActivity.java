package com.example.maryse.laboratoire3;

import android.database.Cursor;
import android.os.Bundle;

public class ListerParTypeActivity extends ListerActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tabTitles.add("Type1");
        tabTitles.add("Type2");
        tabTitles.add("Type3");

        for (int i = 0; i < tabTitles.size(); i++) {
            Cursor c = db.listerParType(db.getReadableDatabase(), tabTitles.get(i));
            fragments.add(ListeArticlesFragment.newInstance(c));
        }

        pagerAdapter.notifyDataSetChanged();
    }
}

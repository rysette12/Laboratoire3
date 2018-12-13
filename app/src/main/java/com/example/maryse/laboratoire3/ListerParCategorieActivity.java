package com.example.maryse.laboratoire3;

import android.database.Cursor;
import android.os.Bundle;

public class ListerParCategorieActivity extends ListerActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Cursor categories = db.toutesLesCategories(db.getReadableDatabase());
        for (int i = 0; i < categories.getCount(); i++) {
            categories.moveToPosition(i);
            tabTitles.add(categories.getString(categories.getColumnIndex(DatabaseHelper.COLONNE_CATEGORIE)));
        }

        for (int i = 0; i < tabTitles.size(); i++) {
            Cursor c = db.listerParCategorie(db.getReadableDatabase(), tabTitles.get(i));
            fragments.add(ListeArticlesFragment.newInstance(c));
        }

        pagerAdapter.notifyDataSetChanged();
    }
}

package com.example.maryse.laboratoire3;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.ArrayList;

public class ListerFavorisActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_layout);

        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        Cursor articles = db.afficherFavoris(db.getReadableDatabase());
        ListView lvItems = (ListView) findViewById(R.id.listView);
        ArticleCursorAdapter todoAdapter = new ArticleCursorAdapter(this, articles);
        lvItems.setAdapter(todoAdapter);
    }

}

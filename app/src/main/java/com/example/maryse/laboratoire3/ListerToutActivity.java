package com.example.maryse.laboratoire3;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

public class ListerToutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_layout);

        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        Cursor articles = db.listerTout(db.getReadableDatabase());
        ListView lvItems = (ListView) findViewById(R.id.listView);
        ArticleCursorAdapter todoAdapter = new ArticleCursorAdapter(this, articles);
        lvItems.setAdapter(todoAdapter);
    }

}

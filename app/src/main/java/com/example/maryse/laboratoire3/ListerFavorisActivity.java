package com.example.maryse.laboratoire3;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ListerFavorisActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_layout);

        DatabaseHelper db = new DatabaseHelper(this);
        final Cursor articles = db.listerArticlesFavoris(db.getReadableDatabase());
        ListView lvItems = (ListView) findViewById(R.id.listView);
        ArticleCursorAdapter adapter = new ArticleCursorAdapter(this, articles);
        lvItems.setAdapter(adapter);
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), DetailsActivity.class);
                articles.moveToPosition(position);
                i.putExtra(BaseActivity.ARTICLE_ID_KEY, articles.getColumnIndexOrThrow(DatabaseHelper.COLONNE_ID));
                startActivity(i);
            }
        });
    }

}

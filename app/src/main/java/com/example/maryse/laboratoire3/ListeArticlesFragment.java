package com.example.maryse.laboratoire3;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class ListeArticlesFragment extends Fragment {
    private Cursor articles;

    public static ListeArticlesFragment newInstance(Cursor data) {
        ListeArticlesFragment fragment = new ListeArticlesFragment();
        fragment.setArticles(data);
        return fragment;
    }

    private void setArticles (Cursor articles) {
        this.articles = articles;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.listview_layout, null);
        ListView lvItems = (ListView) v.findViewById(R.id.listView);
        ArticleCursorAdapter adapter = new ArticleCursorAdapter(getContext(), articles);
        lvItems.setAdapter(adapter);
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getContext(), MainActivity.class);//DetailsActivity.class);
                articles.moveToPosition(position);
                i.putExtra(BaseActivity.ARTICLE_ID_KEY, articles.getColumnIndexOrThrow(DatabaseHelper.COLONNE_ID));
                startActivity(i);
            }
        });
        return v;
    }
}

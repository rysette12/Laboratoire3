package com.example.maryse.laboratoire3;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        ArticleCursorAdapter todoAdapter = new ArticleCursorAdapter(getContext(), articles);
        lvItems.setAdapter(todoAdapter);
        return v;
    }
}

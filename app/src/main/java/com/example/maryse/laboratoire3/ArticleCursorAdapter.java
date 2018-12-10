package com.example.maryse.laboratoire3;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class ArticleCursorAdapter extends CursorAdapter {
    public ArticleCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.article_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvDescription = (TextView) view.findViewById(R.id.textViewDescription);
        TextView tvType = (TextView) view.findViewById(R.id.textViewType);
        TextView tvSaison = (TextView) view.findViewById(R.id.textViewSaison);
        TextView tvCategorie = (TextView) view.findViewById(R.id.textViewCategorie);
        TextView tvCouleur = (TextView) view.findViewById(R.id.textViewCouleurNom);

        String description = "description";
        String type = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.VETEMENT_COLUMN));
        String saison = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.SAISON_COLUMN));
        String categorie = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.CATEGORIE_COLUMN));
        String couleur = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COULEUR_COLUMN));

        tvDescription.setText(description);
        tvType.setText(type);
        tvSaison.setText(saison);
        tvCategorie.setText(categorie);
        tvCouleur.setText(couleur);
    }
}
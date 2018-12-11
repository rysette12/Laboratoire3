package com.example.maryse.laboratoire3;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
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
    public void bindView(View view, final Context context, final Cursor cursor) {
        TextView tvNom = (TextView) view.findViewById(R.id.textViewDescription);
        TextView tvType = (TextView) view.findViewById(R.id.textViewType);
        TextView tvSaison = (TextView) view.findViewById(R.id.textViewSaison);
        TextView tvCategorie = (TextView) view.findViewById(R.id.textViewCategorie);
        TextView tvCouleur = (TextView) view.findViewById(R.id.textViewCouleurNom);
        final ImageButton ibFavoris = (ImageButton)  view.findViewById(R.id.imageButtonFavoris);

        String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLONNE_DESCRIPTION));
        String idd = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLONNE_ID));
        String type = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLONNE_TYPE));
        String saison = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLONNE_COULEUR));
        String categorie = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLONNE_CATEGORIE));
        String couleur = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLONNE_SAISON));
        int favoris = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLONNE_FAVORIS));

        tvType.setText(type);
        tvSaison.setText(saison);
        tvCategorie.setText(categorie);
        tvCouleur.setText(couleur);

        if (favoris > 0)
            ibFavoris.setImageResource(R.drawable.ic_baseline_favorite_24px);
        else
            ibFavoris.setImageResource(R.drawable.ic_baseline_favorite_border_24px);
        ibFavoris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(context);
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLONNE_ID));
                if (ibFavoris.getDrawable().getConstantState().equals(context.getDrawable(R.drawable.ic_baseline_favorite_24px).getConstantState())) {
                    db.retirerDesFavoris(db.getWritableDatabase(), id);
                    ibFavoris.setImageResource(R.drawable.ic_baseline_favorite_border_24px);
                }
                else {
                    db.ajouterAuxFavoris(db.getWritableDatabase(), id);
                    ibFavoris.setImageResource(R.drawable.ic_baseline_favorite_24px);
                }

            }
        });
    }
}
package com.example.maryse.laboratoire3;

import android.content.Context;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.widget.ImageViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class ArticleCursorAdapter extends CursorAdapter {
    private boolean modeDelete;

    public ArticleCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    public ArticleCursorAdapter(Context context, Cursor cursor, boolean modeDelete) {
        this(context, cursor);
        this.modeDelete = modeDelete;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.article_list_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        TextView tvNom = (TextView) view.findViewById(R.id.textViewDescription);
        TextView tvSaison = (TextView) view.findViewById(R.id.textViewSaison);
        TextView tvType = (TextView) view.findViewById(R.id.textViewType);
        TextView tvCouleurNom = (TextView) view.findViewById(R.id.textViewCouleurNom);
        TextView tvCouleur = (TextView) view.findViewById(R.id.textViewCouleur);
        ImageView ivImage = (ImageView) view.findViewById(R.id.imageViewArticle);

        TextView tvCategorie = (TextView) view.findViewById(R.id.textViewCategorie);

        final ImageButton ibFavoris = (ImageButton)  view.findViewById(R.id.imageButtonFavoris);

        String nom = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLONNE_NOM));
        final String id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLONNE_ID));
        String type = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLONNE_TYPE));
        String saison = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLONNE_SAISON));
        String categorie = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLONNE_CATEGORIE));
        String couleurNom = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLONNE_COULEUR));
        int couleur = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLONNE_VALEUR));
        String image = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLONNE_IMAGE));
        int favoris = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLONNE_FAVORIS));

        tvType.setText(type);
        tvSaison.setText(saison);
        tvCategorie.setText(categorie);
        tvCouleur.setText(couleur);
        tvCouleur.setBackgroundColor(couleur);
        File imgFile = new  File(image);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ivImage.setImageBitmap(myBitmap);
        }

        if (favoris > 0)
            ibFavoris.setImageResource(R.drawable.ic_baseline_favorite_24px);
        else
            ibFavoris.setImageResource(R.drawable.ic_baseline_favorite_border_24px);
        ibFavoris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(context);
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLONNE_ID));
                if (ibFavoris.getDrawable().getConstantState().equals(
                        context.getDrawable(R.drawable.ic_baseline_favorite_24px).getConstantState())) {
                    db.retirerDesFavoris(db.getWritableDatabase(), id);
                    ibFavoris.setImageResource(R.drawable.ic_baseline_favorite_border_24px);
                }
                else {
                    db.ajouterAuxFavoris(db.getWritableDatabase(), id);
                    ibFavoris.setImageResource(R.drawable.ic_baseline_favorite_24px);
                }

            }
        });

        ImageButton ibPanier = (ImageButton) view.findViewById(R.id.imageButtonPanier);
        if (modeDelete) {
            ibPanier.setImageResource(R.drawable.ic_baseline_remove_24px);
            int rouge = context.getColor(android.R.color.holo_red_dark);
            ImageViewCompat.setImageTintList(ibPanier, ColorStateList.valueOf(rouge));
        }
        ibPanier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper bd = new DatabaseHelper(context);
                if (modeDelete) {
                    bd.retirerDuPanier(bd.getWritableDatabase(), id);
                } else
                    bd.ajouterDuPanier(bd.getWritableDatabase(), id);
            }
        });
    }
}
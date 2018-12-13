package com.example.maryse.laboratoire3;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class ExempleImage extends BaseActivity {

    // images
    static final int PRENDRE_PHOTO_INTENT = 1;
    static final int CHOISIR_PHOTO_INTENT = 2;
    private String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_add_photo);

        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            findViewById(R.id.buttonPrendrePhoto).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    prendrePhoto();
                }
            });
        } else
            findViewById(R.id.buttonPrendrePhoto).setVisibility(View.INVISIBLE);

        findViewById(R.id.buttonChoisirPhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choisirPhoto();
            }
        });
    }

    private void prendrePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File img = null;
            try {
                img = ImageHelper.createImageFile(this);
                currentPhotoPath = img.getAbsolutePath();
            } catch (IOException ex) {
                Toast.makeText(this, "Erreur: Veuillez recommencer.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (img != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        img);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, PRENDRE_PHOTO_INTENT);
            }
        }
    }

    private void choisirPhoto() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, CHOISIR_PHOTO_INTENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PRENDRE_PHOTO_INTENT && resultCode == RESULT_OK) {
            afficherImage(currentPhotoPath);
        } else if (requestCode == CHOISIR_PHOTO_INTENT && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            File copy = null;
            try {
                copy = ImageHelper.createImageFile(this);
                currentPhotoPath = copy.getAbsolutePath();
                ImageHelper.copyFile(this, imageUri, copy);
                afficherImage(currentPhotoPath);
            } catch (Exception ex) {
                if (copy != null)
                    copy.delete();
                Toast.makeText(this, "Erreur: Veuillez recommencer.", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    private void afficherImage(String imagePath) {
        Bitmap myBitmap = BitmapFactory.decodeFile(imagePath);
        ((ImageView) findViewById(R.id.imageViewPreview)).setImageBitmap(myBitmap);
    }

}

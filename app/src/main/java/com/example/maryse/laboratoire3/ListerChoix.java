package com.example.maryse.laboratoire3;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ListerChoix extends  BaseActivity{

    public Button creerGenre;
    DatabaseHelper db = new DatabaseHelper(getApplicationContext());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cart);
        final Cursor articles = db.listerCart(db.getReadableDatabase());
        ListView lvItems = (ListView) findViewById(R.id.listView);
        ArticleCursorAdapter todoAdapter = new ArticleCursorAdapter(this, articles);
        lvItems.setAdapter(todoAdapter);
        creerGenre = (Button) findViewById(R.id.creer_Ensemble);
        creerGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.ajouterEnsemble(db.getWritableDatabase(),articles);
                ajouter(articles);
            }
        });
    }
    public void ajouter(Cursor cursor){
        Toast.makeText(this, cursor.getCount() + " ensemble creer.", Toast.LENGTH_SHORT).show();
    }

    }




package com.example.maryse.laboratoire3;

import android.os.Bundle;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textview_layout);
        //DatabaseHelper db = new DatabaseHelper(this);
        //db.donnees(db.getWritableDatabase());
    }

}

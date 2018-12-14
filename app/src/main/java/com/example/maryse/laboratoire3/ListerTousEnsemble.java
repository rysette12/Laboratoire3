package com.example.maryse.laboratoire3;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class ListerTousEnsemble extends BaseActivity {
        private ListView maListViewPerso;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.listview_layout);
            maListViewPerso = (ListView) findViewById(R.id.listView);


        }
    }






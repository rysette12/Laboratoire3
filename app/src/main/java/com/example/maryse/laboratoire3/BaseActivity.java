package com.example.maryse.laboratoire3;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

/**
 * MainActivity.java        2018-12-01
 * Maryse Simard, Michelle Ngassa, Nhi Nguyen
 */

public abstract class BaseActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    private static final String SELECTED_ITEM_ID_KEY = "com.example.maryse.laboratoire3.SELECTED_ITEM_ID_KEY";
    public static final String ARTICLE_ID_KEY = "com.example.maryse.laboratoire3.ARTICLE_ID_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID)
    {
        super.setContentView(R.layout.activity_base);
        FrameLayout placeHolder = (FrameLayout) findViewById(R.id.flContent);
        getLayoutInflater().inflate(layoutResID, placeHolder);
        createDrawer();
        int itemId = getIntent().getIntExtra(SELECTED_ITEM_ID_KEY, -1);
        if (itemId != -1) {
            MenuItem item = navigationView.getMenu().findItem(itemId);
            item.setChecked(true);
        }
    }

    private void createDrawer () {
        drawerLayout = findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.ouvrir_desc, R.string.fermer_desc);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.isChecked())
                    return true;

                Intent i = null;
                switch(item.getItemId()) {
                    case R.id.navAccueil:
                        i = new Intent(getApplicationContext(), MainActivity.class);
                        break;
                    case R.id.navEnsemble:
                        i = new Intent(getApplicationContext(), MainActivity.class);
                        break;
                    case R.id.navAjouter:
                        i = new Intent(getApplicationContext(), MainActivity.class);
                        break;
                    case R.id.navEnsembles:
                        i = new Intent(getApplicationContext(), MainActivity.class);
                        break;
                    case R.id.navTous:
                        i = new Intent(getApplicationContext(), MainActivity.class);
                        break;
                    case R.id.navSaison:
                        i = new Intent(getApplicationContext(), ListerParSaisonActivity.class);
                        break;
                    case R.id.navType:
                        i = new Intent(getApplicationContext(), ListerParTypeActivity.class);
                        break;
                    case R.id.navCouleur:
                        i = new Intent(getApplicationContext(), ListerParCouleurActivity.class);
                        break;
                    case R.id.navCategorie:
                        i = new Intent(getApplicationContext(), ListerParCategorieActivity.class);
                        break;
                    case R.id.navFavoris:
                        i = new Intent(getApplicationContext(), ListerFavorisActivity.class);
                        break;
                    case R.id.navPlusRecent:
                        i = new Intent(getApplicationContext(), ListerPlusRecentActivity.class);
                        break;
                    case R.id.navPlusPorte:
                        i = new Intent(getApplicationContext(), ListerPlusPorteActivity.class);
                        break;
                    case R.id.navReglages:
                        i = new Intent(getApplicationContext(), ReglagesActivity.class);
                        break;
                    default:
                        i = new Intent(getApplicationContext(), MainActivity.class);
                }

                i.putExtra(SELECTED_ITEM_ID_KEY, item.getItemId());

                drawerLayout.closeDrawers();
                startActivity(i);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

}

package com.example.maryse.laboratoire3;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * MainActivity.java        2018-12-01
 * Maryse Simard, Michelle Ngassa, Nhi Nguyen
 */

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createDrawer();
    }

    private void createDrawer () {
        drawerLayout = findViewById(R.id.activity_main);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.ouvrir_desc, R.string.fermer_desc);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.isChecked()) {
                    item.setChecked(false);
                    return true;
                }

                Fragment fragment = null;
                Class fragmentClass = TestFragment.class;
                switch(item.getItemId()) {
                    case R.id.navEnsemble:
                        fragmentClass = TestFragment.class;
                        break;
                    case R.id.navAjouter:
                        fragmentClass = TestFragment.class;
                        break;
                    case R.id.navEnsembles:
                        fragmentClass = TestFragment.class;
                        break;
                    case R.id.navTous:
                        fragmentClass = TestFragment.class;
                        break;
                    case R.id.navSaison:
                        fragmentClass = TestFragment.class;
                        break;
                    case R.id.navType:
                        fragmentClass = TestFragment.class;
                        break;
                    case R.id.navCouleur:
                        fragmentClass = TestFragment.class;
                        break;
                    case R.id.navCategorie:
                        fragmentClass = TestFragment.class;
                        break;
                    case R.id.navFavoris:
                        fragmentClass = TestFragment.class;
                        break;
                    case R.id.navPlusRecent:
                        fragmentClass = TestFragment.class;
                        break;
                    case R.id.navPlusPorte:
                        fragmentClass = TestFragment.class;
                        break;
                    case R.id.navReglages:
                        fragmentClass = TestFragment.class;
                        break;
                }

                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

                clearCheckedItems(navigationView.getMenu());
                item.setChecked(true);
                setTitle(item.getTitle());

                drawerLayout.closeDrawers();
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

    private void clearCheckedItems(Menu menu){
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if(item.hasSubMenu()){
                clearCheckedItems(item.getSubMenu());
            }else{
                item.setChecked(false);
            }
        }
    }

}

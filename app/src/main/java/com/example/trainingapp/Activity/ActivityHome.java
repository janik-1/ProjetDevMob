package com.example.trainingapp.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.trainingapp.Activity.ActivityLocalStats;
import com.example.trainingapp.Activity.ActivityLocation;
import com.example.trainingapp.Activity.ActivityRemoteStats;
import com.example.trainingapp.Activity.ActivitySaveStats;
import com.example.trainingapp.Activity.ActivityTakePhoto;
import com.example.trainingapp.DataBase.MatchDatabaseHelper;
import com.example.trainingapp.R;
import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

public class ActivityHome extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // set up the toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set up the navigation drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(true);

        toggle.syncState();

        // handle navigation view item clicks
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_local_stats) {
                    // launch the local statistics page
                    startActivity(new Intent(ActivityHome.this, ActivityLocalStats.class));
                } else if (id == R.id.nav_remote_stats) {
                    // launch the remote statistics page
                    startActivity(new Intent(ActivityHome.this, ActivityRemoteStats.class));
                } else if (id == R.id.nav_location) {
                    // launch the location page
                    startActivity(new Intent(ActivityHome.this, ActivityLocation.class));
                } else if (id == R.id.nav_take_photo) {
                    // launch the take photo page
                    startActivity(new Intent(ActivityHome.this, ActivityTakePhoto.class));
                } else if (id == R.id.nav_language) {
                    // get the current language
                    String currentLanguage = getResources().getConfiguration().locale.getLanguage();

                    // change the language
                    if (currentLanguage.equals("fr")) {
                        setLocale("en");
                    } else {
                        setLocale("fr");
                    }

                    // restart the activity
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);

                } else if (id == R.id.nav_save_stats) {
                    // launch the about page
                    startActivity(new Intent(ActivityHome.this, ActivitySaveStats.class));
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    // change the locale of the app
    public void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }
}

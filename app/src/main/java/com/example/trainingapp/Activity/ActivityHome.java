package com.example.trainingapp.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.trainingapp.DataBase.MatchDatabaseHelper;
import com.example.trainingapp.R;
import com.google.android.material.navigation.NavigationView;

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

      //  set up the navigation drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

       ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
              R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(true);

        toggle.syncState();

        //handle navigation view item clicks
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
                } else if (id == R.id.nav_about) {
                    // launch the about page
                    startActivity(new Intent(ActivityHome.this, ActivityAbout.class));
                } else if (id == R.id.nav_save_stats) {
                    // launch the about page
                    startActivity(new Intent(ActivityHome.this,ActivitySaveStats.class));
                }

                DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
}

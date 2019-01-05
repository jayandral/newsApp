package com.example.jayand_zuch569.news;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class Dashboard extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        bottomNavigationView = findViewById(R.id.navigation);

        final FragmentManager fragmentManager = getSupportFragmentManager();

        final Fragment fragment1 = new Feeds();
        final Fragment fragment2 = new Settings();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment1).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frame, fragment1).commit();
                            return true;
                        case R.id.nav_settings:
                            FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
                            fragmentTransaction1.replace(R.id.frame, fragment2).commit();
                            return true;
                    }
                    return true;
                }
            });
    }
}

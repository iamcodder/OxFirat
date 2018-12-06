package com.example.iamcodder.androidd;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.iamcodder.androidd.duyurular.fragment_duyurular;
import com.example.iamcodder.androidd.etkinlik.fragment_etkinlik;
import com.example.iamcodder.androidd.haberler.fragment_haberler;
import com.example.iamcodder.androidd.yemekhane.fragment_yemekhane;


public class MainActivity extends AppCompatActivity {

    public static String FIRAT_WEB = "http://www.firat.edu.tr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();


        BottomNavigationView bottomNavigationView = findViewById(R.id.main_activity_bottom_navigation_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(onListener);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_activity_frameLayout, new fragment_haberler())
                .commit();


    }


    private BottomNavigationView.OnNavigationItemSelectedListener onListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    Fragment fragment ;

                    switch (menuItem.getItemId()) {

                        case R.id.menu_item_haberler:
                            fragment = new fragment_haberler();
                            break;

                        case R.id.menu_item_duyuru:
                            fragment = new fragment_duyurular();
                            break;
                        case R.id.menu_item_etkinlik:
                            fragment = new fragment_etkinlik();
                            break;
                        case R.id.menu_item_yemekhane:
                            fragment = new fragment_yemekhane();
                            break;
                        default:
                            fragment = new fragment_haberler();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_activity_frameLayout, fragment)
                            .commit();

                    return true;
                }
            };


}

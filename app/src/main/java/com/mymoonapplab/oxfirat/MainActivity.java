package com.mymoonapplab.oxfirat;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.mymoonapplab.oxfirat.duyurular.fragment_duyurular;
import com.mymoonapplab.oxfirat.etkinlik.fragment_etkinlik;
import com.mymoonapplab.oxfirat.haberler.fragment_haberler;
import com.mymoonapplab.oxfirat.yemekhane.fragment_yemekhane;

import com.google.android.gms.ads.MobileAds;


public class MainActivity extends AppCompatActivity {

    public static String FIRAT_WEB = "http://www.firat.edu.tr";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!InternetKontrol()){
            Toast.makeText(this,"LÜTFEN İNTERNETİNİZİ AÇIN",Toast.LENGTH_LONG).show();
        }

        MobileAds.initialize(this, "ca-app-pub-1818679104699845~1785629318");

        //Toolbar ekleme
        Toolbar toolbar = findViewById(R.id.fragment_tutucu_toolBar);
        setSupportActionBar(toolbar);


        BottomNavigationView bottomNavigationView = findViewById(R.id.main_activity_bottom_navigation_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(onListener);

        load_fragment(new fragment_haberler());

    }

    public boolean InternetKontrol() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager.getActiveNetworkInfo() != null &&
                manager.getActiveNetworkInfo().isAvailable() &&
                manager.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    private void load_fragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_tutucu_frameLayout,fragment)
                .commit();
    }


    BottomNavigationView.OnNavigationItemSelectedListener onListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    Fragment fragment=null;

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
                    load_fragment(fragment);
                    return true;
                }
            };

}

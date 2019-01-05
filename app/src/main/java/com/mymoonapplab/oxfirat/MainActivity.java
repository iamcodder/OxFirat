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
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mymoonapplab.oxfirat.duyurular.fragment_duyurular;
import com.mymoonapplab.oxfirat.etkinlik.fragment_etkinlik;
import com.mymoonapplab.oxfirat.haberler.fragment_haberler;
import com.mymoonapplab.oxfirat.yemekhane.fragment_yemekhane;

import com.google.android.gms.ads.MobileAds;


public class MainActivity extends AppCompatActivity {

    public static String FIRAT_WEB = "http://www.firat.edu.tr";

    private AdView mAdView;
    private AdRequest adRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reklamlar();

        if(!InternetKontrol()){
            Toast.makeText(this,"LÜTFEN İNTERNETİNİZİ AÇIN",Toast.LENGTH_LONG).show();
        }

        //Toolbar ekleme
        Toolbar toolbar = findViewById(R.id.fragment_tutucu_toolBar);
        setSupportActionBar(toolbar);


        BottomNavigationView bottomNavigationView = findViewById(R.id.main_activity_bottom_navigation_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(onListener);

        load_fragment(new fragment_haberler());

    }
    private void reklamlar(){

        MobileAds.initialize(this, "ca-app-pub-1818679104699845~1785629318");
        mAdView = findViewById(R.id.adView);
        adRequest = new AdRequest.Builder().addTestDevice("814B87088029BD05B980A3DB14E3ABF2").build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded() {
                mAdView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                reklamlar();
            }

            @Override
            public void onAdOpened() {
            }

            @Override
            public void onAdLeftApplication() {
            }

            @Override
            public void onAdClosed() {
                reklamlar();
            }
        });
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
                            mAdView.setVisibility(View.INVISIBLE);
                            break;
                        case R.id.menu_item_duyuru:
                            fragment = new fragment_duyurular();
                            mAdView.setVisibility(View.INVISIBLE);
                            break;
                        case R.id.menu_item_etkinlik:
                            fragment = new fragment_etkinlik();
                            mAdView.setVisibility(View.INVISIBLE);
                            break;
                        case R.id.menu_item_yemekhane:
                            fragment = new fragment_yemekhane();
                            mAdView.setVisibility(View.VISIBLE);
                            break;
                    }
                    load_fragment(fragment);
                    return true;
                }
            };

}

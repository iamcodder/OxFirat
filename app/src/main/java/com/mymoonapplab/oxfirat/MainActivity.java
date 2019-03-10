package com.mymoonapplab.oxfirat;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.mymoonapplab.oxfirat.duyurular.fragment_duyurular;
import com.mymoonapplab.oxfirat.etkinlik.fragment_etkinlik;
import com.mymoonapplab.oxfirat.haberler.fragment_haberler;
import com.mymoonapplab.oxfirat.yemekhane.fragment_yemekhane;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class MainActivity extends AppCompatActivity {


    private AdView mAdView;
    private AdRequest adRequest;
    private Resources res;

    private Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        res = getResources();
        reklamlar();


        if (!InternetKontrol()) {
            Toast.makeText(this, res.getString(R.string.internet_acin), Toast.LENGTH_LONG).show();
        }


        MeowBottomNavigation bottomNavigation = findViewById(R.id.main_activity_bottom_navigation_bar);
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.newspaper));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.bullhorn));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.account_group));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_restaurant_menu_black_24dp));


        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {

                switch (model.getId()) {

                    case 1:
                        fragment = new fragment_haberler();
                        mAdView.setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        fragment = new fragment_duyurular();
                        mAdView.setVisibility(View.INVISIBLE);
                        break;
                    case 3:
                        fragment = new fragment_etkinlik();
                        mAdView.setVisibility(View.INVISIBLE);
                        break;
                    case 4:
                        fragment = new fragment_yemekhane();
                        mAdView.setVisibility(View.VISIBLE);
                        break;
                }
                load_fragment(fragment);
                return null;
            }
        });

        load_fragment(new fragment_haberler());

    }

    private void reklamlar() {

        MobileAds.initialize(this, res.getString(R.string.Mobile_ads_code));
        mAdView = findViewById(R.id.adView);
        adRequest = new AdRequest.Builder().addTestDevice(res.getString(R.string.Test_device)).build();
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

    private void load_fragment(Fragment fragment) {


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_tutucu_frameLayout, fragment)
                .commit();
    }


}

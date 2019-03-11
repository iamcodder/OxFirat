package com.mymoonapplab.oxfirat;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.mymoonapplab.oxfirat.duyurular.fragment_duyurular;
import com.mymoonapplab.oxfirat.etkinlik.fragment_etkinlik;
import com.mymoonapplab.oxfirat.haberler.fragment_haberler;
import com.mymoonapplab.oxfirat.yemekhane.fragment_yemekhane;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class MainActivity extends AppCompatActivity {


    private Resources res;

    private Fragment fragment = null;

    private LottieAnimationView no_internet_animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        res = getResources();


        if (!InternetKontrol()) {
            Toast.makeText(this, res.getString(R.string.internet_acin), Toast.LENGTH_LONG).show();

        }


        MeowBottomNavigation bottomNavigation = findViewById(R.id.main_activity_bottom_navigation_bar);
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.newspaper));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.bullhorn));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.account_group));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_restaurant_menu_black_24dp));

        bottomNavigation.show(1,true);

        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {

                switch (model.getId()) {

                    case 1:
                        fragment = new fragment_haberler();
                        break;
                    case 2:
                        fragment = new fragment_duyurular();
                        break;
                    case 3:
                        fragment = new fragment_etkinlik();
                        break;
                    case 4:
                        fragment = new fragment_yemekhane();
                        break;
                }
                load_fragment(fragment);
                return null;
            }
        });

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

    private void load_fragment(Fragment fragment) {


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_tutucu_frameLayout, fragment)
                .commit();
    }


}

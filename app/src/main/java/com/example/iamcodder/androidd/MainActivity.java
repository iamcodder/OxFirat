package com.example.iamcodder.androidd;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.iamcodder.androidd.duyurular.fragment_duyurular;
import com.example.iamcodder.androidd.etkinlik.fragment_etkinlik;
import com.example.iamcodder.androidd.haberler.fragment_haberler;
import com.example.iamcodder.androidd.yemekhane.fragment_yemekhane;

import softpro.naseemali.ShapedNavigationView;
import softpro.naseemali.ShapedViewSettings;


public class MainActivity extends AppCompatActivity {

    public static String FIRAT_WEB = "http://www.firat.edu.tr";


    private ShapedNavigationView left_navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        left_navigationView=findViewById(R.id.main_activity_navigationView);
        left_navigationView.getSettings().setShapeType(ShapedViewSettings.BOTTOM_ROUND);
        left_navigationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.findViewById(R.id.left_hosgeldiniz)==v.getTag()){
                    Toast.makeText(getApplicationContext(),"SA",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Toolbar ekleme
        Toolbar toolbar = findViewById(R.id.fragment_tutucu_toolBar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setLogo(R.drawable.firatlogo);
        toolbar.hideOverflowMenu();

        BottomNavigationView bottomNavigationView = findViewById(R.id.main_activity_bottom_navigation_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(onListener);

        load_fragment(new fragment_haberler());


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

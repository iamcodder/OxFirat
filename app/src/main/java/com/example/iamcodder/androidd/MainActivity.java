package com.example.iamcodder.androidd;


import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!internetBaglantisiKontrolu()){
            Toast.makeText(getApplicationContext(),"İnternetinizi Açın",Toast.LENGTH_LONG).show();
        }

        BottomNavigationView bottomNavigationView=findViewById(R.id.main_activity_bottom_navigation_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(onListener);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_activity_frameLayout,new fragment_bilgisayar_muhendisligi())
                .commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    Fragment fragment=null;

                    switch (menuItem.getItemId()){
                        case R.id.menu_item_bilgisayar:
                            fragment=new fragment_bilgisayar_muhendisligi();
                            break;
                        case R.id.menu_item_yemekhane:
                            fragment=new fragment_yemekhane();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_activity_frameLayout,fragment)
                            .commit();

                    return true;
                }
            };

    private boolean internetBaglantisiKontrolu(){

        ConnectivityManager manager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if(manager.getActiveNetworkInfo()!=null){
            return true;
        }
        else{
            return false;
        }
    }


}

package com.mymoonapplab.oxfirat.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;
import com.mymoonapplab.oxfirat.R;
import com.mymoonapplab.oxfirat.broadcast_receiver.NetworkChangeReceiver;
import com.mymoonapplab.oxfirat.constant.statik_class;
import com.mymoonapplab.oxfirat.fragment.fragment_akademik_takvim;
import com.mymoonapplab.oxfirat.fragment.fragment_duyurular;
import com.mymoonapplab.oxfirat.fragment.fragment_etkinlik;
import com.mymoonapplab.oxfirat.fragment.fragment_haberler;
import com.mymoonapplab.oxfirat.fragment.fragment_telefon_genel;
import com.mymoonapplab.oxfirat.fragment.fragment_yemek;
import com.mymoonapplab.oxfirat.interfacee.interface_receiver_network;
import com.mymoonapplab.oxfirat.model.model_menu;
import com.mymoonapplab.oxfirat.navigationMenu.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HomeActivity extends AppCompatActivity implements interface_receiver_network {

    private BottomNavigationView bottomBar;
    private Fragment frag_ekranda_gozuken, fragment;
    private String str_tag;
    private boolean is_first_time, is_connected_network = false;

    private DrawerLayout drawerLayout;

    ExpandableListView expandableListView;
    List<model_menu> list_parent = new ArrayList<>();
    HashMap<model_menu, List<model_menu>> list_child = new HashMap<>();

    private NetworkChangeReceiver receiver;//Network dinleyen receiver objemizin referans˝


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup();

        expandable_listview();
        ExpandableListAdapter expandableListAdapter = new ExpandableListAdapter(this, list_parent, list_child);
        expandableListView.setAdapter(expandableListAdapter);

        run();

    }


    private void setup() {

        //Receiverımızı register ediyoruz
        //Yani Çalıştırıyoruz
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver(this);
        registerReceiver(receiver, filter);

        is_first_time = false;

        Toolbar toolbar = findViewById(R.id.app_bar_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.activity_main_drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        bottomBar = findViewById(R.id.content_main_bottom_navigation_view);
        expandableListView = findViewById(R.id.main_expandable);


    }

    private void run() {

        bottomBar.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.bottom_bar_haberler) {
                    fragment = new fragment_haberler();
                    str_tag = "frag_haber";
                } else if (itemId == R.id.bottom_bar_duyurlar) {
                    fragment = new fragment_duyurular();
                    str_tag = "frag_duyuru";
                } else if (itemId == R.id.bottom_bar_etkinlikler) {
                    fragment = new fragment_etkinlik();
                    str_tag = "frag_etkinlik";
                } else if (itemId == R.id.bottom_bar_yemekhane) {
                    fragment = new fragment_yemek();
                    str_tag = "frag_yemek";
                }
                fragment_change(fragment, str_tag);
                return true;
            }
        });


        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Log.d("SÜlo", String.valueOf(groupPosition));

                if (groupPosition == 0) {
                    fragment = new fragment_akademik_takvim();
                    str_tag = "akademik";

                    statik_class.PDF_ISMI = "akademiktakvim_" + (childPosition + 1);

                    fragment_change(fragment, str_tag);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (groupPosition == 1) {

                    fragment = new fragment_telefon_genel();
                    str_tag = "telefon_numaralari";

                    statik_class.MENU_NUMARA = "" + childPosition;

                    fragment_change(fragment, str_tag);
                    drawerLayout.closeDrawer(GravityCompat.START);

                }

                return false;
            }
        });


    }

    private void fragment_change(Fragment frag_new, String fragment_tag) {

        if (this.is_connected_network) {
            //frag_x: stackte o tag ile ekli fragment oluo olmadığını tutuyor.
            Fragment frag_x = getSupportFragmentManager().findFragmentByTag(fragment_tag);
            FragmentTransaction tr = getSupportFragmentManager().beginTransaction();

            //conteiner boş
            if (!is_first_time) {
                tr.add(R.id.content_main_frame_layout, frag_new, fragment_tag);
                is_first_time = true;
                frag_ekranda_gozuken = frag_new;

            } else if (fragment_tag.equals("akademik") && frag_x != null) {

                frag_x.onDestroy();
                tr.add(R.id.content_main_frame_layout, frag_new, fragment_tag)
                        .hide(this.frag_ekranda_gozuken);
                frag_ekranda_gozuken = frag_new;

            } else if (fragment_tag.equals("telefon_numaralari") && frag_x != null) {

                frag_x.onDestroy();
                tr.add(R.id.content_main_frame_layout, frag_new, fragment_tag)
                        .hide(this.frag_ekranda_gozuken);
                frag_ekranda_gozuken = frag_new;

            } else if (frag_x != null && frag_x.isAdded()) {

                tr.hide(this.frag_ekranda_gozuken).show(frag_x);
                frag_ekranda_gozuken = frag_x;

            } else {

                tr.add(R.id.content_main_frame_layout, frag_new, fragment_tag)
                        .hide(this.frag_ekranda_gozuken);

                frag_ekranda_gozuken = frag_new;
            }

            tr.commit();
        }

    }


    private void expandable_listview() {

        List<model_menu> childModelsList = new ArrayList<>();

        model_menu menuModel = new model_menu("Akademik Takvim", true, true);
        list_parent.add(menuModel);

        childModelsList.add(new model_menu("1- LİSANS/ ÖNLİSANS GENEL AKADEMİK_TAKVİMİ", false, false));
        childModelsList.add(new model_menu("2- TIP FAKÜLTESİ AKADEMİK TAKVİMİ", false, false));
        childModelsList.add(new model_menu("3- DİŞ HEKİMLİĞİ FAKÜLTESİ AKADEMİK TAKVİMİ", false, false));
        childModelsList.add(new model_menu("4- 2019 YAZ OKULU AKADEMİK TAKVİMİ", false, false));
        childModelsList.add(new model_menu("5- 2020 YAZ OKULU AKADEMİK TAKVİMİ", false, false));
        childModelsList.add(new model_menu("6- LİSANSÜSTÜ AKADEMİK TAKVİMİ", false, false));
        childModelsList.add(new model_menu("7- KURUM İÇİ YATAY GEÇİŞ (ÖNLİSANS- LİSANS) BAŞVURU VE KABUL TAKVİMİ", false, false));
        childModelsList.add(new model_menu("8- KURUMLARARASI YATAY GEÇİŞ (ÖNLİSANS-LİSANS) BAŞVURU VE KABUL TAKVİMİ", false, false));
        childModelsList.add(new model_menu("9- MERKEZİ YERLEŞTİRME PUANI (ÖSYM) İLE YATAY GEÇİŞ", false, false));
        childModelsList.add(new model_menu("10- ÇİFT ANADAL VE YANDAL PROGRAMI BAŞVURU VE KABUL TAKVİMİ", false, false));
        childModelsList.add(new model_menu("11- ÖZEL ÖĞRENCİ (ÖNLİSANS-LİSANS) BAŞVURU VE KABUL TAKVİMİ", false, false));
        childModelsList.add(new model_menu("12- EK SINAV TAKVİMİ", false, false));
        childModelsList.add(new model_menu("13- RESMİ TATİLLER", false, false));

        list_child.put(menuModel, childModelsList);


        model_menu new_menu = new model_menu("Telefon Numaraları", true, true);
        list_parent.add(new_menu);

        List<model_menu> child_model_list = new ArrayList<>();
        child_model_list.add(new model_menu("Genel", false, false));
        child_model_list.add(new model_menu("Fakülte,MYO ve Enstitü", false, false));

        list_child.put(new_menu, child_model_list);

    }

    @Override
    public void is_connected(boolean connected) {
        if (connected) {
            this.is_connected_network = true;
            fragment = new fragment_yemek();
            str_tag = "frag_yemek";
            fragment_change(fragment, str_tag);

            fragment = new fragment_haberler();
            str_tag = "frag_haber";
            fragment_change(fragment, str_tag);

        } else {
            Toast.makeText(this, "İnternet bağlantısını kontrol edin", Toast.LENGTH_SHORT).show();
            this.is_connected_network = false;

        }
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

}
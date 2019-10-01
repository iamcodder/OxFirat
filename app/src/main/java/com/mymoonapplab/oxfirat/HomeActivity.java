package com.mymoonapplab.oxfirat;

import android.os.Bundle;
import android.view.View;

import com.gauravk.bubblenavigation.BubbleNavigationConstraintView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.mymoonapplab.oxfirat.duyurular.fragment_duyurular;
import com.mymoonapplab.oxfirat.etkinlik.fragment_etkinlik;
import com.mymoonapplab.oxfirat.haberler.fragment_haberler;
import com.mymoonapplab.oxfirat.yemekhane.fragment_yemek;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class HomeActivity extends AppCompatActivity {

    private BubbleNavigationConstraintView bottomBar;
    private Fragment frag_ekranda_gozuken,fragment;
    private String str_tag;
    private boolean is_first_time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        is_first_time=false;

        fragment = new fragment_haberler();
        str_tag = "frag_haber";
        fragment_change(fragment, str_tag);

        bottomBar = findViewById(R.id.bottom_navigation_view);
        bottomBar.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                switch (position) {
                    case 0:
                        fragment = new fragment_haberler();
                        str_tag = "frag_haber";
                        break;
                    case 1:
                        fragment= new fragment_duyurular();
                        str_tag = "frag_duyuru";
                        break;
                    case 2:
                        fragment = new fragment_etkinlik();
                        str_tag = "frag_etkinlik";
                        break;
                    case 3:
                        fragment = new fragment_yemek();
                        str_tag = "frag_yemek";
                        break;
                }
                fragment_change(fragment, str_tag);

            }
        });
    }

    private void fragment_change(Fragment frag_new, String fragment_tag) {

        //frag_x: stackte o tag ile ekli fragment oluo olmadığını tutuyor.
        Fragment frag_x=getSupportFragmentManager().findFragmentByTag(fragment_tag);
        FragmentTransaction tr=getSupportFragmentManager().beginTransaction();

        //conteiner boş
        if(!is_first_time){
            tr.add(R.id.fragment_tutucu_frameLayout,frag_new,fragment_tag);
            is_first_time=true;
            frag_ekranda_gozuken=frag_new;
        }

        else if (frag_x != null && frag_x.isAdded()) {

            tr.hide(this.frag_ekranda_gozuken).show(frag_x);
            frag_ekranda_gozuken=frag_x;

        }

        else {

            tr.add(R.id.fragment_tutucu_frameLayout,frag_new,fragment_tag)
                    .hide(this.frag_ekranda_gozuken);
            frag_ekranda_gozuken=frag_new;
        }

        tr.commit();
    }
}

package com.mymoonapplab.oxfirat.etkinlik;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.mymoonapplab.oxfirat.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class fragment_etkinlik extends Fragment implements interface_etkinlik {

    private ArrayList<String> list_etkinlik_tarih,list_etkinlik_baslik,list_etkinlik_link;
    private RecyclerView recyclerView;
    private fragment_etkinlik_adapter adapter;
    private ProgressBar progressBar;

    private int son_etkinlik_konumu;

    private View rootView;

    public static int sayfa_sayisi;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_etkinlik, container, false);

        list_etkinlik_tarih = new ArrayList<>();
        list_etkinlik_link = new ArrayList<>();
        list_etkinlik_baslik = new ArrayList<>();
        sayfa_sayisi = 1;

        progressBar=rootView.findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);

        gorev_calistir();

        recycler_islemleri();

        return rootView;
    }


    private void recycler_islemleri() {
        recyclerView = rootView.findViewById(R.id.fragment_etkinlik_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();

                int toplam_etkinlik_sayisi = 0;
                son_etkinlik_konumu = 0;

                if (manager != null) {
                    toplam_etkinlik_sayisi = manager.getItemCount();
                    son_etkinlik_konumu = manager.findLastVisibleItemPosition();
                }

                if (son_etkinlik_konumu + 1 == toplam_etkinlik_sayisi) {
                    gorev_calistir();
                }


            }
        });
    }


    private void gorev_calistir() {
        new async_etkinlik(this).execute(getResources().getString(R.string.etkinlik_sitesi),
                getResources().getString(R.string.okul_sitesi));
        progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void etkinlik_bilgisi_aktarimi(ArrayList<String> etkinlik_basligi, ArrayList<String> etkinlik_tarihi, ArrayList<String> etkinlik_linki) {

        list_etkinlik_baslik.addAll(etkinlik_basligi);
        list_etkinlik_tarih.addAll(etkinlik_tarihi);
        list_etkinlik_link.addAll(etkinlik_tarihi);

        adapter=new fragment_etkinlik_adapter(getContext(),etkinlik_tarihi,etkinlik_basligi,etkinlik_linki,getFragmentManager());
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(son_etkinlik_konumu - 2);
        recyclerView.scheduleLayoutAnimation();
        sayfa_sayisi++;
        progressBar.setVisibility(View.INVISIBLE);
    }
}

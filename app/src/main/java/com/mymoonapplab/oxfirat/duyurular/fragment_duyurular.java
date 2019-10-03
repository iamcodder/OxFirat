package com.mymoonapplab.oxfirat.duyurular;


import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mymoonapplab.oxfirat.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class fragment_duyurular extends Fragment implements interface_duyurular {

    private RecyclerView recyclerView;
    private fragment_duyurular_adapter adapter;
    private int son_duyuru_konumu;
    private View rootView;
    private ProgressBar progressBar;

    private ArrayList<String> list_duyuru_linki, list_duyuru_basligi, list_duyuru_tarihi;
    public static int sayfa_sayisi;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_duyurular, container, false);

        list_duyuru_linki = new ArrayList<>();
        list_duyuru_basligi = new ArrayList<>();
        list_duyuru_tarihi = new ArrayList<>();
        sayfa_sayisi = 1;

        progressBar=rootView.findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);

        gorev_calistir();

        recyler_islemleri();

        return rootView;
    }

    private void recyler_islemleri(){
        recyclerView = rootView.findViewById(R.id.fragment_duyurular_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

                son_duyuru_konumu = 0;
                int toplam_duyuru = 0;

                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (manager != null) {

                    son_duyuru_konumu = manager.findLastVisibleItemPosition();
                    toplam_duyuru = manager.getItemCount();
                }

                if (son_duyuru_konumu + 1 == toplam_duyuru) {
                    gorev_calistir();
                }

            }
        });
    }


    private void gorev_calistir() {
        new async_duyuru(this).execute(getResources().getString(R.string.okul_sitesi),
                getResources().getString(R.string.duyurular_sitesi));
        progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void duyuru_bilgisi_aktarimi(ArrayList<String> duyuru_basligi, ArrayList<String> duyuru_tarihi, ArrayList<String> duyuru_linki) {

        list_duyuru_basligi.addAll(duyuru_basligi);
        list_duyuru_tarihi.addAll(duyuru_tarihi);
        list_duyuru_linki.addAll(duyuru_linki);

        adapter = new fragment_duyurular_adapter(getContext(), list_duyuru_basligi, list_duyuru_tarihi, list_duyuru_linki, getFragmentManager());
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(son_duyuru_konumu - 2);
        recyclerView.scheduleLayoutAnimation();
        sayfa_sayisi++;
        progressBar.setVisibility(View.INVISIBLE);
    }




}

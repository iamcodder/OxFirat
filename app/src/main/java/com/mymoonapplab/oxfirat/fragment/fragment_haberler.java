package com.mymoonapplab.oxfirat.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.mymoonapplab.oxfirat.R;
import com.mymoonapplab.oxfirat.async_task.async_haberler;
import com.mymoonapplab.oxfirat.adapter.fragment_haberler_adapter;
import com.mymoonapplab.oxfirat.interfacee.interface_haberler;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class fragment_haberler extends Fragment implements interface_haberler {


    private com.mymoonapplab.oxfirat.adapter.fragment_haberler_adapter fragment_haberler_adapter;
    private RecyclerView recyclerView;
    private int son_haber_konumu;
    private View rootView;
    private ProgressBar progressBar;

    private ArrayList<String> list_haber_basligi,list_haber_linki,list_haber_resmi;

    public static int sayi_sayfa;
    private SwipeRefreshLayout mPullToRefresh;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_haberler, container, false);

        list_haber_basligi=new ArrayList<>();
        list_haber_linki=new ArrayList<>();
        list_haber_resmi=new ArrayList<>();
        sayi_sayfa=1;

        progressBar=rootView.findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);

        gorev_calistir();

        recycler_islevleri();

        mPullToRefresh = rootView.findViewById(R.id.pullToRefresh);

        mPullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                gorev_calistir();
                Toast.makeText(getContext(),"Güncelleniyor",Toast.LENGTH_SHORT).show();
            }
        });



        return rootView;
    }


    private void recycler_islevleri(){
        recyclerView = rootView.findViewById(R.id.fragment_haberler_recycleview);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

                int toplam_haber_sayisi = 0;
                son_haber_konumu = 0;

                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (manager != null) {
                    toplam_haber_sayisi = manager.getItemCount();
                    son_haber_konumu = manager.findLastVisibleItemPosition();
                }

                if (son_haber_konumu + 1 == toplam_haber_sayisi) {
                    gorev_calistir();

                }
            }

        });
    }

    public void gorev_calistir(){
        new async_haberler(this,getContext()).execute(getResources().getString(R.string.okul_sitesi),
                getResources().getString(R.string.haber_sitesi));


        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void haber_bilgisi_aktarimi(ArrayList<String> haber_basligi, ArrayList<String> haber_resmi, ArrayList<String> haber_linki) {

        list_haber_resmi.addAll(haber_resmi);
        list_haber_linki.addAll(haber_linki);
        list_haber_basligi.addAll(haber_basligi);

        fragment_haberler_adapter = new fragment_haberler_adapter(getContext(), list_haber_basligi, list_haber_resmi, list_haber_linki, getFragmentManager());
        recyclerView.setAdapter(fragment_haberler_adapter);
        recyclerView.scrollToPosition(son_haber_konumu - 2);
        recyclerView.scheduleLayoutAnimation();
        sayi_sayfa++;
        progressBar.setVisibility(View.INVISIBLE);


        mPullToRefresh.setRefreshing(false);

    }


}

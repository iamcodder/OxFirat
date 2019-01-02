package com.mymoonapplab.oxfirat.haberler;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.mymoonapplab.oxfirat.MainActivity;
import com.mymoonapplab.oxfirat.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;


public class fragment_haberler extends Fragment {


    private ArrayList<String> haberBasligi, haberResmi;
    public static ArrayList<String> haberLinki;
    private adapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar bar;

    private WaveSwipeRefreshLayout swipeRefreshLayout;

    private int page_number;

    private int son_haber_konumu;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_haberler, container, false);

        haberBasligi = new ArrayList<>();
        haberLinki = new ArrayList<>();
        haberResmi = new ArrayList<>();

        page_number = 1;

        new haberCek().execute();

        bar = rootView.findViewById(R.id.fragment_haberler_progressBar);

        recyclerView = rootView.findViewById(R.id.fragment_haberler_recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

                int toplam_haber_sayisi = 0;
                son_haber_konumu=0;

                LinearLayoutManager manager = LinearLayoutManager.class.cast(recyclerView.getLayoutManager());

                if (manager != null) {
                    toplam_haber_sayisi = manager.getItemCount();
                    son_haber_konumu = manager.findLastVisibleItemPosition();
                }

                if(son_haber_konumu+1==toplam_haber_sayisi){
                    new haberCek().execute();
                    bar.setVisibility(View.VISIBLE);

                }
            }

        });


        swipeRefreshLayout = rootView.findViewById(R.id.fragment_haberler_waveswipe);
        swipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new haberCek().execute();
            }
        });


        return rootView;
    }


    @SuppressLint("StaticFieldLeak")
    public class haberCek extends AsyncTask<Void, Void, Void> {

        private Elements haber1;

        @Override
        protected Void doInBackground(Void... voids) {

            try {

                Document document = Jsoup.connect("http://www.firat.edu.tr/tr/haberler?page=" + page_number).get();
                haber1 = document.select("div[class=row all-news]").select("div[class=banner col-xs-12 col-sm-4 col-md-4 col-lg-3]");
                page_number++;

                for (int i = 0; i < haber1.size(); i++) {

                    haberBasligi.add(haber1.get(i).select("div[class=top]").text());
                    haberLinki.add(MainActivity.FIRAT_WEB + haber1.get(i).select("a").attr("href"));

                    String parcalama[] = haber1.get(i).select("div").attr("style").split("'");

                    haberResmi.add(parcalama[1]);

                }


            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            bar.setVisibility(View.INVISIBLE);


            adapter = new adapter(getContext(), haberBasligi, haberResmi, haberLinki, getFragmentManager());
            recyclerView.setAdapter(adapter);
            recyclerView.scrollToPosition(son_haber_konumu-1);

            swipeRefreshLayout.setRefreshing(false);


        }
    }
}

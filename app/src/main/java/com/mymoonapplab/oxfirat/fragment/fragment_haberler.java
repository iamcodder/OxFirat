package com.mymoonapplab.oxfirat.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.mymoonapplab.oxfirat.R;
import com.mymoonapplab.oxfirat.adapter.fragment_haberler_adapter;
import com.mymoonapplab.oxfirat.model.ApiResponse;
import com.mymoonapplab.oxfirat.model.News;
import com.mymoonapplab.oxfirat.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class fragment_haberler extends Fragment {

    private static final String TAG = "fragment_haberler";
    private com.mymoonapplab.oxfirat.adapter.fragment_haberler_adapter fragment_haberler_adapter;
    private RecyclerView recyclerView;
    private int son_haber_konumu;
    private View rootView;
    private ProgressBar progressBar;

    private ArrayList<News> newsList;
    private int currentOffset = 0;
    private final int LIMIT = 10;
    private boolean isLoading = false;

    private SwipeRefreshLayout mPullToRefresh;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_haberler, container, false);

        newsList = new ArrayList<>();
        currentOffset = 0;

        progressBar = rootView.findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);

        recycler_islevleri();

        loadNews();

        mPullToRefresh = rootView.findViewById(R.id.pullToRefresh);

        mPullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshNews();
            }
        });

        return rootView;
    }


    private void recycler_islevleri(){
        recyclerView = rootView.findViewById(R.id.fragment_haberler_recycleview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                int toplam_haber_sayisi = layoutManager.getItemCount();
                son_haber_konumu = layoutManager.findLastVisibleItemPosition();

                if (!isLoading && son_haber_konumu + 1 == toplam_haber_sayisi) {
                    loadNews();
                }
            }
        });
    }

    private void loadNews() {
        if (isLoading) return;

        isLoading = true;
        progressBar.setVisibility(View.VISIBLE);

        RetrofitClient.getInstance().getApiService()
                .getNews("tr", currentOffset, LIMIT)
                .enqueue(new Callback<ApiResponse<News>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<News>> call, Response<ApiResponse<News>> response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        mPullToRefresh.setRefreshing(false);
                        isLoading = false;

                        if (response.isSuccessful() && response.body() != null) {
                            List<News> fetchedNews = response.body().getSuccess();
                            if (fetchedNews != null && !fetchedNews.isEmpty()) {
                                int positionStart = newsList.size();
                                newsList.addAll(fetchedNews);

                                if (fragment_haberler_adapter == null) {
                                    fragment_haberler_adapter = new fragment_haberler_adapter(
                                            getContext(), newsList, getFragmentManager());
                                    recyclerView.setAdapter(fragment_haberler_adapter);
                                } else {
                                    fragment_haberler_adapter.notifyItemRangeInserted(positionStart, fetchedNews.size());
                                }

                                currentOffset += LIMIT;
                            }
                        } else {
                            if (getContext() != null) {
                                Toast.makeText(getContext(), "Haberler yüklenirken hata oluştu", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<News>> call, Throwable t) {
                        progressBar.setVisibility(View.INVISIBLE);
                        mPullToRefresh.setRefreshing(false);
                        isLoading = false;

                        Log.e(TAG, "Error loading news", t);
                        if (getContext() != null) {
                            Toast.makeText(getContext(), "Bağlantı hatası: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void refreshNews() {
        currentOffset = 0;
        newsList.clear();
        if (fragment_haberler_adapter != null) {
            fragment_haberler_adapter.notifyDataSetChanged();
        }
        loadNews();
    }
}

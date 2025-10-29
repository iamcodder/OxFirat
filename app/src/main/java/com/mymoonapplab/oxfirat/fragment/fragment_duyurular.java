package com.mymoonapplab.oxfirat.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.mymoonapplab.oxfirat.R;
import com.mymoonapplab.oxfirat.adapter.fragment_duyurular_adapter;
import com.mymoonapplab.oxfirat.model.Announcement;
import com.mymoonapplab.oxfirat.model.ApiResponse;
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

public class fragment_duyurular extends Fragment {

    private static final String TAG = "fragment_duyurular";
    private RecyclerView recyclerView;
    private fragment_duyurular_adapter adapter;
    private int son_duyuru_konumu;
    private View rootView;
    private ProgressBar progressBar;

    private ArrayList<Announcement> announcementList;
    private int currentOffset = 0;
    private final int LIMIT = 10;
    private boolean isLoading = false;

    private SwipeRefreshLayout mPullToRefresh;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_duyurular, container, false);

        announcementList = new ArrayList<>();
        currentOffset = 0;

        progressBar = rootView.findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);

        recyler_islemleri();

        loadAnnouncements();

        mPullToRefresh = rootView.findViewById(R.id.pullToRefresh);

        mPullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshAnnouncements();
            }
        });

        return rootView;
    }

    private void recyler_islemleri(){
        recyclerView = rootView.findViewById(R.id.fragment_duyurular_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                int toplam_duyuru = layoutManager.getItemCount();
                son_duyuru_konumu = layoutManager.findLastVisibleItemPosition();

                if (!isLoading && son_duyuru_konumu + 1 == toplam_duyuru) {
                    loadAnnouncements();
                }
            }
        });
    }


    private void loadAnnouncements() {
        if (isLoading) return;

        isLoading = true;
        progressBar.setVisibility(View.VISIBLE);

        RetrofitClient.getInstance().getApiService()
                .getAnnouncements("tr", currentOffset, LIMIT)
                .enqueue(new Callback<ApiResponse<Announcement>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<Announcement>> call, Response<ApiResponse<Announcement>> response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        mPullToRefresh.setRefreshing(false);
                        isLoading = false;

                        if (response.isSuccessful() && response.body() != null) {
                            List<Announcement> fetchedAnnouncements = response.body().getSuccess();
                            if (fetchedAnnouncements != null && !fetchedAnnouncements.isEmpty()) {
                                int positionStart = announcementList.size();
                                announcementList.addAll(fetchedAnnouncements);

                                if (adapter == null) {
                                    adapter = new fragment_duyurular_adapter(
                                            getContext(), announcementList, getFragmentManager());
                                    recyclerView.setAdapter(adapter);
                                } else {
                                    adapter.notifyItemRangeInserted(positionStart, fetchedAnnouncements.size());
                                }

                                currentOffset += LIMIT;
                            }
                        } else {
                            if (getContext() != null) {
                                Toast.makeText(getContext(), "Duyurular yüklenirken hata oluştu", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<Announcement>> call, Throwable t) {
                        progressBar.setVisibility(View.INVISIBLE);
                        mPullToRefresh.setRefreshing(false);
                        isLoading = false;

                        Log.e(TAG, "Error loading announcements", t);
                        if (getContext() != null) {
                            Toast.makeText(getContext(), "Bağlantı hatası: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void refreshAnnouncements() {
        currentOffset = 0;
        announcementList.clear();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        loadAnnouncements();
    }




}

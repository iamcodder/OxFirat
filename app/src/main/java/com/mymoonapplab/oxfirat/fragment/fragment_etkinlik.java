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
import com.mymoonapplab.oxfirat.adapter.fragment_etkinlik_adapter;
import com.mymoonapplab.oxfirat.model.ApiResponse;
import com.mymoonapplab.oxfirat.model.Event;
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

public class fragment_etkinlik extends Fragment {

    private static final String TAG = "fragment_etkinlik";
    private RecyclerView recyclerView;
    private fragment_etkinlik_adapter adapter;
    private ProgressBar progressBar;

    private int son_etkinlik_konumu;

    private View rootView;

    private ArrayList<Event> eventList;
    private int currentOffset = 0;
    private final int LIMIT = 10;
    private boolean isLoading = false;

    private SwipeRefreshLayout mPullToRefresh;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_etkinlik, container, false);

        eventList = new ArrayList<>();
        currentOffset = 0;

        progressBar = rootView.findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);

        recycler_islemleri();

        loadEvents();

        mPullToRefresh = rootView.findViewById(R.id.pullToRefresh);

        mPullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshEvents();
            }
        });

        return rootView;
    }


    private void recycler_islemleri() {
        recyclerView = rootView.findViewById(R.id.fragment_etkinlik_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                int toplam_etkinlik_sayisi = layoutManager.getItemCount();
                son_etkinlik_konumu = layoutManager.findLastVisibleItemPosition();

                if (!isLoading && son_etkinlik_konumu + 1 == toplam_etkinlik_sayisi) {
                    loadEvents();
                }
            }
        });
    }


    private void loadEvents() {
        if (isLoading) return;

        isLoading = true;
        progressBar.setVisibility(View.VISIBLE);

        RetrofitClient.getInstance().getApiService()
                .getEvents("tr", currentOffset, LIMIT)
                .enqueue(new Callback<ApiResponse<Event>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<Event>> call, Response<ApiResponse<Event>> response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        mPullToRefresh.setRefreshing(false);
                        isLoading = false;

                        if (response.isSuccessful() && response.body() != null) {
                            List<Event> fetchedEvents = response.body().getSuccess();
                            if (fetchedEvents != null && !fetchedEvents.isEmpty()) {
                                int positionStart = eventList.size();
                                eventList.addAll(fetchedEvents);

                                if (adapter == null) {
                                    adapter = new fragment_etkinlik_adapter(
                                            getContext(), eventList, getFragmentManager());
                                    recyclerView.setAdapter(adapter);
                                } else {
                                    adapter.notifyItemRangeInserted(positionStart, fetchedEvents.size());
                                }

                                currentOffset += LIMIT;
                            }
                        } else {
                            if (getContext() != null) {
                                Toast.makeText(getContext(), "Etkinlikler yüklenirken hata oluştu", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<Event>> call, Throwable t) {
                        progressBar.setVisibility(View.INVISIBLE);
                        mPullToRefresh.setRefreshing(false);
                        isLoading = false;

                        Log.e(TAG, "Error loading events", t);
                        if (getContext() != null) {
                            Toast.makeText(getContext(), "Bağlantı hatası: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void refreshEvents() {
        currentOffset = 0;
        eventList.clear();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        loadEvents();
    }
}

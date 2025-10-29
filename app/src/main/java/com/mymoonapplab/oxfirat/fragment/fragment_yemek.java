package com.mymoonapplab.oxfirat.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mymoonapplab.oxfirat.R;
import com.mymoonapplab.oxfirat.adapter.fragment_yemek_adapter;
import com.mymoonapplab.oxfirat.model.DailyFood;
import com.mymoonapplab.oxfirat.model.DailyFoodResponse;
import com.mymoonapplab.oxfirat.network.FoodRetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_yemek extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView emptyText;
    private SwipeRefreshLayout swipeRefreshLayout;
    private fragment_yemek_adapter adapter;
    private List<DailyFood> foodList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_yemekhane_webview, container, false);

        recyclerView = rootView.findViewById(R.id.yemek_recyclerview);
        progressBar = rootView.findViewById(R.id.progress);
        emptyText = rootView.findViewById(R.id.empty_text);
        swipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh);

        foodList = new ArrayList<>();
        adapter = new fragment_yemek_adapter(getContext(), foodList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadFoodData();
            }
        });

        loadFoodData();

        return rootView;
    }

    private void loadFoodData() {
        progressBar.setVisibility(View.VISIBLE);
        emptyText.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        Call<DailyFoodResponse> call = FoodRetrofitClient.getInstance()
                .getFoodApiService()
                .getDailyFood(FoodRetrofitClient.BEARER_TOKEN);

        call.enqueue(new Callback<DailyFoodResponse>() {
            @Override
            public void onResponse(Call<DailyFoodResponse> call, Response<DailyFoodResponse> response) {
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);

                if (response.isSuccessful() && response.body() != null) {
                    DailyFoodResponse foodResponse = response.body();

                    if (foodResponse.isSuccess() && foodResponse.getData() != null && !foodResponse.getData().isEmpty()) {
                        foodList.clear();
                        foodList.addAll(foodResponse.getData());
                        adapter.notifyDataSetChanged();
                        recyclerView.setVisibility(View.VISIBLE);
                        emptyText.setVisibility(View.GONE);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        emptyText.setVisibility(View.VISIBLE);
                    }
                } else {
                    recyclerView.setVisibility(View.GONE);
                    emptyText.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Veri yüklenirken hata oluştu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DailyFoodResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                recyclerView.setVisibility(View.GONE);
                emptyText.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "Bağlantı hatası: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

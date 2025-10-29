package com.mymoonapplab.oxfirat.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mymoonapplab.oxfirat.R;
import com.mymoonapplab.oxfirat.model.DailyFood;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class fragment_yemek_adapter extends RecyclerView.Adapter<fragment_yemek_adapter.YemekViewHolder> {

    private Context mContext;
    private List<DailyFood> foodList;

    public fragment_yemek_adapter(Context mContext, List<DailyFood> foodList) {
        this.mContext = mContext;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public YemekViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_yemek_design, viewGroup, false);
        return new YemekViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final YemekViewHolder yemekViewHolder, @SuppressLint("RecyclerView") final int i) {
        DailyFood food = foodList.get(i);

        // Öğün tipini Türkçe'ye çevir
        String mealType = translateMealType(food.getMealType());
        yemekViewHolder.mealType.setText(mealType);

        // Menüyü göster
        yemekViewHolder.menu.setText(food.getMenu());

        // Kaloriyi göster
        if (food.getCalories() != null && !food.getCalories().isEmpty()) {
            yemekViewHolder.calories.setText(food.getCalories() + " kcal");
        } else {
            yemekViewHolder.calories.setText("Belirtilmemiş");
        }

        // Tarihi formatla
        String formattedDate = formatDate(food.getDate());
        yemekViewHolder.date.setText(formattedDate);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    private String translateMealType(String mealType) {
        if (mealType == null) return "Yemek";

        switch (mealType.toLowerCase()) {
            case "breakfast":
                return "Kahvaltı";
            case "lunch":
                return "Öğle Yemeği";
            case "dinner":
                return "Akşam Yemeği";
            default:
                return mealType;
        }
    }

    private String formatDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return "";
        }

        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy", new Locale("tr"));
            Date date = inputFormat.parse(dateString);
            return date != null ? outputFormat.format(date) : dateString;
        } catch (ParseException e) {
            e.printStackTrace();
            return dateString;
        }
    }

    class YemekViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView mealType;
        private TextView menu;
        private TextView calories;
        private TextView date;

        YemekViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.fragment_yemek_cardview);
            mealType = itemView.findViewById(R.id.yemek_meal_type);
            menu = itemView.findViewById(R.id.yemek_menu);
            calories = itemView.findViewById(R.id.yemek_calories);
            date = itemView.findViewById(R.id.yemek_date);
        }
    }
}

package com.mymoonapplab.oxfirat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mymoonapplab.oxfirat.R;

import java.util.List;

public class fargment_yemekhane_adapter extends RecyclerView.Adapter<fargment_yemekhane_adapter.viewHolder> {

    private Context mContext;
    private List<String> liste;

    public fargment_yemekhane_adapter(Context mContext, List<String> liste) {
        this.mContext = mContext;
        this.liste = liste;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View rootView= LayoutInflater.from(mContext).inflate(R.layout.fragment_yemekhane_design,null);

        return new viewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.txt_menu.setText(liste.get(position));
    }

    @Override
    public int getItemCount() {
        return liste.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {

        TextView txt_menu;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            txt_menu=itemView.findViewById(R.id.fragment_yemekhane_design_textView);
        }
    }
}

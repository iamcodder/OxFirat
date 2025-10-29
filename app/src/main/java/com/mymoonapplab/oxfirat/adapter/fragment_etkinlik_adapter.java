package com.mymoonapplab.oxfirat.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mymoonapplab.oxfirat.R;
import com.mymoonapplab.oxfirat.fragment.fragment_dialogbox;
import com.mymoonapplab.oxfirat.model.Event;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class fragment_etkinlik_adapter extends RecyclerView.Adapter<fragment_etkinlik_adapter.etkinlikViewHolder> {

    private Context mContext;
    private List<Event> eventList;
    private FragmentManager manager;

    public fragment_etkinlik_adapter(Context mContext, List<Event> eventList, FragmentManager manager) {
        this.mContext = mContext;
        this.eventList = eventList;
        this.manager = manager;
    }

    @NonNull
    @Override
    public etkinlikViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_etkinlik_design, null);

        return new etkinlikViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final etkinlikViewHolder etkinlikViewHolder, @SuppressLint("RecyclerView") final int i) {
        Event event = eventList.get(i);

        etkinlikViewHolder.mTextview.setText(event.getTitle());

        // İçeriği HTML taglerinden temizleyip göster
        String content = event.getContent();
        if (content != null && !content.isEmpty()) {
            // HTML taglerini kaldır
            String plainText = android.text.Html.fromHtml(content, android.text.Html.FROM_HTML_MODE_LEGACY).toString().trim();
            etkinlikViewHolder.mContentTextview.setText(plainText);
            etkinlikViewHolder.mContentTextview.setVisibility(View.VISIBLE);
        } else {
            etkinlikViewHolder.mContentTextview.setVisibility(View.GONE);
        }

        etkinlikViewHolder.mProgressBar.setVisibility(View.VISIBLE);

        // Görsel URL'ini oluştur
        String imageUrl = "https://www.firat.edu.tr" + event.getContentImage();
        final String defaultImage = "https://www.firat.edu.tr/front/images/about/index1/12.jpg";

        Glide.with(mContext)
                .load(imageUrl)
                .error(Glide.with(mContext).load(defaultImage))
                .transition(DrawableTransitionOptions.withCrossFade())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        etkinlikViewHolder.mProgressBar.setVisibility(View.INVISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        etkinlikViewHolder.mProgressBar.setVisibility(View.INVISIBLE);
                        return false;
                    }
                })
                .into(etkinlikViewHolder.mImageview);

        etkinlikViewHolder.mCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_dialogbox fragment_dialogbox = new fragment_dialogbox(event.getContentPageUrl());
                fragment_dialogbox.show(manager, "dialogbox_showed");
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class etkinlikViewHolder extends RecyclerView.ViewHolder {
        private CardView mCardview;
        private ImageView mImageview;
        private TextView mTextview;
        private TextView mContentTextview;
        private ProgressBar mProgressBar;

        etkinlikViewHolder(@NonNull View itemView) {
            super(itemView);
            mCardview = itemView.findViewById(R.id.fragment_etkinlik_cardview);
            mImageview = itemView.findViewById(R.id.cardView_resim);
            mTextview = itemView.findViewById(R.id.cardView_baslik);
            mContentTextview = itemView.findViewById(R.id.cardView_icerik);
            mProgressBar = itemView.findViewById(R.id.fragment_etkinlik_carview_design_progress);
        }
    }
}

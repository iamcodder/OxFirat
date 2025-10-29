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
import com.mymoonapplab.oxfirat.model.Announcement;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class fragment_duyurular_adapter extends RecyclerView.Adapter<fragment_duyurular_adapter.duyurularViewHolder> {

    private Context mContext;
    private List<Announcement> announcementList;
    private FragmentManager manager;


    public fragment_duyurular_adapter(Context mContext, List<Announcement> announcementList, FragmentManager manager) {
        this.mContext = mContext;
        this.announcementList = announcementList;
        this.manager = manager;
    }


    @NonNull
    @Override
    public duyurularViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_duyurular_design, null);

        return new duyurularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final duyurularViewHolder duyurularViewHolder, @SuppressLint("RecyclerView") final int i) {
        Announcement announcement = announcementList.get(i);

        duyurularViewHolder.mTextview.setText(announcement.getTitle());

        // İçeriği HTML taglerinden temizleyip göster
        String content = announcement.getContent();
        if (content != null && !content.isEmpty()) {
            // HTML taglerini kaldır
            String plainText = android.text.Html.fromHtml(content, android.text.Html.FROM_HTML_MODE_LEGACY).toString().trim();
            duyurularViewHolder.mContentTextview.setText(plainText);
            duyurularViewHolder.mContentTextview.setVisibility(View.VISIBLE);
        } else {
            duyurularViewHolder.mContentTextview.setVisibility(View.GONE);
        }

        duyurularViewHolder.mProgressBar.setVisibility(View.VISIBLE);

        // Görsel URL'ini oluştur
        String imageUrl = "https://www.firat.edu.tr" + announcement.getContentImage();
        final String defaultImage = "https://www.firat.edu.tr/front/images/about/index1/12.jpg";

        Glide.with(mContext)
                .load(imageUrl)
                .error(Glide.with(mContext).load(defaultImage))
                .transition(DrawableTransitionOptions.withCrossFade())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        duyurularViewHolder.mProgressBar.setVisibility(View.INVISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        duyurularViewHolder.mProgressBar.setVisibility(View.INVISIBLE);
                        return false;
                    }
                })
                .into(duyurularViewHolder.mImageview);

        duyurularViewHolder.mCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_dialogbox fragment_dialogbox = new fragment_dialogbox(announcement.getContentPageUrl());
                fragment_dialogbox.show(manager, "dialogbox_showed");
            }
        });
    }

    @Override
    public int getItemCount() {
        return announcementList.size();
    }

    class duyurularViewHolder extends RecyclerView.ViewHolder {
        private CardView mCardview;
        private ImageView mImageview;
        private TextView mTextview;
        private TextView mContentTextview;
        private ProgressBar mProgressBar;

        duyurularViewHolder(@NonNull View itemView) {
            super(itemView);
            mCardview = itemView.findViewById(R.id.fragment_duyurular_cardview);
            mImageview = itemView.findViewById(R.id.cardView_resim);
            mTextview = itemView.findViewById(R.id.cardView_baslik);
            mContentTextview = itemView.findViewById(R.id.cardView_icerik);
            mProgressBar = itemView.findViewById(R.id.fragment_duyurular_carview_design_progress);
        }
    }
}

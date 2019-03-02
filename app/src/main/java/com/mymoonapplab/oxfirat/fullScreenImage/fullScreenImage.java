package com.mymoonapplab.oxfirat.fullScreenImage;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mymoonapplab.oxfirat.R;
import com.wang.avi.AVLoadingIndicatorView;

public class fullScreenImage extends AppCompatActivity {

    private ImageView imageView;
    private AVLoadingIndicatorView progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);


        imageView = findViewById(R.id.activity_full_screen_imageview);
        progress_bar = findViewById(R.id.activity_full_screen_progress_avi);

        Intent getIntent = getIntent();

        Glide.with(getApplicationContext()).load(getIntent.getStringExtra("resim_linki"))
                .load(getIntent.getStringExtra("resim_linki"))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progress_bar.smoothToHide();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progress_bar.smoothToHide();
                        return false;
                    }
                })
                .into(imageView);


    }

}

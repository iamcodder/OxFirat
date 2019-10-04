package com.mymoonapplab.oxfirat.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.mymoonapplab.oxfirat.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class fullScreenImage extends AppCompatActivity {

    private PhotoView imageView;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        progressBar=findViewById(R.id.full_screnn_progress);
        imageView = findViewById(R.id.activity_full_screen_imageview);
        progressBar.setVisibility(View.VISIBLE);

        Intent getIntent = getIntent();

        Glide.with(getApplicationContext()).load(getIntent.getStringExtra("resim_linki"))
                .load(getIntent.getStringExtra("resim_linki"))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.INVISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.INVISIBLE);
                        return false;
                    }
                })
                .into(imageView);


    }

}

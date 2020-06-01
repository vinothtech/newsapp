package com.newsapp.util;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.newsapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static void loadImage(ImageView imageView, String url, CircularProgressDrawable progressDrawable) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(progressDrawable);
        requestOptions.error(R.mipmap.ic_launcher);
        Glide.with(imageView.getContext()).setDefaultRequestOptions(requestOptions).load(url)
                .into(imageView);
    }


    public static CircularProgressDrawable getProgressDrawable(Context contex) {
        CircularProgressDrawable progressDrawable = new CircularProgressDrawable(contex);
        progressDrawable.setStrokeWidth(10f);
        progressDrawable.setCenterRadius(50f);
        progressDrawable.start();
        return progressDrawable;

    }

    @BindingAdapter("android:imageHelper")
    public static void loadImages(ImageView imageView, String url) {
        loadImage(imageView, url, getProgressDrawable(imageView.getContext()));
    }

    @BindingAdapter("android:dateHelper")
    public static void loadDate(TextView textView, Date date) {
        textView.setText(new SimpleDateFormat(TIMESTAMP_FORMAT).format(date));
    }

}

package com.example.ramil.myuniversity.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ramil.myuniversity.R;

public class ImageBindingAdapters {

    @BindingAdapter("imageUrl")
    public static void setNewsImage(ImageView view, String imageUrl) {
        Context context = view.getContext();

        // TODO improve error placeholder

        RequestOptions option = new RequestOptions()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error);

        Glide.with(context)
                .setDefaultRequestOptions(option)
                .load(imageUrl)
                .into(view);
    }
}

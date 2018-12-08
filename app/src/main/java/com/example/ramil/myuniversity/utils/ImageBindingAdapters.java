package com.example.ramil.myuniversity.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ramil.myuniversity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ImageBindingAdapters {

    private static final String TAG = "ImageBindingAdapters";

    @BindingAdapter("imageUrl")
    public static void setNewsImage(ImageView view, String imageUrl) {
        Log.i(TAG, "Download from URL: ");

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

    @BindingAdapter("storageImageUrl")
    public static void setNewsImageFromStorage(final ImageView view, String imageUrl) {
        Log.i(TAG, "Download from storage: ");

        final Context context = view.getContext();

        // TODO improve error placeholder

        final RequestOptions option = new RequestOptions()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error);

        StorageReference storageReference = FirebaseStorage.getInstance()
                .getReferenceFromUrl(imageUrl);

        storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    String downloadUrl = task.getResult().toString();

                    Glide.with(context)
                            .setDefaultRequestOptions(option)
                            .load(downloadUrl)
                            .into(view);
                } else {
                    Log.w(TAG,"Getting download url wasn't successful. ",
                            task.getException());
                }
            }
        });

        /*StorageReference storageReference = FirebaseStorage.getInstance()
                .getReferenceFromUrl(imageUrl);

        Glide.with(context)
                .setDefaultRequestOptions(option)
                .load(storageReference)
                .into(view);*/
    }
}

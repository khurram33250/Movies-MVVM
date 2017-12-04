package com.example.hasham.movies_mvvm.data;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.util.Patterns;
import android.widget.ImageView;

import com.example.hasham.movies_mvvm.util.CropTransformation;
import com.squareup.picasso.Picasso;

/**
 * Developed by Hasham.Tahir on 1/17/2017.
 */

public class DataBinder {

    private DataBinder() {
        //NO-OP
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {

        if (url != null && !url.equals("") && Patterns.WEB_URL.matcher(url).matches()) {
            Context context = imageView.getContext();
            Picasso.with(context).load("https://image.tmdb.org/t/p/w500/" + url).into(imageView);
        }
    }

    @BindingAdapter("imageUrlCrop")
    public static void setImageCropUrl(ImageView imageView, String url) {

        if (url != null && !url.equals("") && Patterns.WEB_URL.matcher(url).matches()) {
            Context context = imageView.getContext();
            Picasso.with(context).load(url).transform(new CropTransformation(500, 500, CropTransformation.GravityHorizontal.LEFT,
                    CropTransformation.GravityVertical.TOP)).into(imageView);
        }
    }

}

package com.vullpes.newsapi.ui.bindingAdapters

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import com.vullpes.newsapi.R

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {

    if(!imageUrl.isNullOrEmpty()){
        Picasso.get()
            .load(imageUrl)
            .resize(70,70)
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)
            .into(view);
    }else{
        Picasso.get()
            .load(R.drawable.ic_image)
            .into(view);
    }

}
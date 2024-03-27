package com.vullpes.newsapi.ui.bindingAdapters

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import com.bumptech.glide.Glide
import com.vullpes.newsapi.R

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    Glide
        .with(view.context)
        .load(imageUrl)
        .centerCrop()
        .placeholder(R.drawable.ic_image)
        .into(view)

}
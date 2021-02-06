package com.mimoupsa.myevents.ui.extensions

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.net.URL

fun ImageView.loadImage(context: Context,url: String){
    Glide.with(context)
        .load(url)
        .into(this);
}
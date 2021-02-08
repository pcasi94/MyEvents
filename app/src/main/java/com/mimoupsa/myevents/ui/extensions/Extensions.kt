package com.mimoupsa.myevents.ui.extensions

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import java.net.URL

fun ImageView.loadImage(context: Context,url: String){
    Glide.with(context)
        .load(url)
        .into(this);
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}
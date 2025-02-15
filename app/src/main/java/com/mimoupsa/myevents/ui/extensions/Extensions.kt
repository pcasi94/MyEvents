package com.mimoupsa.myevents.ui.extensions

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mimoupsa.myevents.domain.model.EventList
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

fun View.switchVisibility(it: Any?){
    if (it == null) visibility = View.GONE
}

fun View.switchVisibility(eventList: EventList){
    visibility = if (eventList.list.isEmpty()) View.VISIBLE
    else View.GONE
}


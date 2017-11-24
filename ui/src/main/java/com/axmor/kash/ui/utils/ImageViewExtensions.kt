package com.axmor.kash.ui.utils

import android.text.TextUtils
import android.widget.ImageView
import com.squareup.picasso.Picasso

/**
 * Created by akolodyazhnyy on 8/29/2017.
 */
fun ImageView.loadImg(imageUrl: String, resPlaceholder: Int) {
    if (TextUtils.isEmpty(imageUrl)) {
        Picasso.with(context).load(resPlaceholder).into(this)
    } else {
        Picasso.with(context).load(imageUrl).placeholder(resPlaceholder).into(this)
    }
}
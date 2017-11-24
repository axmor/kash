package com.axmor.kash.ui.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by akolodyazhnyy on 8/31/2017.
 */
fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

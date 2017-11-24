package com.axmor.kash.ui.utils

import android.content.Context
import android.util.TypedValue

/**
 * Created by akolodyazhnyy on 9/13/2017.
 */

fun Context.dpToPx(dp: Float): Float {
    val r = resources
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.displayMetrics)
}
package com.axmor.kash.ui.utils

import android.content.Context
import android.text.Html
import android.text.Spanned

/**
 * Created by akolodyazhnyy on 8/28/2017.
 */

fun loadCDataHtml(context: Context, cdataResStringId: Int): Spanned? {
    if (0 == cdataResStringId) {
        return null
    }

    val rawHtml = context.getString(cdataResStringId)
    return Html.fromHtml(rawHtml)
}
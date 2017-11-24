package com.axmor.kash.ui.adapters

/**
 * Created by akolodyazhnyy on 8/31/2017.
 */

/**
 * View type interface that bind model to adapter delegate implementation.
 * @see com.axmor.kash.sample.domain.local.RedditNews - example.
 */

interface KashAdapterViewType {
    fun getViewType(): Int
}
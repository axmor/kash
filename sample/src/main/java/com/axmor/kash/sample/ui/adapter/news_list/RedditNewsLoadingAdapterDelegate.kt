package com.axmor.kash.sample.ui.adapter.news_list

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.axmor.kash.sample.R
import com.axmor.kash.ui.adapters.KashAdapterDelegate
import com.axmor.kash.ui.adapters.KashAdapterViewType
import com.axmor.kash.ui.utils.inflate

/**
 * Created by akolodyazhnyy on 8/31/2017.
 */
class RedditNewsLoadingAdapterDelegate : KashAdapterDelegate {

    override fun isForViewType(items: KashAdapterViewType, position: Int) = items.getViewType() == getViewType()

    override fun onCreateViewHolder(parent: ViewGroup) = LoadingViewHolder(parent)

    override fun onBindViewHolder(items: KashAdapterViewType, position: Int, holder: RecyclerView.ViewHolder) {

    }

    override fun getViewType(): Int {
        return RedditNewsAdapterConstants.LOADING
    }

    class LoadingViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.reddit_lsit_item_loading_view))
}
package com.axmor.kash.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by akolodyazhnyy on 8/28/2017.
 */

/**
 * @see com.axmor.kash.sample.ui.adapter.news_list.RedditNewsAdapter - example.
 */

open class KashBaseAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {

    var items: MutableList<KashAdapterViewType> = mutableListOf()
    var delegates = KashAdapterDelegatesManager()

    constructor(items: List<KashAdapterViewType>?, vararg delegates: KashAdapterDelegate) : super() {
        if (items != null) this.items.addAll(items)
        for (d in delegates) {
            this.delegates.addDelegate(d)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].getViewType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegates.onCreateViewHolder(parent, viewType)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegates.onBindViewHolder(items[position], position, holder)
    }
}
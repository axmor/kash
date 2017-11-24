package com.axmor.kash.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by akolodyazhnyy on 8/28/2017.
 */

/**
 * @see com.axmor.kash.sample.ui.adapter.news_list.RedditNewsAdapterDelegate
 */

interface KashAdapterDelegate {
    /**
     * Called to determine whether this AdapterDelegate is the responsible for the given data
     * element.
     *
     * @param items The data source of the Adapter
     * @param position The position in the datasource
     * @return true, if this item is responsible,  otherwise false
     */
    fun isForViewType(items: KashAdapterViewType, position: Int): Boolean

    /**
     * Creates the  {@link RecyclerView.ViewHolder} for the given data source item
     *
     * @param parent The ViewGroup parent of the given datasource
     * @return The new instantiated {@link RecyclerView.ViewHolder}
     */
    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    /**
     * Called to bind the {@link RecyclerView.ViewHolder} to the item of the datas source set
     *
     * @param items The data source
     * @param position The position in the datasource
     * @param holder The {@link RecyclerView.ViewHolder} to bind
     */
    fun onBindViewHolder(items: KashAdapterViewType, position: Int, holder: RecyclerView.ViewHolder)


    fun getViewType(): Int
}
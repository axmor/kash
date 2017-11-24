package com.axmor.kash.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

/**
 * Created by akolodyazhnyy on 8/28/2017.
 */

class KashAdapterDelegatesManager {

    var delegates: MutableList<KashAdapterDelegate> = mutableListOf()

    fun addDelegate(delegate: KashAdapterDelegate): KashAdapterDelegatesManager {
        delegates.add(delegate)
        return this
    }

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        for (d in delegates) {
            if (d.getViewType() == viewType) {
                return d.onCreateViewHolder(parent)
            }
        }
        return EmptyViewHolder(View(parent.context))
    }

    fun onBindViewHolder(items: KashAdapterViewType, position: Int, viewHolder: RecyclerView.ViewHolder) {
        for (d in delegates) {
            if (d.isForViewType(items, position)) {
                return d.onBindViewHolder(items, position, viewHolder)
            }
        }
    }


    class EmptyViewHolder(v: View) : RecyclerView.ViewHolder(v)
}
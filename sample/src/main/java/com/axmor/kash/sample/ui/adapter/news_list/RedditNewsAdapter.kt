package com.axmor.kash.sample.ui.adapter.news_list

import com.axmor.kash.sample.domain.local.RedditNews
import com.axmor.kash.ui.adapters.KashAdapterViewType
import com.axmor.kash.ui.adapters.KashBaseAdapter

/**
 * Created by akolodyazhnyy on 8/31/2017.
 */
class RedditNewsAdapter(favClickListener: RedditNewsAdapterDelegate.OnFavClickListener? = null, var isFavList: Boolean = false) : KashBaseAdapter(mutableListOf(), RedditNewsAdapterDelegate(favClickListener), RedditNewsLoadingAdapterDelegate()) {

    private val loadingItem = object : KashAdapterViewType {
        override fun getViewType() = RedditNewsAdapterConstants.LOADING
    }

    init {
        if (!isFavList)
            items.add(loadingItem)
    }

    fun addNews(news: List<RedditNews>) {
        if (!isFavList) {
            // first remove loading and notify
            val initPosition = items.size - 1
            items.removeAt(initPosition)
            notifyItemRemoved(initPosition)


            // insert news and the loading at the end of the list
            items.addAll(news)
            items.add(loadingItem)
            notifyItemRangeChanged(initPosition, items.size + 1 /* plus loading item */)
        } else {
            items.addAll(news)
            notifyDataSetChanged()
        }
    }

    fun clearAndAddNews(news: List<RedditNews>) {
        items.clear()
        items.addAll(news)
        if (!isFavList) {
            items.add(loadingItem)
        }
        notifyDataSetChanged()
    }


    fun getNews(): ArrayList<RedditNews> =
            items
                    .filter { it.getViewType() == RedditNewsAdapterConstants.NEWS }
                    .map { it as RedditNews } as ArrayList<RedditNews>

    private fun getLastPosition() = if (items.lastIndex == -1) 0 else items.lastIndex

}
package com.axmor.kash.sample.ui.reddit_list

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.axmor.kash.sample.R
import com.axmor.kash.sample.core.AppService
import com.axmor.kash.sample.domain.local.RedditNews
import com.axmor.kash.sample.ui.adapter.news_list.RedditNewsAdapter
import com.axmor.kash.sample.ui.adapter.news_list.RedditNewsAdapterDelegate
import com.axmor.kash.ui.commons.InfiniteScrollListener
import com.axmor.kash.ui.mvvm.KashFragment
import com.axmor.kash.ui.utils.inflate
import kotlinx.android.synthetic.main.reddit_list_fr.*


/**
 * Created by akolodyazhnyy on 8/29/2017.
 */
class RedditListFragment : KashFragment<RedditListViewModel>(), RedditNewsAdapterDelegate.OnFavClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.reddit_list_fr)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        reddit_news_list.apply {
            setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout
            clearOnScrollListeners()
            //call news when getting end of list
            addOnScrollListener(InfiniteScrollListener({ requestNews() }, linearLayout))
        }
        initAdapter()
        viewModel.redditsData.observe(this, Observer { data ->
            (reddit_news_list.adapter as RedditNewsAdapter).clearAndAddNews(data!!)
        })
    }

    override fun getViewModelClass(): Class<RedditListViewModel> = RedditListViewModel::class.java


    private fun requestNews() {
        viewModel.requestNews()
    }

    override fun onFavClick(reddit: RedditNews, isNowFav: Boolean) {
        if (isNowFav) {
            viewModel.makeFavorite(reddit)
        } else {
            viewModel.unmakeFavorite(reddit)
        }
        reddit.fav = isNowFav
        reddit_news_list.adapter.notifyDataSetChanged()
    }

    private fun initAdapter() {
        if (reddit_news_list.adapter == null) {
            reddit_news_list.adapter = RedditNewsAdapter(this)
        }
    }

    override fun getFragmentTag() = this::class.java.name

}
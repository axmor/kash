package com.axmor.kash.sample.ui.favorites

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.axmor.kash.sample.R
import com.axmor.kash.sample.core.AppService
import com.axmor.kash.sample.ui.adapter.news_list.RedditNewsAdapter
import com.axmor.kash.ui.commons.InfiniteScrollListener
import com.axmor.kash.ui.mvvm.KashFragment
import com.axmor.kash.ui.utils.inflate
import kotlinx.android.synthetic.main.reddit_list_fr.*

/**
 * Created by akolodyazhnyy on 8/29/2017.
 */
class FavoritesListFragment : KashFragment<FavoritesListViewModel, AppService>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.reddit_list_fr)
    }

    override fun getViewModelClass(): Class<FavoritesListViewModel> = FavoritesListViewModel::class.java

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        reddit_news_list.apply {
            setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout
            clearOnScrollListeners()
        }
        initAdapter()
        viewModel.redditsData.observe(this, Observer { data ->
            (reddit_news_list.adapter as RedditNewsAdapter).clearAndAddNews(data!!)
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.requestNews()
    }


    private fun initAdapter() {
        if (reddit_news_list.adapter == null) {
            reddit_news_list.adapter = RedditNewsAdapter(isFavList = true)
        }
    }

    override fun getFragmentTag() = this::class.java.name

}
package com.axmor.kash.sample.ui.adapter.news_list

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.axmor.kash.sample.R
import com.axmor.kash.sample.domain.local.RedditNews
import com.axmor.kash.toolset.utils.getFriendlyTime
import com.axmor.kash.ui.adapters.KashAdapterDelegate
import com.axmor.kash.ui.adapters.KashAdapterViewType
import com.axmor.kash.ui.utils.inflate
import com.axmor.kash.ui.utils.loadImg
import kotlinx.android.synthetic.main.reddit_list_item_view.view.*

/**
 * Created by akolodyazhnyy on 8/31/2017.
 */

class RedditNewsAdapterDelegate(private val favClickListener: OnFavClickListener? = null) : KashAdapterDelegate {

    interface OnFavClickListener {
        fun onFavClick(reddit: RedditNews, isNowFav: Boolean)
    }

    override fun isForViewType(items: KashAdapterViewType, position: Int) = items.getViewType() == getViewType()

    override fun onCreateViewHolder(parent: ViewGroup) = NewsViewHolder(parent)

    override fun onBindViewHolder(items: KashAdapterViewType, position: Int, holder: RecyclerView.ViewHolder) {
        (holder as NewsViewHolder).bind(items as RedditNews)
    }

    override fun getViewType() = RedditNewsAdapterConstants.NEWS

    inner class NewsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.reddit_list_item_view)) {

        private val imgThumbnail = itemView.img_thumbnail
        private val description = itemView.description
        private val author = itemView.author
        private val comments = itemView.comments
        private val time = itemView.time
        private val star = itemView.img_star
        private var item: RedditNews? = null

        init {
            star.setOnClickListener({
                favClickListener?.onFavClick(item!!, !item!!.fav)
            })
        }

        fun bind(item: RedditNews) {
            this.item = item
            imgThumbnail.loadImg(item.thumbnail, R.drawable.ic_launcher_background)
            description.text = item.title
            author.text = item.author
            comments.text = "${item.numComments} comments"
            time.text = item.created.getFriendlyTime()
            if (item.fav) {
                star.setImageResource(R.drawable.star_in)
            } else {
                star.setImageResource(R.drawable.star_out)
            }
        }
    }
}
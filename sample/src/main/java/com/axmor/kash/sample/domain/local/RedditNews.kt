package com.axmor.kash.sample.domain.local

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.Index
import android.os.Parcel
import android.os.Parcelable
import com.axmor.kash.sample.ui.adapter.news_list.RedditNewsAdapterConstants
import com.axmor.kash.ui.adapters.KashAdapterViewType
import android.arch.persistence.room.PrimaryKey

/**
 * Created by akolodyazhnyy on 8/29/2017.
 */
@Entity(tableName = "RedditNews", indices = arrayOf(Index(value = "created",
        unique = true)))
data class RedditNews(@PrimaryKey(autoGenerate = true) var id: Long? = null, var author: String = "",
                      var title: String = "",
                      var numComments: Int = 0,
                      var created: Long = 0,
                      var thumbnail: String = "",
                      var url: String? = "",
                      @Ignore var fav: Boolean = false) : Parcelable, KashAdapterViewType {

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<RedditNews> = object : Parcelable.Creator<RedditNews> {
            override fun createFromParcel(source: Parcel): RedditNews = RedditNews(source)
            override fun newArray(size: Int): Array<RedditNews?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readLong(), source.readString(), source.readString(), source.readInt(), source.readLong(), source.readString(), source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.apply {
            writeLong(0)
            writeString(author)
            writeString(title)
            writeInt(numComments)
            writeLong(created)
            writeString(thumbnail)
            writeString(url)
        }
    }

    override fun getViewType() = RedditNewsAdapterConstants.NEWS
}


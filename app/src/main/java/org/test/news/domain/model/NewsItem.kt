package org.test.news.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsItem(var id : String, var name  : String? = null, var description  : String? = null, var url : String? = null, var category : String? = null, @Transient var isBookmarked: Boolean = false) :
    Parcelable

package org.test.news.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsItemEntity(@PrimaryKey val id : String, val name  : String? = null,val description  : String? = null, val url : String? = null,val category : String? = null)

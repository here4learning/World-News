package org.test.news.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.test.news.data.entity.NewsItemEntity


@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: NewsItemEntity)

    @Query("DELETE FROM news WHERE id = :id")
    suspend fun deleteNewsById(id: String)

    @Query("SELECT * FROM news")
    suspend fun getBookmarkNews(): List<NewsItemEntity>

}
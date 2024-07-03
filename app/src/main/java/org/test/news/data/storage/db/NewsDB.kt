package org.test.news.data.storage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import org.test.news.data.entity.NewsItemEntity
import org.test.news.data.storage.dao.NewsDao

@Database(entities = [NewsItemEntity::class], version = 1)
abstract class NewsDB : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}
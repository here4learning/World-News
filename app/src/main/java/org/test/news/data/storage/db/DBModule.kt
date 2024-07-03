package org.test.news.data.storage.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.test.news.data.storage.dao.NewsDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): NewsDB {
        return Room.databaseBuilder(
            context,
            NewsDB::class.java,
            "news_db"
        ).build()
    }

    @Provides
    fun provideNewsDao(db: NewsDB): NewsDao {
        return db.newsDao()
    }
}

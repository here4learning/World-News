package org.test.news.data.repository.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.test.news.data.repository.NewsRepositoryImpl
import org.test.news.domain.repository.INewsRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindNewsRepository(
        articleRepositoryImpl: NewsRepositoryImpl
    ): INewsRepository
}
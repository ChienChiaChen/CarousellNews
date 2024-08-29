package com.chiachen.data.di

import com.chiachen.data.repository.DefaultNewsRepository
import com.chiachen.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideNewsRepository(
        repository: DefaultNewsRepository
    ): NewsRepository
}
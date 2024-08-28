package com.chiachen.network.di

import com.chiachen.data.source.network.NewsNetworkDataSource
import com.chiachen.network.apiservice.NewsApiService
import com.chiachen.network.source.DefaultNewsNetworkDataSource
import com.chiachen.network.utils.Extensions.moshi
import com.chiachen.network.utils.NetworkConstants
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    abstract fun provideNewsNetworkDataSource(
        datasource: DefaultNewsNetworkDataSource
    ): NewsNetworkDataSource

    companion object {
        private const val timeout: Long = 30

        @Provides
        @Singleton
        fun provideNewsApi(builder: Retrofit.Builder): NewsApiService {
            return builder
                .baseUrl(NetworkConstants.BASE_ENDPOINT)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(NewsApiService::class.java)
        }

        @Provides
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit.Builder {
            return Retrofit.Builder()
                .baseUrl(NetworkConstants.BASE_ENDPOINT)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(okHttpClient)
        }

        @Provides
        fun provideOkHttpClient(
            httpLoggingInterceptor: HttpLoggingInterceptor,
        ): OkHttpClient {
            return OkHttpClient.Builder()
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build()
        }

        @Provides
        fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            return interceptor
        }

    }
}
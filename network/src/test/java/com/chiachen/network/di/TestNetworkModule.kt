package com.chiachen.network.di

import com.chiachen.data.source.network.NewsNetworkDataSource
import com.chiachen.network.apiservice.NewsApiService
import com.chiachen.network.source.DefaultNewsNetworkDataSource
import com.chiachen.network.utils.Extensions
import com.chiachen.network.utils.NetworkConstants
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
abstract class TestNetworkModule {

    @Binds
    abstract fun provideNewsNetworkDataSource(
        datasource: DefaultNewsNetworkDataSource
    ): NewsNetworkDataSource

    companion object {

        @Provides
        @Singleton
        fun provideMockWebServer(): MockWebServer {
            return MockWebServer()
        }

        @Provides
        @Singleton
        fun provideNewsApi(builder: Retrofit.Builder): NewsApiService {
            return builder
                .baseUrl(NetworkConstants.BASE_ENDPOINT)
                .build()
                .create(NewsApiService::class.java)
        }

        @Provides
        fun provideRetrofit(
            okHttpClient: OkHttpClient,
            mockWebServer: MockWebServer
        ): Retrofit.Builder {
            return Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(Extensions.moshi))
        }

        @Singleton
        @Provides
        fun provideOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .build()
        }
    }
}

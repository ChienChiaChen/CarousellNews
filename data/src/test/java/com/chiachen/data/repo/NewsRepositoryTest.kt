package com.chiachen.data.repo

import androidx.test.filters.MediumTest
import com.chiachen.common.utils.DataResult
import com.chiachen.data.fake.FakeDataGenerator
import com.chiachen.data.fake.cache.FakeNewsCacheDataSource
import com.chiachen.data.fake.network.FakeNewsNetworkDataSource
import com.chiachen.data.fake.preferences.FakePreferences
import com.chiachen.data.mapper.NewsDataDomainMapper
import com.chiachen.data.repository.DefaultNewsRepository
import com.chiachen.domain.repository.NewsRepository
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import okio.IOException
import org.junit.Before
import org.junit.Test

@MediumTest
class NewsRepositoryTest {

    private lateinit var mockNewsRepository: NewsRepository
    private lateinit var newsDataDomainMapper: NewsDataDomainMapper


    @Before
    fun setup() {
        newsDataDomainMapper = NewsDataDomainMapper()

        // systemUnderTest
        mockNewsRepository = DefaultNewsRepository(
            newsCacheDataSource = FakeNewsCacheDataSource(),
            newsNetworkDataSource = FakeNewsNetworkDataSource(DataResult.Success(FakeDataGenerator.news)),
            preferencesDataSource = FakePreferences(),
            mapper = newsDataDomainMapper,
        )
    }

    @Test
    fun `requestNews() returns news when NetworkDataSource returns success`() = runBlocking {
        val expected =
            newsDataDomainMapper.fromList(FakeDataGenerator.news)

        val result = mockNewsRepository.requestNews()
        val actual = (result as DataResult.Success).data

        TestCase.assertEquals(actual, expected)
    }

    @Test
    fun `requestNews() returns error when NetworkDataSource throws IOException`() = runBlocking {
        val expected = IOException()

        mockNewsRepository = DefaultNewsRepository(
            newsCacheDataSource = FakeNewsCacheDataSource(),
            newsNetworkDataSource = FakeNewsNetworkDataSource(DataResult.Error(expected)),
            preferencesDataSource = FakePreferences(),
            mapper = newsDataDomainMapper,
        )
        val result = mockNewsRepository.requestNews()
        val actual = (result as DataResult.Error).exception

        TestCase.assertEquals(actual, expected)
    }

    @Test
    fun `getNews() returns news from CacheDataSource`() = runBlocking {
        val expected = newsDataDomainMapper.fromList(FakeDataGenerator.news)

        val actual = mockNewsRepository.getNews()

        TestCase.assertEquals(actual, expected)

    }
}


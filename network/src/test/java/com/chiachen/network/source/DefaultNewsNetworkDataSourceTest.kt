package com.chiachen.network.source

import android.net.Uri
import androidx.test.filters.MediumTest
import com.chiachen.common.utils.DataResult
import com.chiachen.data.source.network.NewsNetworkDataSource
import com.chiachen.network.apiservice.NewsApiService
import com.chiachen.network.fakes.FakeDataGenerator
import com.chiachen.network.mapper.NewsNetworkDataMapper
import com.chiachen.network.model.NEWS_RESPONSE_JSON
import com.chiachen.network.utils.Extensions
import com.chiachen.network.utils.enqueueResponse
import io.mockk.MockKAnnotations
import io.mockk.mockkStatic
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.HttpURLConnection


@MediumTest
class DefaultNewsNetworkDataSourceTest {

    private val mockWebServer = MockWebServer()

    private lateinit var mockNewsNetworkDataSource: NewsNetworkDataSource
    private lateinit var newsApiService: NewsApiService
    private lateinit var newsNetworkDataMapper: NewsNetworkDataMapper

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        mockkStatic(Uri::class)

        newsApiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(Extensions.moshi))
            .build()
            .create(NewsApiService::class.java)

        newsNetworkDataMapper = NewsNetworkDataMapper()

        mockNewsNetworkDataSource = DefaultNewsNetworkDataSource(
            newsApiService, newsNetworkDataMapper
        )
    }


    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getNews() returns news when NetworkDataSource returns success`() = runBlocking {
        mockWebServer.enqueueResponse(NEWS_RESPONSE_JSON, 200)
        val expected = FakeDataGenerator.news

        val response = mockNewsNetworkDataSource.getNews()


        val actual = (response as? DataResult.Success)?.data

        TestCase.assertEquals(expected, actual)
    }

    @Test
    fun `getNews() throws Network exception when server is unreachable`() = runBlocking {
        // Given
        mockWebServer.enqueueResponse("", HttpURLConnection.HTTP_UNAVAILABLE)
        val expected = HttpException(
            Response.error<ResponseBody>(
                HttpURLConnection.HTTP_UNAVAILABLE,
                ResponseBody.create("plain/text".toMediaTypeOrNull(), "")
            )
        )

        // When
        val response = mockNewsNetworkDataSource.getNews()

        // Then
        val actual = ((response as DataResult.Error).exception)

        // Assertion
        TestCase.assertEquals(expected.code(), (actual as? HttpException)?.code())
    }

}
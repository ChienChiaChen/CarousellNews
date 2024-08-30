package com.chiachen.presentation.news

import androidx.test.filters.MediumTest
import app.cash.turbine.test
import com.chiachen.common.utils.DataResult
import com.chiachen.domain.usecase.GetNewsUseCase
import com.chiachen.presentation.TestCoroutineRule
import com.chiachen.presentation.fakes.FakeDataGenerator
import com.chiachen.presentation.mapper.NewsDomainPresentationMapper
import com.chiachen.presentation.viewmodel.news.NewsContract
import com.chiachen.presentation.viewmodel.news.NewsSortType
import com.chiachen.presentation.viewmodel.news.NewsViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import okio.IOException
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@MediumTest
class NewsViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var mockNewsViewModel: NewsViewModel

    @MockK
    private lateinit var getNewsUseCase: GetNewsUseCase

    private lateinit var newsDomainPresentationMapper: NewsDomainPresentationMapper

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        newsDomainPresentationMapper = NewsDomainPresentationMapper()

        mockNewsViewModel = NewsViewModel(getNewsUseCase, newsDomainPresentationMapper)

    }

    @Test
    fun `when GetNewsUseCase returns success then verify viewState`() = runBlocking {
        // Given
        coEvery {
            getNewsUseCase.invoke()
        } returns DataResult.Success(FakeDataGenerator.news)

        val expectedViewState = NewsContract.NewsViewState(
            data = newsDomainPresentationMapper.fromList(FakeDataGenerator.news),
        )

        // When
        mockNewsViewModel.setEvent(NewsContract.NewsEvent.Initialization)

        // Then
        TestCase.assertEquals(
            expectedViewState.data.size,
            mockNewsViewModel.viewState.value.data.size
        )
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `when GetNewsUseCase returns IOException() then verify viewEffect`() =
        testCoroutineRule.runBlockingTest {
            // Given
            coEvery {
                getNewsUseCase.invoke()
            } returns DataResult.Error(IOException("test"))

            val expectedViewEffect = NewsContract.NewsViewEffect.ShowSnackBarError("test")

            // When
            mockNewsViewModel.setEvent(NewsContract.NewsEvent.Initialization)

            mockNewsViewModel.viewEffect.test {
                val actual = awaitItem()
                TestCase.assertEquals(
                    expectedViewEffect.error,
                    (actual as? NewsContract.NewsViewEffect.ShowSnackBarError)?.error
                )
                expectNoEvents()

            }
        }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `when OnSortOptionSelected then verify viewEffect`() = runBlocking {
        // Given
        val expected = NewsSortType.RECENT

        // When
        mockNewsViewModel.setEvent(NewsContract.NewsEvent.OnSortOptionSelected(expected))

        // Then
        TestCase.assertEquals(
            expected,
            mockNewsViewModel.viewState.value.sortType
        )
    }
}
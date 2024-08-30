package com.chiachen.presentation.viewmodel.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chiachen.common.utils.DataResult
import com.chiachen.domain.usecase.GetNewsUseCase
import com.chiachen.presentation.mapper.NewsDomainPresentationMapper
import com.chiachen.presentation.viewmodel.news.NewsContract.NewsEvent.Initialization
import com.chiachen.presentation.viewmodel.news.NewsContract.NewsEvent.OnSortOptionSelected
import com.chiachen.presentation.viewmodel.news.NewsContract.NewsEvent.SwipeRefresh
import com.chiachen.presentation.viewmodel.news.NewsContract.NewsViewEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val newsDomainPresentationMapper: NewsDomainPresentationMapper
) : ViewModel() {

    private var job: Job? = null

    val viewState: StateFlow<NewsContract.NewsViewState> get() = _viewState
    private val _viewState = MutableStateFlow(NewsContract.NewsViewState())


    private val _viewEffect: Channel<NewsViewEffect> = Channel()
    val viewEffect = _viewEffect.receiveAsFlow()

    private fun setEffect(builder: () -> NewsViewEffect) {
        val effectValue = builder()
        setState { copy(loading = false) }
        viewModelScope.launch { _viewEffect.send(effectValue) }
    }

    private fun setState(reduce: NewsContract.NewsViewState.() -> NewsContract.NewsViewState) {
        val newState = viewState.value.reduce()
        _viewState.value = newState
    }

    fun setEvent(event: NewsContract.NewsEvent) {
        when (event) {
            is Initialization, SwipeRefresh -> fetchNews()
            is OnSortOptionSelected -> {
                setState {
                    copy(sortType = event.option,
                        data = data.sortedBy {
                            if (NewsSortType.RECENT == event.option) {
                                -it.timeCreated
                            } else {
                                it.rank.toLong()
                            }
                        }
                    )
                }
            }
        }
    }

    private fun fetchNews() {
        setState { copy(loading = true) }
        job?.cancel()
        job = viewModelScope.launch {
            when (val result = getNewsUseCase.invoke()) {
                is DataResult.Success -> {
                    if (result.data.isEmpty()) {
                        setState { copy(showEmptyNews = true, data = emptyList(), loading = false) }
                    } else {
                        setState {
                            copy(
                                showEmptyNews = false,
                                data = newsDomainPresentationMapper.fromList(result.data)
                                    .sortedBy { if (NewsSortType.RECENT == this.sortType) -it.timeCreated else it.rank.toLong() },
                                loading = false
                            )
                        }

                    }
                }

                is DataResult.Error -> {
                    setState {
                        copy(
                            loading= false,
                            showEmptyNews= true,
                            data= emptyList(),
                        )
                    }
                    setEffect { NewsViewEffect.ShowSnackBarError(result.exception.message) }
                }
            }
        }
    }

}
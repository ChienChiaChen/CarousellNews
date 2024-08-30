package com.chiachen.presentation.viewmodel.news

import com.chiachen.presentation.model.NewsPresentationModel

class NewsContract {
    sealed class NewsEvent {
        object Initialization : NewsEvent()
        data class OnSortOptionSelected(val option: NewsSortType) : NewsEvent()

        object SwipeRefresh : NewsEvent()
    }

    sealed class NewsViewEffect {
        data class ShowSnackBarError(val error: String?) : NewsViewEffect()
    }

    data class NewsViewState(
        val loading: Boolean = false,
        val showEmptyNews: Boolean = false,
        val data: List<NewsPresentationModel> = emptyList(),
        val sortType: NewsSortType = NewsSortType.RECENT
    )
}
enum class NewsSortType {
    RECENT, POPULAR
}

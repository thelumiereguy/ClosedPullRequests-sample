package dev.thelumiereguy.feature_closed_pr_list.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.thelumiereguy.data.repo.ClosedPRsRepo
import dev.thelumiereguy.experiments.ExperimentBucket
import dev.thelumiereguy.experiments.ExperimentModule
import dev.thelumiereguy.helpers.framework.DispatcherProvider
import dev.thelumiereguy.helpers.framework.ResultState
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@HiltViewModel
class ClosedPRListingViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val closedPRsRepo: ClosedPRsRepo,
    private val dispatcherProvider: DispatcherProvider,
    @Named(ExperimentModule.LISTING_AD) private val bookListingAdExperiment: ExperimentBucket
) : ViewModel() {

    private val _uiState = closedPRsRepo.getAllClosedPRs().map {
        println("State $it")
        when (it) {
            is ResultState.Error -> UIState.LoadedState(
                true,
                false,
                it.data ?: emptyList()
            )
            is ResultState.Loading -> UIState.LoadedState(
                false,
                true,
                it.data ?: emptyList()
            )
            is ResultState.Success -> UIState.LoadedState(
                false,
                false,
                it.data
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = UIState.EmptyState
    )

    val state = _uiState

    private val _events = MutableSharedFlow<UIEvent>()

    val events = _events.asSharedFlow()

    fun add(action: ClosedPRListingActions) {
        when (action) {
            ClosedPRListingActions.Fetch -> {
//                fetchClosedPRs()
            }
            is ClosedPRListingActions.LoadNextPage -> {
                val totalItems = (state.value as? UIState.LoadedState)?.listItems?.size
                if (totalItems != null && totalItems - action.lastPosition < 3) {
                    closedPRsRepo.loadNextPage()
                }
            }
        }
    }


//    private suspend fun processResponse(audioBooks: List<AudioBook>) {
//        withContext(dispatcherProvider.default) {
//            if (bookListingAdExperiment == ExperimentBucket.B) {
//                mapBooksToListingItemsWithAd(audioBooks)
//            } else {
//                mapBooksToListingItems(audioBooks)
//            }
//        }
//    }

    // New experiment which inserts an ad in after every 7th item
//    private fun mapBooksToListingItemsWithAd(audioBooks: List<AudioBook>) {
//        if (audioBooks.size <= upgradeBannerPosition) {
//            mapBooksToListingItems(audioBooks)
//        } else {
//            val chunkedListOfBooks: List<List<BaseListItem>> = audioBooks
//                .map(::BookItem)
//                .chunked(upgradeBannerPosition)
//
//            val items = chunkedListOfBooks.reduce { acc, chunkOfBooks ->
//                acc + listOf(UpgradeToBannerItem) + chunkOfBooks
//            }

//            _uiState.value = UIState.ListLoadedState(items)
//        }
//    }

//    private fun mapBooksToListingItems(audioBooks: List<AudioBook>) {
//        val items = audioBooks.map(::BookItem)
//        _uiState.value = UIState.ListLoadedState(items)
//    }

    companion object {
        private const val upgradeBannerPosition = 7
    }
}

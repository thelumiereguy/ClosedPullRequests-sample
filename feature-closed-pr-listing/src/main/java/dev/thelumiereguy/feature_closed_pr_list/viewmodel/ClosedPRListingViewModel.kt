package dev.thelumiereguy.feature_closed_pr_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.thelumiereguy.data.repo.ClosedPRsRepo
import dev.thelumiereguy.helpers.framework.DispatcherProvider
import dev.thelumiereguy.helpers.framework.ResultState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ClosedPRListingViewModel @Inject constructor(
    private val closedPRsRepo: ClosedPRsRepo,
    dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private var previousState: UIState? = null

    val state = closedPRsRepo.getAllClosedPRs().map {
        when (it) {
            is ResultState.Loading -> UIState(
                true,
                it.data ?: emptyList(),
                previousState?.errorMessage
            )
            is ResultState.Error -> UIState(
                false,
                it.data ?: emptyList(),
                it.message
            )
            is ResultState.Success -> UIState(
                false,
                it.data,
                null
            )
        }
    }.onEach {
        previousState = it
    }.flowOn(dispatcherProvider.default).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), // Clear buffer only after 5 seconds
        initialValue = UIState(
            true,
            emptyList(),
            null
        )
    )

    fun loadNextPage(lastPosition: Int) {
        val totalItems = state.value.listItems.size
        if (totalItems - lastPosition < 2) {
            closedPRsRepo.loadNextPage()
        }
    }
}

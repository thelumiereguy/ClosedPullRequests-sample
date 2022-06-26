package dev.thelumiereguy.feature_closed_pr_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.thelumiereguy.data.repo.ClosedPRsRepo
import dev.thelumiereguy.helpers.framework.ResultState
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ClosedPRListingViewModel @Inject constructor(
    private val closedPRsRepo: ClosedPRsRepo,
) : ViewModel() {

    val state = closedPRsRepo.getAllClosedPRs().map {
        println("VM State $it")
        when (it) {
            is ResultState.Error -> UIState.LoadedState(
                false,
                it.data ?: emptyList(),
                it.message
            )
            is ResultState.Loading -> UIState.LoadedState(
                true,
                it.data ?: emptyList(),
                null
            )
            is ResultState.Success -> UIState.LoadedState(
                false,
                it.data,
                null
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = UIState.EmptyState
    )

    fun loadNextPage(lastPosition: Int) {
        val totalItems = (state.value as? UIState.LoadedState)?.listItems?.size
        if (totalItems != null && totalItems - lastPosition < 2) {
            closedPRsRepo.loadNextPage()
        }
    }
}

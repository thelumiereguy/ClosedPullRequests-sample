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
        when (it) {
            is ResultState.Error -> UIState(
                false,
                it.data ?: emptyList(),
                it.message
            )
            is ResultState.Loading -> UIState(
                true,
                it.data ?: emptyList(),
                null
            )
            is ResultState.Success -> UIState(
                false,
                it.data,
                null
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
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

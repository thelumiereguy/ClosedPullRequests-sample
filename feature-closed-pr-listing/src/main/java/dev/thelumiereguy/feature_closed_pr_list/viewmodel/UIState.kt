package dev.thelumiereguy.feature_closed_pr_list.viewmodel

import dev.thelumiereguy.data.repo.models.ClosedPR

sealed class UIState {
    data class LoadedState(
        val hasErrorOccurred: Boolean,
        val isLoading: Boolean,
        val listItems: List<ClosedPR>
    ) : UIState()

    object EmptyState : UIState()
}

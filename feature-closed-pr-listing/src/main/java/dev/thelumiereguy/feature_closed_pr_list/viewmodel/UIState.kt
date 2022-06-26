package dev.thelumiereguy.feature_closed_pr_list.viewmodel

import dev.thelumiereguy.data.repo.models.ClosedPR

data class UIState(
    val isLoading: Boolean,
    val listItems: List<ClosedPR>,
    val errorMessage: String? = null,
)
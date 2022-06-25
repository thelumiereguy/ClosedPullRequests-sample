package dev.thelumiereguy.feature_closed_pr_list.viewmodel

sealed class ClosedPRListingActions {
    object Fetch : ClosedPRListingActions()
    data class LoadNextPage(val lastPosition: Int) : ClosedPRListingActions()
}

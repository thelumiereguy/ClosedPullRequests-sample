package dev.thelumiereguy.feature_closed_pr_list.viewmodel

sealed interface UIEvent {
    object ShowLoading : UIEvent
    object HideLoading : UIEvent
}

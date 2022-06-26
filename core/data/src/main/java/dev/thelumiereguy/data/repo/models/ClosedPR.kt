package dev.thelumiereguy.data.repo.models

import dev.thelumiereguy.helpers.ui.adapter.BaseListItem

data class ClosedPR(
    val id: Long,
    val title: String,
    val authorId: String,
    val closedTimeStamp: String,
    val branchDetails: BranchDetails,
    override val itemId: String = "#$id"
) : BaseListItem

data class BranchDetails(
    val head: String,
    val base: String
)

data class PRDetails(
    val replies: Int,
    val commits: Int,
    val filesChanged: Int
)

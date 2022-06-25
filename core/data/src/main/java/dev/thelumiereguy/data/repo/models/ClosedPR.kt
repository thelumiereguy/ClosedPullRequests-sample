package dev.thelumiereguy.data.repo.models

data class ClosedPR(
    val id: Long,
    val title: String,
    val authorId: String,
    val closedTimeStamp: Long,
    val branchDetails: BranchDetails
)

data class BranchDetails(
    val head: String,
    val base: String
)

data class PRDetails(
    val replies: Int,
    val commits: Int,
    val filesChanged: Int
)

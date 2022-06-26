package dev.thelumiereguy.data.network.mapper

import dev.thelumiereguy.data.network.models.ClosedPRsResponseItem
import dev.thelumiereguy.data.repo.models.BranchDetails
import dev.thelumiereguy.data.repo.models.ClosedPR
import kotlinx.datetime.Instant
import java.text.SimpleDateFormat
import java.util.Locale

fun List<ClosedPRsResponseItem>.mapPRListingResponseToDomainModel(): List<ClosedPR> {
    return map { closedPRsResponseItem ->
        ClosedPR(
            closedPRsResponseItem.number.toLong(),
            closedPRsResponseItem.title,
            closedPRsResponseItem.closedAt.formatDate(),
            closedPRsResponseItem.user.login,
            BranchDetails(
                closedPRsResponseItem.head.label,
                closedPRsResponseItem.base.label,
            )
        )
    }
}

private fun String.formatDate(): String {
    val timeStamp = Instant.parse(this).toEpochMilliseconds()
    return dateFormatter.format(timeStamp)
}

private val dateFormatter = SimpleDateFormat("dd MMM yyyy", Locale.ROOT)

package dev.thelumiereguy.data.network.mapper

import dev.thelumiereguy.data.local.models.ClosedPREntity
import dev.thelumiereguy.data.network.models.ClosedPRsResponseItem
import kotlinx.datetime.Instant

fun List<ClosedPRsResponseItem>.mapPRListingResponseToEntity(): List<ClosedPREntity> {
    return map { closedPRsResponseItem ->
        ClosedPREntity(
            closedPRsResponseItem.number.toLong(),
            closedPRsResponseItem.title,
            closedPRsResponseItem.closedAt.convertFullDateToTimestamp(),
            closedPRsResponseItem.user.login,
            closedPRsResponseItem.head.label,
            closedPRsResponseItem.base.label,
        )
    }
}

private fun String.convertFullDateToTimestamp(): Long {
    return Instant.parse(this).toEpochMilliseconds()
}

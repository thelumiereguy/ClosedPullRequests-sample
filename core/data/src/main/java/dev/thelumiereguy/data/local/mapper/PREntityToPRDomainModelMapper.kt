package dev.thelumiereguy.data.local.mapper

import dev.thelumiereguy.data.local.models.ClosedPREntity
import dev.thelumiereguy.data.repo.models.BranchDetails
import dev.thelumiereguy.data.repo.models.ClosedPR
import java.text.SimpleDateFormat
import java.util.*

fun ClosedPREntity?.mapPREntityToDomainModel(): ClosedPR? {
    return if (this != null)
        ClosedPR(
            prId,
            title,
            userLogin,
            closedAt.convertTimestampToFullDate(),
            branchDetails = BranchDetails(
                head = branchHead,
                base = branchBase
            )
        )
    else null
}

private fun Long.convertTimestampToFullDate(): String {
    return dateFormatter.format(this)
}

private val dateFormatter = SimpleDateFormat("dd MMM yyyy", Locale.ROOT)
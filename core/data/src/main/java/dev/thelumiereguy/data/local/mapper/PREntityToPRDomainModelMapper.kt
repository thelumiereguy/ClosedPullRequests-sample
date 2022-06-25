package dev.thelumiereguy.data.local.mapper

import dev.thelumiereguy.data.local.models.ClosedPREntity
import dev.thelumiereguy.data.repo.models.BranchDetails
import dev.thelumiereguy.data.repo.models.ClosedPR

fun ClosedPREntity?.mapPREntityToDomainModel(): ClosedPR? {
    return if (this != null)
        ClosedPR(
            prId,
            title,
            userLogin,
            closedAt,
            branchDetails = BranchDetails(
                head = branchHead,
                base = branchBase
            )
        )
    else null
}
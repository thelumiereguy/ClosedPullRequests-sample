package dev.thelumiereguy.data.network

import dev.thelumiereguy.data.network.models.ClosedPRDetailsResponse
import dev.thelumiereguy.data.network.models.ClosedPRsListResponse
import dev.thelumiereguy.helpers.framework.APIState
import kotlinx.coroutines.flow.Flow

interface ClosedPRsApi {
    fun fetchClosedPRs(pageNumber: Int): Flow<APIState<ClosedPRsListResponse>>
    suspend fun fetchPRDetails(prNumber: Int): ClosedPRDetailsResponse?
}
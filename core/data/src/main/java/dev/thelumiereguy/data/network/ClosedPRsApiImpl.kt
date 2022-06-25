package dev.thelumiereguy.data.network

import dev.thelumiereguy.data.network.models.ClosedPRDetailsResponse
import dev.thelumiereguy.data.network.models.ClosedPRsListResponse
import dev.thelumiereguy.helpers.framework.APIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class ClosedPRsApiImpl : ClosedPRsApi {

    override fun fetchClosedPRs(pageNumberState: Int): Flow<APIState<ClosedPRsListResponse>> = flow {
        emit(APIState.Loading)
        try {
            emit(APIState.Success(ClosedPRsListResponse()))
        } catch (e: Exception) {
            emit(APIState.Error(e.message))
        }
    }

    override suspend fun fetchPRDetails(prNumber: Int): ClosedPRDetailsResponse? {
        return ClosedPRDetailsResponse(
            prNumber,
            2,
            4,
            5
        )
    }
}
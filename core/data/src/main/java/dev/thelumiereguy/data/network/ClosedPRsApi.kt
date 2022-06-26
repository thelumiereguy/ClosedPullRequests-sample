package dev.thelumiereguy.data.network

import dev.thelumiereguy.data.network.models.ClosedPRsResponseItem
import dev.thelumiereguy.helpers.framework.APIState
import kotlinx.coroutines.flow.Flow

interface ClosedPRsApi {
    fun fetchClosedPRs(pageNumber: Int): Flow<APIState<List<ClosedPRsResponseItem>>>
}

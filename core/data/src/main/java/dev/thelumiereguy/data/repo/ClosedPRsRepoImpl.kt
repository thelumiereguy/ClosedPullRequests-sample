package dev.thelumiereguy.data.repo

import dev.thelumiereguy.data.network.ClosedPRsApi
import dev.thelumiereguy.data.network.mapper.mapPRListingResponseToDomainModel
import dev.thelumiereguy.data.repo.models.ClosedPR
import dev.thelumiereguy.helpers.framework.APIState
import dev.thelumiereguy.helpers.framework.DispatcherProvider
import dev.thelumiereguy.helpers.framework.ResultState
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.flowOn

class ClosedPRsRepoImpl @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val closedPRsApi: ClosedPRsApi,
) : ClosedPRsRepo {

    private var closedPRList = emptyList<ClosedPR>()

    private val pageNumberState = MutableStateFlow(1)

    private val closedPRsRemote = pageNumberState.flatMapLatest { pageNumber ->
        closedPRsApi.fetchClosedPRs(pageNumber)
    }

    override fun getAllClosedPRs(): Flow<ResultState<List<ClosedPR>>> {
        return closedPRsRemote.map { apiState ->

            if (apiState is APIState.Success) {
                val prList = apiState.data
                closedPRList = closedPRList + prList.mapPRListingResponseToDomainModel()
            }

            apiState appendAPIState closedPRList
        }.flowOn(dispatcherProvider.io)
    }

    override fun loadNextPage() {
        pageNumberState.getAndUpdate { it + 1 }
    }
}

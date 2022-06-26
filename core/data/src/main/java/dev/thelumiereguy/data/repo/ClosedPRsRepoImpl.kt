package dev.thelumiereguy.data.repo

import dev.thelumiereguy.data.local.dao.ClosedPRDao
import dev.thelumiereguy.data.local.mapper.mapPREntityToDomainModel
import dev.thelumiereguy.data.network.ClosedPRsApi
import dev.thelumiereguy.data.network.mapper.mapPRListingResponseToEntity
import dev.thelumiereguy.data.repo.models.ClosedPR
import dev.thelumiereguy.helpers.framework.APIState
import dev.thelumiereguy.helpers.framework.DispatcherProvider
import dev.thelumiereguy.helpers.framework.ResultState
import javax.inject.Inject
import kotlinx.coroutines.flow.*

class ClosedPRsRepoImpl @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val closedPRsApi: ClosedPRsApi,
    private val prDao: ClosedPRDao
) : ClosedPRsRepo {

    private val pageNumberState = MutableStateFlow(1)

    private val closedPRsRemote = pageNumberState.flatMapLatest { pageNumber ->
        closedPRsApi.fetchClosedPRs(pageNumber).onEach {
            if (it is APIState.Success) {
                val response = it.data
                prDao.insert(response.mapPRListingResponseToEntity())
            }
        }
    }


    override fun getAllClosedPRs(): Flow<ResultState<List<ClosedPR>>> {
        return combine(
            prDao.getClosedPRs(),
            closedPRsRemote
        ) { existingClosedPRs, apiState ->

            println("Repo $existingClosedPRs $apiState")

            val existingData = existingClosedPRs?.mapNotNull {
                it.mapPREntityToDomainModel()
            } ?: emptyList()

            apiState + existingData

        }.flowOn(dispatcherProvider.io)
    }

    override fun loadNextPage() {
        pageNumberState.getAndUpdate { it + 1 }
    }
}
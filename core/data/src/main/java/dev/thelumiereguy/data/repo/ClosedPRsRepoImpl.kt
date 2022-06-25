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
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ClosedPRsRepoImpl @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val closedPRsApi: ClosedPRsApi,
    private val prDao: ClosedPRDao
) : ClosedPRsRepo {

    private val pageNumberState = MutableStateFlow(1)

    private val closedPRsRemote = pageNumberState.flatMapLatest { pageNumber ->
        println("Loading next page $pageNumber")
        closedPRsApi.fetchClosedPRs(pageNumber).onEach {
            if (it is APIState.Success) {
                println("Inserting data $pageNumber")
                val response = it.data
                prDao.insert(response.mapPRListingResponseToEntity())
            }
        }
    }


    override suspend fun getAllClosedPRs(): Flow<ResultState<List<ClosedPR>>> {
        return pageNumberState.flatMapLatest { pageNumber ->
            println("Loading next page $pageNumber")
            closedPRsApi.fetchClosedPRs(pageNumber).onEach {
                if (it is APIState.Success) {
                    println("Inserting data $pageNumber")
                    val response = it.data
                    prDao.insert(response.mapPRListingResponseToEntity())
                }
            }.map { apiState ->
                apiState + (prDao.getClosedPRs().first()?.mapNotNull {
                    it.mapPREntityToDomainModel()
                } ?: emptyList())
//                apiState + existingData
            }
        }

//        return closedPRsRemote.flatMapMerge { apiState ->
//            prDao.getClosedPRs().map {
//                apiState + (it?.mapNotNull {
//                    it.mapPREntityToDomainModel()
//                } ?: emptyList())
//            }
//        }

        return combine(
            prDao.getClosedPRs(),
            closedPRsRemote
        ) { existingClosedPRs, apiState ->

            println("Repo $apiState $existingClosedPRs")

            val existingData = existingClosedPRs?.mapNotNull {
                it.mapPREntityToDomainModel()
            } ?: emptyList()

            apiState + existingData

        }.onEach {
            println("State $it")
        }.flowOn(dispatcherProvider.io)
    }

    override suspend fun loadNextPage() {
        pageNumberState.getAndUpdate { it + 1 }
    }

//    private suspend fun ClosedPRsListResponse.fetchPrDetails(): List<ClosedPR> = supervisorScope {
//
//        val prDetailsList = map { closedPR ->
//            async { closedPRsApi.fetchPRDetails(closedPR.number) }
//        }.awaitAll()
//
//        return@supervisorScope mapIndexed { index, closedPR ->
//
//
//            if (prDetails != null) {
//
//                ClosedPR(
//                    closedPR.number.toLong(),
//                    closedPR.title,
//                    closedPR.user.login,
//                    1,
//                    prDetails = PRDetails(
//                        prDetails.comments,
//                        prDetails.commits,
//                        prDetails.changed_files
//                    ),
//                    branchDetails = BranchDetails(
//                        head = closedPR.head.label,
//                        base = closedPR.base.label
//                    )
//                )
//            } else null
//        }.filterNotNull()
//    }
}
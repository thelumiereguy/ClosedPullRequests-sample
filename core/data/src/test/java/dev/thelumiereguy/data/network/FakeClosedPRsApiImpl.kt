package dev.thelumiereguy.data.network

import dev.thelumiereguy.data.network.models.ClosedPRDetailsResponse
import dev.thelumiereguy.data.network.models.ClosedPRsListResponse
import dev.thelumiereguy.helpers.framework.APIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class FakeClosedPRsApiImpl : ClosedPRsApi {

    private val response = ClosedPRsListResponse().apply {
        add(
            ClosedPRsListResponse.ClosedPRsResponseItem(
                "2022-06-06T18:55:17Z",
                123,
                "Test 1",
                ClosedPRsListResponse.ClosedPRsResponseItem.Base(
                    "master"
                ),
                ClosedPRsListResponse.ClosedPRsResponseItem.Head(
                    "test",
                ),
                ClosedPRsListResponse.ClosedPRsResponseItem.User(
                    "testuser",
                )
            )
        )
    }

    private val responsePage2 = ClosedPRsListResponse().apply {
        add(
            ClosedPRsListResponse.ClosedPRsResponseItem(
                "2022-06-06T18:55:17Z",
                456,
                "Test 2",
                ClosedPRsListResponse.ClosedPRsResponseItem.Base(
                    "master"
                ),
                ClosedPRsListResponse.ClosedPRsResponseItem.Head(
                    "test_branch",
                ),
                ClosedPRsListResponse.ClosedPRsResponseItem.User(
                    "page2TestUser",
                )
            )
        )
    }

    override fun fetchClosedPRs(pageNumber: Int): Flow<APIState<ClosedPRsListResponse>> = flow {
        emit(APIState.Loading)
        try {
            if (pageNumber == 1) {
                emit(APIState.Success(response))
            } else {
                emit(APIState.Success(responsePage2))
            }
        } catch (e: Exception) {
            emit(APIState.Error(e.message))
        }
    }.onEach {
        println("Api $it")
    }

    override suspend fun fetchPRDetails(prNumber: Int): ClosedPRDetailsResponse? {
        return ClosedPRDetailsResponse(
            prNumber,
            1,
            3,
            5
        )
    }

}
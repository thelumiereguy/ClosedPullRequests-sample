package dev.thelumiereguy.data.network

import dev.thelumiereguy.data.network.models.ClosedPRsResponseItem
import dev.thelumiereguy.helpers.framework.APIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeClosedPRsApiImpl : ClosedPRsApi {

    private val response = listOf(
        ClosedPRsResponseItem(
            "2022-06-06T18:58:37Z",
            "2022-06-06T18:55:17Z",
            123,
            "Test 1",
            ClosedPRsResponseItem.Base(
                "master"
            ),
            ClosedPRsResponseItem.Head(
                "test",
            ),
            ClosedPRsResponseItem.User(
                "testuser",
                "",
            )
        )
    )

    private val responsePage2 = listOf(
        ClosedPRsResponseItem(
            "2022-06-06T18:55:17Z",
            "2022-06-06T18:55:17Z",
            456,
            "Test 2",
            ClosedPRsResponseItem.Base(
                "master"
            ),
            ClosedPRsResponseItem.Head(
                "test_branch",
            ),
            ClosedPRsResponseItem.User(
                "page2TestUser",
                "",
            )
        )
    )

    override fun fetchClosedPRs(pageNumber: Int): Flow<APIState<List<ClosedPRsResponseItem>>> =
        flow {
            emit(APIState.Loading)

            if (isError) {
                emit(APIState.Error("error"))
                return@flow
            }

            try {
                if (pageNumber == 1) {
                    emit(APIState.Success(response))
                } else {
                    emit(APIState.Success(responsePage2))
                }
            } catch (e: Exception) {
                emit(APIState.Error(e.message))
            }
        }

    private var isError = false

    fun sendErrorResponse() {
        isError = true
    }
}

package dev.thelumiereguy.data.network

import dev.thelumiereguy.data.network.ClosedPRsApiImpl.ClosedPullRequestsApi.Companion.queryMap
import dev.thelumiereguy.data.network.ClosedPRsApiImpl.ClosedPullRequestsApi.Companion.repoFullName
import dev.thelumiereguy.data.network.models.ClosedPRsResponseItem
import dev.thelumiereguy.helpers.framework.APIState
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

class ClosedPRsApiImpl @Inject constructor(
    retrofit: Retrofit
) : ClosedPRsApi {

    private val api = retrofit.create(ClosedPullRequestsApi::class.java)

    override fun fetchClosedPRs(pageNumber: Int): Flow<APIState<List<ClosedPRsResponseItem>>> =
        flow {
            emit(APIState.Loading)
            try {
                val response = api.getTrendingRepos(
                    repoFullName,
                    queryMap + mapOf(
                        "page" to pageNumber.toString()
                    )
                )
                emit(APIState.Success(response))
            } catch (e: Exception) {
                emit(APIState.Error(e.message))
            }
        }

    interface ClosedPullRequestsApi {

        @GET(REPO_ENDPOINT)
        suspend fun getTrendingRepos(
            @Path("repo_full_name", encoded = true) repoName: String,
            @QueryMap() queryMap: Map<String, String>
        ): List<ClosedPRsResponseItem>

        companion object {

            internal const val repoFullName = "octocat/hello-world"

            internal val queryMap = mapOf(
                "per_page" to "10",
                "state" to "closed"
            )

            private const val REPO_ENDPOINT = "repos/{repo_full_name}/pulls"
        }
    }
}
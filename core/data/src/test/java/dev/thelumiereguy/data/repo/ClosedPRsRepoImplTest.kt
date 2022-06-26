package dev.thelumiereguy.data.repo

import app.cash.turbine.test
import dev.thelumiereguy.data.network.FakeClosedPRsApiImpl
import dev.thelumiereguy.data.repo.models.BranchDetails
import dev.thelumiereguy.data.repo.models.ClosedPR
import dev.thelumiereguy.helpers.framework.DispatcherProvider
import dev.thelumiereguy.helpers.framework.ResultState
import io.mockk.MockKAnnotations
import kotlin.test.assertEquals
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

internal class ClosedPRsRepoImplTest {

    private lateinit var closedPRsRepo: ClosedPRsRepo

    private lateinit var fakeAPIImpl: FakeClosedPRsApiImpl

    init {
        MockKAnnotations.init(this)
    }

    @Test
    fun `when fetch is invoked, it should return proper api states, and insert data into db`() =
        runTest {

            initRepo(this)

            closedPRsRepo.getAllClosedPRs().test {

                assertEquals(
                    ResultState.loading(
                        emptyList()
                    ),
                    awaitItem()
                )

                assertEquals(
                    ResultState.Success(
                        listOf(
                            ClosedPR(
                                123,
                                "Test 1",
                                "testuser",
                                "07 Jun 2022",// 1654541717000
                                branchDetails = BranchDetails(
                                    "test",
                                    "master"
                                )
                            )
                        )
                    ),
                    awaitItem()
                )
            }
        }

    @Test
    fun `when next page is invoked, proper loading state should be emitted with existing data`() =
        runTest {

            val page1Item = ClosedPR(
                123,
                "Test 1",
                "testuser",
                "07 Jun 2022",
                branchDetails = BranchDetails(
                    "test",
                    "master"
                )
            )

            initRepo(this)

            closedPRsRepo.getAllClosedPRs().test {

                assertEquals(
                    ResultState.loading(
                        emptyList()
                    ),
                    awaitItem()
                )

                assertEquals(
                    ResultState.Success(
                        listOf(
                            page1Item
                        )
                    ),
                    awaitItem()
                )

                closedPRsRepo.loadNextPage()

                assertEquals(
                    ResultState.loading(
                        listOf(
                            page1Item
                        )
                    ),
                    awaitItem()
                )

                assertEquals(
                    ResultState.Success(
                        listOf(
                            page1Item,
                            ClosedPR(
                                456,
                                "Test 2",
                                "page2TestUser",
                                "07 Jun 2022",
                                branchDetails = BranchDetails(
                                    "test_branch",
                                    "master"
                                )
                            )
                        )
                    ),
                    awaitItem()
                )
            }
        }

    @Test
    fun `when next page is invoked and if error occurs, error state should be emitted with existing data`() =
        runTest {

            val page1Item = ClosedPR(
                123,
                "Test 1",
                "testuser",
                "07 Jun 2022",
                branchDetails = BranchDetails(
                    "test",
                    "master"
                )
            )

            initRepo(this)

            closedPRsRepo.getAllClosedPRs().test {

                assertEquals(
                    ResultState.loading(
                        emptyList()
                    ),
                    awaitItem()
                )

                assertEquals(
                    ResultState.Success(
                        listOf(
                            page1Item
                        )
                    ),
                    awaitItem()
                )

                fakeAPIImpl.sendErrorResponse()

                closedPRsRepo.loadNextPage()

                assertEquals(
                    ResultState.loading(
                        listOf(
                            page1Item
                        )
                    ),
                    awaitItem()
                )

                assertEquals(
                    ResultState.error(
                        listOf(
                            page1Item,
                        ),
                        "error"
                    ),
                    awaitItem()
                )
            }
        }

    private fun initRepo(testScope: TestScope) {

        val dispatcher = StandardTestDispatcher(testScope.testScheduler)

        fakeAPIImpl = FakeClosedPRsApiImpl()

        closedPRsRepo = ClosedPRsRepoImpl(
            object : DispatcherProvider {
                override val main = dispatcher
                override val default = dispatcher
                override val io = dispatcher
                override val unconfined = dispatcher
            },
            fakeAPIImpl
        )
    }
}

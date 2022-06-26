package dev.thelumiereguy.data.repo

import app.cash.turbine.test
import dev.thelumiereguy.data.local.FakeClosedPRDao
import dev.thelumiereguy.data.local.dao.ClosedPRDao
import dev.thelumiereguy.data.network.FakeClosedPRsApiImpl
import dev.thelumiereguy.data.repo.models.BranchDetails
import dev.thelumiereguy.data.repo.models.ClosedPR
import dev.thelumiereguy.helpers.framework.DispatcherProvider
import dev.thelumiereguy.helpers.framework.ResultState
import io.mockk.*
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class ClosedPRsRepoImplTest {

    private lateinit var closedPRsRepo: ClosedPRsRepo

    private val closedPRDao = mockk<ClosedPRDao>()

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
                                1654541717000,
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
                                1654541717000,
                                branchDetails = BranchDetails(
                                    "test",
                                    "master"
                                )
                            )
                        )
                    ),
                    awaitItem()
                )

                closedPRsRepo.loadNextPage()

                assertEquals(
                    ResultState.loading(
                        listOf(
                            ClosedPR(
                                123,
                                "Test 1",
                                "testuser",
                                1654541717000,
                                branchDetails = BranchDetails(
                                    "test",
                                    "master"
                                )
                            )
                        )
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
                                1654541717000,
                                branchDetails = BranchDetails(
                                    "test",
                                    "master"
                                )
                            ),
                            ClosedPR(
                                456,
                                "Test 2",
                                "page2TestUser",
                                1654541717000,
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

    private fun initRepo(testScope: TestScope) {

        val dispatcher = StandardTestDispatcher(testScope.testScheduler)

        closedPRsRepo = ClosedPRsRepoImpl(
            object : DispatcherProvider {
                override val main = dispatcher
                override val default = dispatcher
                override val io = dispatcher
                override val unconfined = dispatcher
            },
            FakeClosedPRsApiImpl(),
            FakeClosedPRDao()
        )
    }
}

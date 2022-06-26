/*
 * Created by Piyush Pradeepkumar on 26/06/22, 11:18 PM
 */

package dev.thelumiereguy.feature_closed_pr_list.viewmodel

import app.cash.turbine.test
import dev.thelumiereguy.data.repo.ClosedPRsRepo
import dev.thelumiereguy.data.repo.models.BranchDetails
import dev.thelumiereguy.data.repo.models.ClosedPR
import dev.thelumiereguy.helpers.framework.DispatcherProvider
import dev.thelumiereguy.helpers.framework.ResultState
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class ClosedPRListingViewModelTest {

    private lateinit var viewModel: ClosedPRListingViewModel

    private lateinit var dispatcherProvider: DispatcherProvider

    private val mockRepo = mockk<ClosedPRsRepo>()

    init {
        MockKAnnotations.init(this)
    }

    @AfterEach
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when receiving loading state, show UI with loading `() = runTest {

        val channel = MutableSharedFlow<ResultState<List<ClosedPR>>>()

        every {
            mockRepo.getAllClosedPRs()
        } returns channel.asSharedFlow()

        initDispatchers()

        channel.emit(ResultState.loading(emptyList()))

        viewModel.state.test {

            Assertions.assertEquals(
                UIState(
                    true,
                    emptyList(),
                    null
                ),
                awaitItem()
            )
        }
    }

    private val page1Items = listOf(
        ClosedPR(
            1,
            "Test 1",
            "test_author",
            "",
            BranchDetails(
                "test",
                "master"
            )
        ),
        ClosedPR(
            2,
            "Test 2",
            "test_author2",
            "",
            BranchDetails(
                "test2",
                "master"
            )
        )
    )

    @Test
    fun `when receiving success after loading state, show UI with list items`() = runTest {

        every {
            mockRepo.getAllClosedPRs()
        } returns flowOf(
            ResultState.loading(emptyList()),
            ResultState.Success(
                page1Items
            )
        )

        initDispatchers()

        viewModel.state.test {

            Assertions.assertEquals(
                UIState(
                    true,
                    emptyList(),
                    null
                ),
                awaitItem()
            )

            Assertions.assertEquals(
                UIState(
                    false,
                    page1Items,
                    null
                ),
                awaitItem()
            )
        }
    }

    @Test
    fun `when receiving on second page, show UI with first page items`() = runTest {

        justRun {
            mockRepo.loadNextPage()
        }

        every {
            mockRepo.getAllClosedPRs()
        } returns flowOf(
            ResultState.loading(emptyList()),
            ResultState.Success(
                page1Items
            ),
            ResultState.Error(
                "error",
                page1Items
            )
        )

        initDispatchers()

        viewModel.state.test {

            Assertions.assertEquals(
                UIState(
                    true,
                    emptyList(),
                    null
                ),
                awaitItem()
            )

            Assertions.assertEquals(
                UIState(
                    false,
                    page1Items,
                    null
                ),
                awaitItem()
            )

            viewModel.loadNextPage(2)

            Assertions.assertEquals(
                UIState(
                    false,
                    page1Items,
                    "error"
                ),
                awaitItem()
            )
        }
    }

    private fun TestScope.initDispatchers() {
        val testDispatcher = StandardTestDispatcher(testScheduler)

        dispatcherProvider = object : DispatcherProvider {
            override val main: CoroutineDispatcher = testDispatcher
            override val default: CoroutineDispatcher = testDispatcher
            override val io: CoroutineDispatcher = testDispatcher
            override val unconfined: CoroutineDispatcher = testDispatcher
        }

        viewModel = ClosedPRListingViewModel(
            mockRepo,
            dispatcherProvider
        )
    }
}

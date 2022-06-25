package dev.thelumiereguy.feature_closed_pr_list.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Fade
import androidx.transition.TransitionManager
import dagger.hilt.android.AndroidEntryPoint
import dev.thelumiereguy.data.repo.models.ClosedPR
import dev.thelumiereguy.feature_closed_pr_list.R
import dev.thelumiereguy.feature_closed_pr_list.adapter.closedPRAdapter
import dev.thelumiereguy.feature_closed_pr_list.databinding.FragmentClosedPrListingBinding
import dev.thelumiereguy.feature_closed_pr_list.databinding.ItemClosedPullRequestBinding
import dev.thelumiereguy.feature_closed_pr_list.viewmodel.ClosedPRListingActions
import dev.thelumiereguy.feature_closed_pr_list.viewmodel.ClosedPRListingViewModel
import dev.thelumiereguy.feature_closed_pr_list.viewmodel.UIEvent
import dev.thelumiereguy.feature_closed_pr_list.viewmodel.UIState
import dev.thelumiereguy.helpers.ui.SimpleItemDividerDecorator
import dev.thelumiereguy.helpers.ui.adapter.BindingListAdapter
import dev.thelumiereguy.helpers.ui.toDp
import kotlin.math.roundToInt
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ClosedPRListingFragment : Fragment(R.layout.fragment_closed_pr_listing) {

    private val viewModel: ClosedPRListingViewModel by viewModels()

    private var viewBinding: FragmentClosedPrListingBinding? = null

    private var closedPRListAdapter: BindingListAdapter<ClosedPR, ItemClosedPullRequestBinding>? =
        null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        observeViewModel()
        viewModel.add(ClosedPRListingActions.Fetch)
    }

    private fun setupView(view: View) {

        closedPRListAdapter = closedPRAdapter()

        viewBinding = FragmentClosedPrListingBinding.bind(view).apply {
            rvBooksList.run {
                adapter = closedPRListAdapter
                layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(
                    SimpleItemDividerDecorator(
                        requireContext().toDp(4).roundToInt(),
                        false
                    )
                )
            }
        }
    }

    private fun observeViewModel() {
        val binding = viewBinding ?: return
        binding.rvBooksList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val lastPosition =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (lastPosition >= 0) {
                    viewModel.add(
                        ClosedPRListingActions.LoadNextPage(lastPosition)
                    )
                }
            }
        })
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.collect { uiState ->
                    when (uiState) {
                        UIState.EmptyState -> {
                            binding.progressHorizontal.isVisible = false
                            binding.rvBooksList.isVisible = false
                            binding.tvNoItems.isVisible = true
                        }
                        is UIState.LoadedState -> {
                            binding.progressHorizontal.isInvisible = uiState.isLoading.not()
                            binding.tvNoItems.isVisible = uiState.hasErrorOccurred
                            binding.rvBooksList.isVisible = uiState.listItems.isNotEmpty()
                            closedPRListAdapter?.submitList(uiState.listItems)
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.events.collect { uiEvent ->
                    when (uiEvent) {
                        UIEvent.ShowLoading -> {
                            showAfterFadeTransition(binding) {
//                                flLoaderContainer.isVisible = true
                                rvBooksList.isVisible = false
                            }
                        }
                        UIEvent.HideLoading -> {
                            showAfterFadeTransition(binding) {
//                                flLoaderContainer.isVisible = false
                                rvBooksList.isVisible = true
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showAfterFadeTransition(
        binding: FragmentClosedPrListingBinding,
        block: FragmentClosedPrListingBinding.() -> Unit
    ) {
        TransitionManager.beginDelayedTransition(
            binding.root as ViewGroup, Fade()
        )
        block(binding)
    }

    override fun onDestroyView() {
        viewBinding?.rvBooksList?.clearOnScrollListeners()
        viewBinding = null
        closedPRListAdapter = null
        super.onDestroyView()
    }
}
